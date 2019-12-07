package database.service;

import classes.Equipment;
import database.DAO.EquipmentDAO;
import database.configs.DBHandler;
import database.consts.EquipmentTableConsts;
import network.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EquipmentService extends DBHandler implements EquipmentDAO {
    Connection dbConnection = getDbConnection();
    Session session = Session.getInstance("login",-1);
    @Override
    public ResultSet getAllEquipment() {
        ResultSet result = null;
        String select = "SELECT * FROM " + EquipmentTableConsts.EQUIPMENT_TABLE + " WHERE "
                + EquipmentTableConsts.EQUIPMENT_USERID +
                " = ? ";
        try
        {
            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(select);
            prSt.setString(1, String.valueOf(session.getCurrentID()));
            result = prSt.executeQuery();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return result;
    }


    @Override
    public void add(Equipment equipment)
    {
        String insert = "INSERT INTO " + EquipmentTableConsts.EQUIPMENT_TABLE + "("
                + EquipmentTableConsts.EQUIPMENT_NAME + "," + EquipmentTableConsts.EQUIPMENT_MODEL +
                "," + EquipmentTableConsts.EQUIPMENT_HOURSWORKED + "," + EquipmentTableConsts.EQUIPMENT_ENERGY
                + "," + EquipmentTableConsts.EQUIPMENT_GAS + "," + EquipmentTableConsts.EQUIPMENT_USERID + ")"
                + "VALUES (?,?,?,?,?,?)";
        try
        {

            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(insert);
            prSt.setString(1,equipment.getName());
            prSt.setString(2,equipment.getModel());
            prSt.setString(3, String.valueOf(equipment.getHoursWorked()));
            prSt.setString(4, String.valueOf(equipment.getEnergy()));
            prSt.setString(5, String.valueOf(equipment.getGas()));
            prSt.setString(6, String.valueOf(session.getCurrentID()));

            prSt.executeUpdate();
        }

        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }


    @Override
    public void delete(Equipment equipment) {
        ResultSet result = null;
        String delete = "DELETE FROM " + EquipmentTableConsts.EQUIPMENT_TABLE + " WHERE "
                + "(" + EquipmentTableConsts.EQUIPMENT_ID +
                " = ? " + ")";
        try {
            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(delete);
            prSt.setString(1, String.valueOf(equipment.getId()));
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Equipment equipment) {
        String update = "UPDATE " + EquipmentTableConsts.EQUIPMENT_TABLE
                + " SET " + EquipmentTableConsts.EQUIPMENT_NAME + " = ? "
                + "," + EquipmentTableConsts.EQUIPMENT_MODEL + " = ? "
                + "," + EquipmentTableConsts.EQUIPMENT_HOURSWORKED + " = ? "
                + "," + EquipmentTableConsts.EQUIPMENT_ENERGY + " = ? "
                + "," + EquipmentTableConsts.EQUIPMENT_GAS + " = ? "
                + " WHERE " + "(" + EquipmentTableConsts.EQUIPMENT_ID +
                " = ? " + ")";

        try
        {
            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(update);
            prSt.setString(1,equipment.getName());
            prSt.setString(2,equipment.getModel());
            prSt.setString(3, String.valueOf(equipment.getHoursWorked()));
            prSt.setString(4, String.valueOf(equipment.getEnergy()));
            prSt.setString(5, String.valueOf(equipment.getGas()));
            prSt.setString(6, String.valueOf(equipment.getId()));
            prSt.executeUpdate();
        } catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public ResultSet getEquipmentByName(Equipment equipment) {
        ResultSet result = null;

        String select = "SELECT * FROM " + EquipmentTableConsts.EQUIPMENT_TABLE + " WHERE "
                + EquipmentTableConsts.EQUIPMENT_NAME +
                " = ? " + " AND " + EquipmentTableConsts.EQUIPMENT_USERID + " = ? ";
        try
        {
            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(select);
            prSt.setString(1,equipment.getName());
            prSt.setString(2, String.valueOf(session.getCurrentID()));
            result = prSt.executeQuery();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return result;
    }


    @Override
    public ResultSet getEquipmentByHoursWorked(Equipment equipment) {
        ResultSet result = null;

        String select = "SELECT * FROM " + EquipmentTableConsts.EQUIPMENT_TABLE + " WHERE "
                + EquipmentTableConsts.EQUIPMENT_HOURSWORKED +
                " = ? " + " AND " + EquipmentTableConsts.EQUIPMENT_USERID + " = ? ";
        try
        {
            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(select);
            prSt.setString(1, String.valueOf(equipment.getHoursWorked()));
            prSt.setString(2, String.valueOf(session.getCurrentID()));
            result = prSt.executeQuery();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }

        return result;
    }

}
