package database.service;

import classes.Tariff;
import database.DAO.TariffDAO;
import database.configs.DBHandler;
import database.consts.TariffTableConsts;
import network.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TariffService extends DBHandler implements TariffDAO {
    Connection dbConnection = getDbConnection();
    Session session = Session.getInstance("login",-1);
    @Override
    public void add(Tariff tariff) {
        String insert = "INSERT INTO " + TariffTableConsts.TARIFF_TABLE + "("
                + TariffTableConsts.TARIFF_ENERGYCOST + "," + TariffTableConsts.TARIFF_GASCOST +
                "," + TariffTableConsts.TARIFF_RENTCOST + "," + TariffTableConsts.TARIFF_USERID +  ")"
                + "VALUES (?,?,?,?)";
        try
        {

            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(insert);
            prSt.setString(1, String.valueOf(tariff.getEnergyCost()));
            prSt.setString(2, String.valueOf(tariff.getGasCost()));
            prSt.setString(3, String.valueOf(tariff.getRentCost()));
            prSt.setString(4, String.valueOf(session.getCurrentID()));
            prSt.executeUpdate();
        }

        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Tariff tariff) {
        String update = "UPDATE " + TariffTableConsts.TARIFF_TABLE
                + " SET " + TariffTableConsts.TARIFF_ENERGYCOST + " = ? "
                + "," + TariffTableConsts.TARIFF_GASCOST + " = ? "
                + "," + TariffTableConsts.TARIFF_RENTCOST + " = ? "
                + " WHERE " + "(" + TariffTableConsts.TARIFF_ID +
                " = ? " + ")";

        try
        {
            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(update);
            prSt.setString(1, String.valueOf(tariff.getEnergyCost()));
            prSt.setString(2, String.valueOf(tariff.getGasCost()));
            prSt.setString(3, String.valueOf(tariff.getRentCost()));
            prSt.setString(4, String.valueOf(tariff.getId()));
            prSt.executeUpdate();
        } catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Tariff tariff) {
        ResultSet result = null;
        String delete = "DELETE FROM " + TariffTableConsts.TARIFF_TABLE + " WHERE "
                + "(" + TariffTableConsts.TARIFF_ID +
                " = ? " + ")";
        try {
            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(delete);
            prSt.setString(1, String.valueOf(tariff.getId()));
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet getTariff() {
        ResultSet result = null;
        String select = "SELECT * FROM " + TariffTableConsts.TARIFF_TABLE + " WHERE "
                + TariffTableConsts.TARIFF_USERID +
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
}
