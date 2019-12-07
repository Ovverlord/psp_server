package database.service;

import classes.Result;
import database.DAO.ResultDAO;
import database.configs.DBHandler;
import database.consts.*;
import network.Session;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultService extends DBHandler implements ResultDAO {
    Connection dbConnection = getDbConnection();
    Session session = Session.getInstance("login",-1);
    @Override
    public Result calculateCost(Result result) {
        ResultSet GasExpensesRS = null;
        ResultSet EnergyExpensesRS = null;
        ResultSet TariffCostRS = null;
        ResultSet WageCostRS = null;
        ResultSet MaterialCostRS = null;

        Double tempGasExpenses = 0.0;
        Double tempEnergyExpenses = 0.0;
        Double tempGasCost = 0.0;
        Double tempEnergyCost = 0.0;

        String gasExpenses = "(SELECT SUM(CONCAT(hoursworked*gas)) as GasExpenses FROM psp.equipment WHERE "
                + EquipmentTableConsts.EQUIPMENT_USERID + " = ? )";

        String energyExpenses = "(SELECT SUM(CONCAT(hoursworked*energy)) as EnergyExpenses FROM psp.equipment WHERE "
                + EquipmentTableConsts.EQUIPMENT_USERID + " = ? )";

        String tariffCost = "SELECT gascost, energycost, rentcost FROM psp.tariff WHERE "
                + TariffTableConsts.TARIFF_USERID + " = ? ";

        String wageCost = "(SELECT SUM(CONCAT(hoursworked*wage)) as WageCost FROM psp.worker WHERE "
                + WorkerTableConsts.WORKER_USERID + " = ? )";

        String materialCost = "(SELECT SUM(CONCAT(unitcost*usedamount)) as MaterialCost FROM psp.material WHERE "
                + MaterialTableConsts.MATERIAL_USERID + " = ? )";

        try
        {
            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(gasExpenses);
            prSt.setString(1, String.valueOf(session.getCurrentID()));
            GasExpensesRS = prSt.executeQuery();

            prSt = dbConnection.prepareStatement(energyExpenses);
            prSt.setString(1, String.valueOf(session.getCurrentID()));
            EnergyExpensesRS = prSt.executeQuery();

            prSt = dbConnection.prepareStatement(tariffCost);
            prSt.setString(1, String.valueOf(session.getCurrentID()));
            TariffCostRS = prSt.executeQuery();

            prSt = dbConnection.prepareStatement(wageCost);
            prSt.setString(1, String.valueOf(session.getCurrentID()));
            WageCostRS = prSt.executeQuery();


            prSt = dbConnection.prepareStatement(materialCost);
            prSt.setString(1, String.valueOf(session.getCurrentID()));
            MaterialCostRS = prSt.executeQuery();

            if(GasExpensesRS.next())
            {
                tempGasExpenses = GasExpensesRS.getDouble("GasExpenses");
            }

            if(EnergyExpensesRS.next())
            {
                tempEnergyExpenses = EnergyExpensesRS.getDouble("EnergyExpenses");
            }

            if(TariffCostRS.next())
            {
                tempGasCost = TariffCostRS.getDouble("gascost");
                tempEnergyCost = TariffCostRS.getDouble("energycost");
                result.setFinalRentCost(TariffCostRS.getDouble("rentcost"));
            }
            result.setFinalGasCost(tempGasExpenses*tempGasCost);
            result.setFinalEnergyCost(tempEnergyExpenses*tempEnergyCost);
            if(WageCostRS.next())
            {
                result.setFinalWageCost(WageCostRS.getInt("WageCost"));
            }
            if(MaterialCostRS.next())
            {
                result.setFinalMaterialCost(MaterialCostRS.getDouble("MaterialCost"));
            }

            result.setFinalCost(result.getFinalEnergyCost() +
                    result.getFinalGasCost() + result.getFinalRentCost() +
                    result.getFinalWageCost() + result.getFinalMaterialCost());

        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return result;
    }


    @Override
    public void saveResult(Result result) {
        String insert = "INSERT INTO " + ResultTableConsts.RESULT_TABLE + "("
                + ResultTableConsts.RESULT_ENERGY + "," + ResultTableConsts.RESULT_GAS +
                "," + ResultTableConsts.RESULT_RENT + "," + ResultTableConsts.RESULT_MATERIAL
                + "," + ResultTableConsts.RESULT_WAGE + "," + ResultTableConsts.RESULT_COST
                + "," + ResultTableConsts.RESULT_USERID + ")"
                + "VALUES (?,?,?,?,?,?,?)";
        try
        {

            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(insert);
            prSt.setString(1, String.valueOf(result.getFinalEnergyCost()));
            prSt.setString(2, String.valueOf(result.getFinalGasCost()));
            prSt.setString(3, String.valueOf(result.getFinalRentCost()));
            prSt.setString(4, String.valueOf(result.getFinalMaterialCost()));
            prSt.setString(5, String.valueOf(result.getFinalWageCost()));
            prSt.setString(6, String.valueOf(result.getFinalCost()));
            prSt.setString(7, String.valueOf(session.getCurrentID()));
            prSt.executeUpdate();
        }

        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }


    @Override
    public ResultSet getAllResults() {
        ResultSet result = null;
        String select = "SELECT * FROM " + ResultTableConsts.RESULT_TABLE + " WHERE "
                + ResultTableConsts.RESULT_USERID +
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
    public ResultSet getResultByCost(Result result) {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + ResultTableConsts.RESULT_TABLE + " WHERE "
                + ResultTableConsts.RESULT_COST +
                " = ? " + " AND " + ResultTableConsts.RESULT_USERID + " = ? ";
        try
        {
            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(select);
            prSt.setString(1, String.valueOf(result.getFinalCost()));
            prSt.setString(2, String.valueOf(session.getCurrentID()));
            resultSet = prSt.executeQuery();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return resultSet;
    }

    @Override
    public ResultSet getResultByWageCost(Result result) {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + ResultTableConsts.RESULT_TABLE + " WHERE "
                + ResultTableConsts.RESULT_WAGE +
                " = ? " + " AND " + ResultTableConsts.RESULT_USERID + " = ? ";
        try
        {
            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(select);
            prSt.setString(1, String.valueOf(result.getFinalWageCost()));
            prSt.setString(2, String.valueOf(session.getCurrentID()));
            resultSet = prSt.executeQuery();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return resultSet;
    }

    @Override
    public ResultSet getResultByMaterialCost(Result result) {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + ResultTableConsts.RESULT_TABLE + " WHERE "
                + ResultTableConsts.RESULT_MATERIAL +
                " = ? " + " AND " + ResultTableConsts.RESULT_USERID + " = ? ";
        try
        {
            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(select);
            prSt.setString(1, String.valueOf(result.getFinalMaterialCost()));
            prSt.setString(2, String.valueOf(session.getCurrentID()));
            resultSet = prSt.executeQuery();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return resultSet;
    }

    @Override
    public void delete(Result result) {
        ResultSet resultSet = null;
        String delete = "DELETE FROM " + ResultTableConsts.RESULT_TABLE + " WHERE "
                + "(" + ResultTableConsts.RESULT_ID +
                " = ? " + ")";
        try {
            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(delete);
            prSt.setString(1, String.valueOf(result.getId()));
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generateReport(Result result)
    {
        try(FileWriter writer = new FileWriter("UserReports\\Cost " + result.getFinalCost() + ".txt", false))
        {
            double energyPercent = new BigDecimal(result.getFinalEnergyCost()*100
                    /result.getFinalCost()).setScale(2, RoundingMode.UP).doubleValue();
            double gasPercent = new BigDecimal(result.getFinalGasCost()*100
                    /result.getFinalCost()).setScale(2, RoundingMode.UP).doubleValue();
            double wagePercent = new BigDecimal(result.getFinalWageCost()*100
                    /result.getFinalCost()).setScale(2, RoundingMode.UP).doubleValue();
            double materialPercent = new BigDecimal(result.getFinalMaterialCost()*100
                    /result.getFinalCost()).setScale(2, RoundingMode.UP).doubleValue();
            double rentPercent = new BigDecimal(result.getFinalRentCost()*100
                    /result.getFinalCost()).setScale(2, RoundingMode.UP).doubleValue();
                    writer.write("Затраты на энергию: " + String.valueOf(result.getFinalEnergyCost())
                    + ";  " + energyPercent + "%\n");
            writer.write("Затраты на газ: " + String.valueOf(result.getFinalGasCost())
                    + ";  " + gasPercent + "%\n");
            writer.write("Затраты на зарплату: " + String.valueOf(result.getFinalWageCost())
                    + ";  " + wagePercent + "%\n");
            writer.write("Затраты на материалы: " + String.valueOf(result.getFinalMaterialCost())
                    + ";  " + materialPercent + "%\n");
            writer.write("Затраты на аренду: " + String.valueOf(result.getFinalRentCost())
                    + ";  " + rentPercent + "%\n");
            writer.write("Себестоимость выпуска: " + String.valueOf(result.getFinalCost())
                    + ";  \n");
            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }
}
