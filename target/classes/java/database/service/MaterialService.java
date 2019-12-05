package database.service;

import classes.Material;
import database.DAO.MaterialDAO;
import database.configs.DBHandler;
import database.consts.MaterialTableConsts;
import network.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MaterialService extends DBHandler implements MaterialDAO {
    Connection dbConnection = getDbConnection();
    Session session = new Session("nothing",-1);
    @Override
    public ResultSet getAllMaterials() {
        ResultSet result = null;
        String select = "SELECT * FROM " + MaterialTableConsts.MATERIAL_TABLE + " WHERE "
                + MaterialTableConsts.MATERIAL_USERID +
                " = ? ";
        try
        {
            System.out.println(session.getCurrentID());
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
    public void add(Material material) {
        String insert = "INSERT INTO " + MaterialTableConsts.MATERIAL_TABLE + "("
                + MaterialTableConsts.MATERIAL_NAME + "," + MaterialTableConsts.MATERIAL_UNITCOST +
                "," + MaterialTableConsts.MATERIAL_USEDAMOUNT + "," + MaterialTableConsts.MATERIAL_USERID + ")"
                + "VALUES (?,?,?,?)";
        try
        {

            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(insert);
            prSt.setString(1,material.getName());
            prSt.setString(2, String.valueOf(material.getUnitcost()));
            prSt.setString(3, String.valueOf(material.getUsedamount()));
            prSt.setString(4, String.valueOf(session.getCurrentID()));

            prSt.executeUpdate();
        }

        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Material material) {
        String update = "UPDATE " + MaterialTableConsts.MATERIAL_TABLE
                + " SET " + MaterialTableConsts.MATERIAL_NAME + " = ? "
                + "," + MaterialTableConsts.MATERIAL_UNITCOST + " = ? "
                + "," + MaterialTableConsts.MATERIAL_USEDAMOUNT + " = ? "
                + " WHERE " + "(" + MaterialTableConsts.MATERIAL_ID +
                " = ? " + ")";

        try
        {
            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(update);
            prSt.setString(1, material.getName());
            prSt.setString(2, String.valueOf(material.getUnitcost()));
            prSt.setString(3, String.valueOf(material.getUsedamount()));
            prSt.setString(4, String.valueOf(material.getId()));
            prSt.executeUpdate();
        } catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Material material) {
        ResultSet result = null;
        String delete = "DELETE FROM " + MaterialTableConsts.MATERIAL_TABLE + " WHERE "
                + "(" + MaterialTableConsts.MATERIAL_ID +
                " = ? " + ")";
        try {
            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(delete);
            prSt.setString(1, String.valueOf(material.getId()));
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet getMaterialByName(Material material) {
        ResultSet result = null;

        String select = "SELECT * FROM " + MaterialTableConsts.MATERIAL_TABLE + " WHERE "
                + MaterialTableConsts.MATERIAL_NAME +
                " = ? " + " AND " + MaterialTableConsts.MATERIAL_USERID + " = ? ";
        try
        {
            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(select);
            prSt.setString(1, material.getName());
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
    public ResultSet getMaterialByUnitCost(Material material) {
        ResultSet result = null;

        String select = "SELECT * FROM " + MaterialTableConsts.MATERIAL_TABLE + " WHERE "
                + MaterialTableConsts.MATERIAL_UNITCOST +
                " = ? " + " AND " + MaterialTableConsts.MATERIAL_USERID + " = ? ";
        try
        {
            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(select);
            prSt.setString(1, String.valueOf(material.getUnitcost()));
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
    public ResultSet getMaterialByUsedAmount(Material material) {
        ResultSet result = null;

        String select = "SELECT * FROM " + MaterialTableConsts.MATERIAL_TABLE + " WHERE "
                + MaterialTableConsts.MATERIAL_USEDAMOUNT +
                " = ? " + " AND " + MaterialTableConsts.MATERIAL_USERID + " = ? ";
        try
        {
            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(select);
            prSt.setString(1, String.valueOf(material.getUsedamount()));
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
