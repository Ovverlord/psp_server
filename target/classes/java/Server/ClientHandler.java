package Server;


import classes.*;
import database.configs.DBHandler;
import database.service.*;
import network.JSONParser;
import network.Session;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler implements Runnable {
    private static int clients_count = 0;
    private Server server;
    private Socket clientSocket = null;

    public ClientHandler(Socket socket, Server server) {
        this.server = server;
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            Session session = Session.getInstance("notInitialize",-1);
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            String response;
            String[] query;

            System.out.println(clientSocket);
            clients_count++;

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
                            //session = new Session(user.getLogin(), userID);
                            session = Session.getInstance(user.getLogin(), userID);
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
                            //session = new Session(user.getLogin(), userID);
                            Session.getInstance(user.getLogin(), userID);
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
                                        rs.getDouble("energy"),
                                        rs.getDouble("gas"),
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
                                        rs.getDouble("energy"),
                                        rs.getDouble("gas"),
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
                                        rs.getDouble("energy"),
                                        rs.getDouble("gas"),
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
                    //WorkerOperations

                    case "getAllWorkers": {
                        ArrayList<Worker> workers = new ArrayList<Worker>();
                        WorkerService workerService = new WorkerService();
                        ResultSet rs = workerService.getAllWorkers();
                        try {
                            while (rs.next()) {
                                workers.add(new Worker(rs.getString("surname"),
                                        rs.getString("name"),
                                        rs.getString("lastname"),
                                        rs.getString("position"),
                                        rs.getInt("wage"),
                                        rs.getInt("hoursworked"),
                                        rs.getInt("id")));
                            }
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                        response = JSONParser.jsonFromObject(workers.toArray(new Worker[workers.size()]));
                        workers.clear();
                        out.writeUTF(response);
                        out.flush();
                        break;
                    }

                    case "addWorker":
                    {
                        Worker worker = JSONParser.objectFromJson(query[1], Worker.class);
                        WorkerService workerService = new WorkerService();
                        workerService.add(worker);
                        break;
                    }

                    case "deleteWorker":
                    {
                        Worker worker = JSONParser.objectFromJson(query[1], Worker.class);
                        WorkerService workerService = new WorkerService();
                        workerService.delete(worker);
                        break;
                    }

                    case "editWorker": {
                        Worker worker = JSONParser.objectFromJson(query[1], Worker.class);
                        WorkerService workerService = new WorkerService();
                        workerService.update(worker);
                        break;
                    }


                    case "searchWorkerBySurname":
                    {
                        ArrayList<Worker> workers = new ArrayList<Worker>();
                        Worker worker = JSONParser.objectFromJson(query[1], Worker.class);
                        WorkerService workerService = new WorkerService();
                        ResultSet rs = workerService.getWorkerBySurname(worker);
                        try {
                            while (rs.next()) {
                                workers.add(new Worker(rs.getString("surname"),
                                        rs.getString("name"),
                                        rs.getString("lastname"),
                                        rs.getString("position"),
                                        rs.getInt("wage"),
                                        rs.getInt("hoursworked"),
                                        rs.getInt("id")));
                            }
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                        response = JSONParser.jsonFromObject(workers.toArray(new Worker[workers.size()]));
                        out.writeUTF(response);
                        out.flush();
                        break;
                    }


                    case "searchWorkerByPosition":
                    {
                        ArrayList<Worker> workers = new ArrayList<Worker>();
                        Worker worker = JSONParser.objectFromJson(query[1], Worker.class);
                        WorkerService workerService = new WorkerService();
                        ResultSet rs = workerService.getWorkerByPosition(worker);
                        try {
                            while (rs.next()) {
                                workers.add(new Worker(rs.getString("surname"),
                                        rs.getString("name"),
                                        rs.getString("lastname"),
                                        rs.getString("position"),
                                        rs.getInt("wage"),
                                        rs.getInt("hoursworked"),
                                        rs.getInt("id")));
                            }
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                        response = JSONParser.jsonFromObject(workers.toArray(new Worker[workers.size()]));
                        out.writeUTF(response);
                        out.flush();
                        break;
                    }


                    case "searchWorkerByHoursWorked":
                    {
                        ArrayList<Worker> workers = new ArrayList<Worker>();
                        Worker worker = JSONParser.objectFromJson(query[1], Worker.class);
                        WorkerService workerService = new WorkerService();
                        ResultSet rs = workerService.getWorkerByHoursWorked(worker);
                        try {
                            while (rs.next()) {
                                workers.add(new Worker(rs.getString("surname"),
                                        rs.getString("name"),
                                        rs.getString("lastname"),
                                        rs.getString("position"),
                                        rs.getInt("wage"),
                                        rs.getInt("hoursworked"),
                                        rs.getInt("id")));
                            }
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                        response = JSONParser.jsonFromObject(workers.toArray(new Worker[workers.size()]));
                        out.writeUTF(response);
                        out.flush();
                        break;
                    }


                    //===================================================================================
                    //===================================================================================
                    //===================================================================================
                    //===================================================================================
                    //===================================================================================
                    //TariffOperations

                    case "getTariff":
                    {
                        ArrayList<Tariff> tariffs = new ArrayList<Tariff>();
                        TariffService tariffService = new TariffService();
                        ResultSet rs = tariffService.getTariff();
                        try {
                            while (rs.next()) {
                                tariffs.add(new Tariff(rs.getDouble("energycost"),
                                        rs.getDouble("gascost"),
                                        rs.getDouble("rentcost"),
                                        rs.getInt("id")));
                            }
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                        response = JSONParser.jsonFromObject(tariffs.toArray(new Tariff[tariffs.size()]));
                        tariffs.clear();
                        out.writeUTF(response);
                        out.flush();
                        break;
                    }


                    case "addTariff":
                    {
                        Tariff tariff = JSONParser.objectFromJson(query[1], Tariff.class);
                        TariffService tariffService = new TariffService();
                        tariffService.add(tariff);
                        break;
                    }

                    case "deleteTariff":
                    {
                        Tariff tariff = JSONParser.objectFromJson(query[1], Tariff.class);
                        TariffService tariffService = new TariffService();
                        tariffService.delete(tariff);
                        break;
                    }

                    case "editTariff":
                    {
                        Tariff tariff = JSONParser.objectFromJson(query[1], Tariff.class);
                        TariffService tariffService = new TariffService();
                        tariffService.update(tariff);
                        break;
                    }


                    //===================================================================================
                    //===================================================================================
                    //===================================================================================
                    //===================================================================================
                    //===================================================================================
                    //MaterialsOperations


                    case "getAllMaterials":
                    {
                        ArrayList<Material> materials = new ArrayList<Material>();
                        MaterialService materialService = new MaterialService();
                        ResultSet rs = materialService.getAllMaterials();
                        try {
                            while (rs.next()) {
                                materials.add(new Material(rs.getString("name"),
                                        rs.getDouble("unitcost"),
                                        rs.getDouble("usedamount"),
                                        rs.getInt("id")));
                            }
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                        response = JSONParser.jsonFromObject(materials.toArray(new Material[materials.size()]));
                        materials.clear();
                        out.writeUTF(response);
                        out.flush();
                        break;
                    }


                    case "addMaterial":
                    {
                        Material material = JSONParser.objectFromJson(query[1], Material.class);
                        MaterialService materialService = new MaterialService();
                        materialService.add(material);
                        break;
                    }

                    case "deleteMaterial":
                    {
                        Material material = JSONParser.objectFromJson(query[1], Material.class);
                        MaterialService materialService = new MaterialService();
                        materialService.delete(material);
                        break;
                    }


                    case "editMaterial":
                    {
                        Material material = JSONParser.objectFromJson(query[1], Material.class);
                        MaterialService materialService = new MaterialService();
                        materialService.update(material);
                        break;
                    }

                    case "searchMaterialByName":
                    {
                        ArrayList<Material> materials = new ArrayList<Material>();
                        Material material = JSONParser.objectFromJson(query[1], Material.class);
                        MaterialService materialService = new MaterialService();
                        ResultSet rs = materialService.getMaterialByName(material);
                        try {
                            while (rs.next()) {
                                materials.add(new Material(rs.getString("name"),
                                        rs.getDouble("unitcost"),
                                        rs.getDouble("usedamount"),
                                        rs.getInt("id")));
                            }
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                        response = JSONParser.jsonFromObject(materials.toArray(new Material[materials.size()]));
                        out.writeUTF(response);
                        out.flush();
                        break;
                    }


                    case "searchMaterialByUnitCost":
                    {
                        ArrayList<Material> materials = new ArrayList<Material>();
                        Material material = JSONParser.objectFromJson(query[1], Material.class);
                        MaterialService materialService = new MaterialService();
                        ResultSet rs = materialService.getMaterialByUnitCost(material);
                        try {
                            while (rs.next()) {
                                materials.add(new Material(rs.getString("name"),
                                        rs.getDouble("unitcost"),
                                        rs.getDouble("usedamount"),
                                        rs.getInt("id")));
                            }
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                        response = JSONParser.jsonFromObject(materials.toArray(new Material[materials.size()]));
                        out.writeUTF(response);
                        out.flush();
                    }


                    case "searchMaterialByUsedAmount":
                    {
                        ArrayList<Material> materials = new ArrayList<Material>();
                        Material material = JSONParser.objectFromJson(query[1], Material.class);
                        MaterialService materialService = new MaterialService();
                        ResultSet rs = materialService.getMaterialByUsedAmount(material);
                        try {
                            while (rs.next()) {
                                materials.add(new Material(rs.getString("name"),
                                        rs.getDouble("unitcost"),
                                        rs.getDouble("usedamount"),
                                        rs.getInt("id")));
                            }
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                        response = JSONParser.jsonFromObject(materials.toArray(new Material[materials.size()]));
                        out.writeUTF(response);
                        out.flush();
                    }

                    //===================================================================================
                    //===================================================================================
                    //===================================================================================
                    //===================================================================================
                    //===================================================================================
                    //ResultOperations


                    case "calculateCost":
                    {
                        Result result = JSONParser.objectFromJson(query[1], Result.class);
                        ResultService resultService = new ResultService();
                        result = resultService.calculateCost(result);
                        response = JSONParser.jsonFromObject(result);
                        out.writeUTF(response);
                        out.flush();
                        break;
                    }

                    case "saveResult":
                    {
                        Result result = JSONParser.objectFromJson(query[1], Result.class);
                        ResultService resultService = new ResultService();
                        resultService.saveResult(result);
                        break;
                    }

                    case "deleteResult":
                    {
                        Result result = JSONParser.objectFromJson(query[1], Result.class);
                        ResultService resultService = new ResultService();
                        resultService.delete(result);
                        break;
                    }

                    case "getAllResults":
                    {
                        ArrayList<Result> results = new ArrayList<Result>();
                        ResultService resultService = new ResultService();
                        ResultSet rs = resultService.getAllResults();
                        try {
                            while (rs.next()) {
                                results.add(new Result(rs.getDouble("finalEnergyCost"),
                                        rs.getDouble("finalGasCost"),
                                        rs.getDouble("finalRentCost"),
                                        rs.getInt("finalWageCost"),
                                        rs.getDouble("finalMaterialCost"),
                                        rs.getDouble("finalCost"),
                                        rs.getInt("id")));
                            }
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                        response = JSONParser.jsonFromObject(results.toArray(new Result[results.size()]));
                        results.clear();
                        out.writeUTF(response);
                        out.flush();
                        break;
                    }


                    case "searchResultByCost":
                    {
                        ArrayList<Result> results = new ArrayList<Result>();
                        Result result = JSONParser.objectFromJson(query[1], Result.class);
                        ResultService resultService = new ResultService();
                        ResultSet rs = resultService.getResultByCost(result);
                        try {
                            while (rs.next()) {
                                results.add(new Result(rs.getDouble("finalEnergyCost"),
                                        rs.getDouble("finalGasCost"),
                                        rs.getDouble("finalRentCost"),
                                        rs.getInt("finalWageCost"),
                                        rs.getDouble("finalMaterialCost"),
                                        rs.getDouble("finalCost"),
                                        rs.getInt("id")));
                            }
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                        response = JSONParser.jsonFromObject(results.toArray(new Result[results.size()]));
                        results.clear();
                        out.writeUTF(response);
                        out.flush();
                        break;
                    }

                    case "searchResultByWageCost":
                    {
                        ArrayList<Result> results = new ArrayList<Result>();
                        Result result = JSONParser.objectFromJson(query[1], Result.class);
                        ResultService resultService = new ResultService();
                        ResultSet rs = resultService.getResultByWageCost(result);
                        try {
                            while (rs.next()) {
                                results.add(new Result(rs.getDouble("finalEnergyCost"),
                                        rs.getDouble("finalGasCost"),
                                        rs.getDouble("finalRentCost"),
                                        rs.getInt("finalWageCost"),
                                        rs.getDouble("finalMaterialCost"),
                                        rs.getDouble("finalCost"),
                                        rs.getInt("id")));
                            }
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                        response = JSONParser.jsonFromObject(results.toArray(new Result[results.size()]));
                        results.clear();
                        out.writeUTF(response);
                        out.flush();
                        break;
                    }


                    case "searchResultByMaterialCost":
                    {
                        ArrayList<Result> results = new ArrayList<Result>();
                        Result result = JSONParser.objectFromJson(query[1], Result.class);
                        ResultService resultService = new ResultService();
                        ResultSet rs = resultService.getResultByMaterialCost(result);
                        try {
                            while (rs.next()) {
                                results.add(new Result(rs.getDouble("finalEnergyCost"),
                                        rs.getDouble("finalGasCost"),
                                        rs.getDouble("finalRentCost"),
                                        rs.getInt("finalWageCost"),
                                        rs.getDouble("finalMaterialCost"),
                                        rs.getDouble("finalCost"),
                                        rs.getInt("id")));
                            }
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                        response = JSONParser.jsonFromObject(results.toArray(new Result[results.size()]));
                        results.clear();
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
                        session = Session.getInstance(null,null);
                       //session.cleanUserSession();
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
