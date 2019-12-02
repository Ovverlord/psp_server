package Server;


import classes.User;
import database.configs.DBHandler;
import database.service.UserService;
import network.JSONParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.ResultSet;

public class ClientHandler implements Runnable {
    private static int clients_count = 0;
    private Server server;
    private Socket clientSocket = null;

    DataInputStream in;
    DataOutputStream out;
    String[] query;
    String response;

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
                System.out.println("Ожидание запроса");
                //query = in.readUTF();
                query = in.readUTF().split("//");
                switch (query[0]){
                    case "signIn":
                    {
                        UserService userService = new UserService();
                        User user = JSONParser.objectFromJson(query[1], User.class);
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

                        if(flag>0){response = admin;}
                        else{ response = "error";}

                        out.writeUTF(response);
                        out.flush();
                        break;
                    }
                    case "signUp":
                    {
                        User user = JSONParser.objectFromJson(query[1], User.class);
                        System.out.println(user.getLogin());
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
