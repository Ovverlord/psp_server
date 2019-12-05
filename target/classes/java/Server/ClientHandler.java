package Server;


//import classes.Equipment;

import classes.Equipment;
import classes.User;
import database.configs.DBHandler;
import database.service.EquipmentService;
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


    public ClientHandler(Socket socket, Server server) {
        this.server = server;
        this.clientSocket = socket;
    }

    String response;
    String[] query;

    @Override
    public void run() {
        Session session = Session.getSession(null, null, null);
        try {
            System.out.println(clientSocket);
            clients_count++;
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());


            System.out.println("Клиент подключился");
            System.out.println("Его порт: " + clientSocket.getPort());
            System.out.println("Количество клиентов: " + clients_count);

            while (!clientSocket.isClosed()) {
                //System.out.println("Ожидание запроса");
                query = in.readUTF().split("//");
                switch (query[0]) {

                    //User operations
                    case "signIn": {
                        User user = JSONParser.objectFromJson(query[1], User.class);
                        UserService userService = new UserService();
                        ResultSet rs = userService.getUser(user);
                        String isAdmin = "";
                        Integer userID = -1;
                        int flag = 0;
                        try {
                            while (rs.next()) {
                                isAdmin = rs.getString("isAdmin");
                                userID = rs.getInt("id");
                                flag++;
                            }
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }

                        if (flag > 0) {
                            response = isAdmin;
                            //session = new Session(user.getLogin(),admin,user.getId());
                            session = Session.getSession(user.getLogin(), isAdmin, userID);
                        } else {
                            response = "error";
                        }

                        out.writeUTF(response);
                        out.flush();


//                        System.out.print("Текущая сессия после входа:");
//                        System.out.println(session.getCurrentLogin());
//                        System.out.print("ID:");
//                        System.out.println(session.getCurrentID());
                        break;
                    }


                    case "signUp": {
                        User user = JSONParser.objectFromJson(query[1], User.class);
                        UserService userService = new UserService();
                        ResultSet rs = userService.getUserByLogin(user);
                        int flag = 0;
                        Integer userID = -1;
                        try {
                            while (rs.next()) {
                                flag++;
                            }
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                        if (flag > 0) {
                            response = "error";
                        } else {
                            String isAdmin;
                            response = "successfull";
                            userService.add(user);
                            rs = userService.getUserByLogin(user);
                            try {
                                while (rs.next()) {
                                    userID = rs.getInt("id");
                                }
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }
                            if (user.isAdmin() == null) {
                                isAdmin = "0";
                            } else {
                                isAdmin = "1";
                            }
                            //session = new Session(user.getLogin(),isAdmin,user.getId());
                            session = Session.getSession(user.getLogin(), isAdmin, userID);
                        }
                        out.writeUTF(response);
                        out.flush();


//                        System.out.print("Текущая сессия после регистрации:");
//                        System.out.println(session.getCurrentLogin());
//                        System.out.print("ID:");
//                        System.out.println(session.getCurrentID());
                        break;
                    }


                    case "getAllUsers": {
                        ArrayList<User> users = new ArrayList<User>();
                        UserService userService = new UserService();
                        ResultSet rs = userService.getAllUsers();
                        try {
                            while (rs.next()) {
                                users.add(new User(rs.getString("login"),
                                        rs.getString("password"),
                                        rs.getString("isAdmin"),
                                        rs.getInt("id")));
                            }
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                        response = JSONParser.jsonFromObject(users.toArray(new User[users.size()]));
                        users.clear();
                        out.writeUTF(response);
                        out.flush();
                        break;
                    }


                    case "addUser": {
                        User user = JSONParser.objectFromJson(query[1], User.class);
                        UserService userService = new UserService();
                        ResultSet rs = userService.getUserByLogin(user);
                        int flag = 0;
                        try {
                            while (rs.next()) {
                                flag++;
                            }
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                        if (flag > 0) {
                            response = "error";
                        } else {
                            userService.add(user);
                            response = "successfull";
                        }
                        out.writeUTF(response);
                        out.flush();
                        break;
                    }


                    case "deleteUser": {
                        User user = JSONParser.objectFromJson(query[1], User.class);
                        if (session.getCurrentLogin().equals(user.getLogin())) {
                            response = "error";
                        } else {
                            UserService userService = new UserService();
                            userService.delete(user);
                            response = "successfull";
                        }
                        out.writeUTF(response);
                        out.flush();
                        break;
                    }


                    case "editUser": {
                        User user = JSONParser.objectFromJson(query[1], User.class);
                        UserService userService = new UserService();
                        response = userService.update(user);
                        out.writeUTF(response);
                        out.flush();
                        break;
                    }


                    case "searchUser": {
                        User user = JSONParser.objectFromJson(query[1], User.class);
                        UserService userService = new UserService();
                        ResultSet rs = userService.getUserByLogin(user);
                        int flag = 0;
                        try {
                            while (rs.next()) {
                                user.setLogin(rs.getString("login"));
                                user.setPassword(rs.getString("password"));
                                user.setAdmin(rs.getString("isAdmin"));
                                user.setId(rs.getInt("id"));
                                flag++;
                            }
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                        if (flag == 0) {
                            user.setAdmin("notFound");
                        }
                        response = JSONParser.jsonFromObject(user);
                        out.writeUTF(response);
                        out.flush();
                        break;
                    }


                    //===================================================================================
                    //===================================================================================
                    //===================================================================================
                    //===================================================================================
                    //===================================================================================
                    //EquipmentOperations
                    case "getAllEquipment": {
                        ArrayList<Equipment> equipment = new ArrayList<Equipment>();
                        EquipmentService equipmentService = new EquipmentService();
                        ResultSet rs = equipmentService.getAllEquipment();
                        try {
                            while (rs.next()) {
                                equipment.add(new Equipment(rs.getString("name"),
                                        rs.getString("model"),
                                        rs.getInt("hoursworked"),
                                        rs.getInt("energy"),
                                        rs.getInt("gas"),
                                        rs.getInt("id")));
                            }
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                        response = JSONParser.jsonFromObject(equipment.toArray(new Equipment[equipment.size()]));
                        equipment.clear();
                        out.writeUTF(response);
                        out.flush();
                        break;
                    }


                    case "addEquipment": {
                        Equipment equipment = JSONParser.objectFromJson(query[1], Equipment.class);
                        EquipmentService equipmentService = new EquipmentService();
                        equipmentService.add(equipment);
                        break;
                    }

                    case "deleteEquipment": {
                        Equipment equipment = JSONParser.objectFromJson(query[1], Equipment.class);


                        EquipmentService equipmentService = new EquipmentService();
                        equipmentService.delete(equipment);
                        break;
                    }

                    case "editEquipment": {
                        Equipment equipment = JSONParser.objectFromJson(query[1], Equipment.class);
                        EquipmentService equipmentService = new EquipmentService();
                        equipmentService.update(equipment);
                        break;
                    }



                    case "searchEquipmentByName":
                    {
                        ArrayList<Equipment> equipment_list = new ArrayList<Equipment>();
                        Equipment equipment = JSONParser.objectFromJson(query[1], Equipment.class);
                        EquipmentService equipmentService = new EquipmentService();
                        ResultSet rs = equipmentService.getEquipmentByName(equipment);
                        try {
                            while (rs.next()) {
                                equipment_list.add(new Equipment(rs.getString("name"),
                                        rs.getString("model"),
                                        rs.getInt("hoursworked"),
                                        rs.getInt("energy"),
                                        rs.getInt("gas"),
                                        rs.getInt("id")));
                            }
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                        response = JSONParser.jsonFromObject(equipment_list.toArray(new Equipment[equipment_list.size()]));
                        out.writeUTF(response);
                        out.flush();
                        break;
                    }


                    case "searchEquipmentByHoursWorked":
                    {
                        ArrayList<Equipment> equipment_list = new ArrayList<Equipment>();
                        Equipment equipment = JSONParser.objectFromJson(query[1], Equipment.class);
                        EquipmentService equipmentService = new EquipmentService();
                        ResultSet rs = equipmentService.getEquipmentByHoursWorked(equipment);
                        try {
                            while (rs.next()) {
                                equipment_list.add(new Equipment(rs.getString("name"),
                                        rs.getString("model"),
                                        rs.getInt("hoursworked"),
                                        rs.getInt("energy"),
                                        rs.getInt("gas"),
                                        rs.getInt("id")));
                            }
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                        response = JSONParser.jsonFromObject(equipment_list.toArray(new Equipment[equipment_list.size()]));
                        out.writeUTF(response);
                        out.flush();
                        break;
                    }


                    //===================================================================================
                    //===================================================================================
                    //===================================================================================
                    //===================================================================================
                    //===================================================================================
                    //Exit
                    case "endSession": {
                        session = Session.getSession(null, null, null);
                        break;
                    }


                    case "exit": {
                        session = null;
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
