package Server;


import classes.User;
import database.configs.DBHandler;
import database.service.UserService;
import network.JSONParser;
import network.Session;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private static int clients_count = 0;
    private Server server;
    private Socket clientSocket = null;

    DataInputStream in;
    DataOutputStream out;
    String[] query;
    String response;
    Session session;

    public ClientHandler(Socket socket, Server server) {
        this.server = server;
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            System.out.println(clientSocket);
            clients_count++;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            System.out.println("Клиент подключился");
            System.out.println("Количество клиентов: " + clients_count);

            while (!clientSocket.isClosed()){
                //System.out.println("Ожидание запроса");
                query = in.readUTF().split("//");
                switch (query[0]){

                    case "signIn":
                    {
                        User user = JSONParser.objectFromJson(query[1], User.class);
                        UserService userService = new UserService();
                        ResultSet rs = userService.getUser(user);
                        String admin = "";
                        int flag = 0;
                        try
                        {
                            while (rs.next())
                            {
                                admin = rs.getString("isAdmin");
                                flag++;
                            }
                        }
                        catch (Exception ex)
                        {
                            System.out.println(ex);
                        }

                        if(flag>0){
                            response = admin;
                            session = new Session(user.getLogin(),admin,user.getId());
                        }
                        else{ response = "error";}

                        out.writeUTF(response);
                        out.flush();
                        break;
                    }



                    case "signUp":
                    {
                        User user = JSONParser.objectFromJson(query[1], User.class);
                        UserService userService = new UserService();
                        ResultSet rs = userService.getUserByLogin(user);
                        int flag = 0;
                        try
                        {
                            while (rs.next())
                            {
                                flag++;
                            }
                        }
                        catch (Exception ex)
                        {
                            System.out.println(ex);
                        }
                        if(flag>0){
                            response = "error";
                        }
                        else{
                            String isAdmin;
                            response = "successfull";
                            userService.add(user);
                            if(user.isAdmin()==null){isAdmin="0";}
                            else{isAdmin="1";}
                            session = new Session(user.getLogin(),isAdmin,user.getId());
                        }
                        out.writeUTF(response);
                        out.flush();
                        break;
                    }




                    case "getAllUsers":
                    {
                        ArrayList<User> users = new ArrayList<User>();
                        UserService userService = new UserService();
                        ResultSet rs = userService.getAllUsers();
                        try
                        {
                            while (rs.next())
                            {
                                users.add(new User(rs.getString("login"),
                                        rs.getString("password"),
                                        rs.getString("isAdmin"),
                                        rs.getInt("id")));
                            }
                        }
                        catch (Exception ex)
                        {
                            System.out.println(ex);
                        }
                        response = JSONParser.jsonFromObject(users.toArray(new User[users.size()]));
                        users.clear();
                        out.writeUTF(response);
                        out.flush();
                        break;
                    }



                    case "addUser":
                    {
                        User user = JSONParser.objectFromJson(query[1], User.class);
                        UserService userService = new UserService();
                        ResultSet rs = userService.getUserByLogin(user);
                        int flag = 0;
                        try
                        {
                            while (rs.next())
                            {
                                flag++;
                            }
                        }
                        catch (Exception ex)
                        {
                            System.out.println(ex);
                        }
                        if(flag>0){
                            response = "error";
                        }
                        else
                        {
                            userService.add(user);
                            response = "successfull";
                        }
                        out.writeUTF(response);
                        out.flush();
                        break;
                    }



                    case "deleteUser":
                    {
                        User user = JSONParser.objectFromJson(query[1], User.class);
                        if(session.getCurrentLogin().equals(user.getLogin()))
                        {
                            response = "error";
                        }
                        else
                        {
                            UserService userService = new UserService();
                            userService.delete(user);
                            response = "successfull";
                        }
                        out.writeUTF(response);
                        out.flush();
                        break;
                    }



                    case "editUser":
                    {
                        User user = JSONParser.objectFromJson(query[1], User.class);
                        UserService userService = new UserService();
                        String response = userService.update(user);
                        out.writeUTF(response);
                        out.flush();
                        break;
                    }



                    case "searchUser":
                    {
                        User user = JSONParser.objectFromJson(query[1], User.class);
                        UserService userService = new UserService();
                        ResultSet rs = userService.getUserByLogin(user);
                        int flag = 0;
                        try
                        {
                            while (rs.next())
                            {
                                user.setLogin(rs.getString("login"));
                                user.setPassword(rs.getString("password"));
                                user.setAdmin(rs.getString("isAdmin"));
                                user.setId(rs.getInt("id"));
                                flag++;
                            }
                        }
                        catch (Exception ex)
                        {
                            System.out.println(ex);
                        }
                        if(flag==0)
                        {
                            user.setAdmin("notFound");
                        }
                        response = JSONParser.jsonFromObject(user);
                        out.writeUTF(response);
                        out.flush();
                        break;
                    }


                    case "endSession":
                    {
                        session = null;
                        break;
                    }



                    case "exit":
                    {
                        System.out.println("Клиент отключился");
                        clients_count--;
                        clientSocket.close();
                        Thread.interrupted();
                        System.out.println("Количество клиентов: " + clients_count);
                        break;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
