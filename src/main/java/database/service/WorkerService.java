package database.service;

import classes.Worker;
import database.DAO.WorkerDAO;
import database.configs.DBHandler;
import database.consts.WorkerTableConsts;
import network.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkerService extends DBHandler implements WorkerDAO {
    Connection dbConnection = getDbConnection();
    Session session = new Session("nothing",-1);
    @Override
    public ResultSet getAllWorkers() {
        ResultSet result = null;
        String select = "SELECT * FROM " + WorkerTableConsts.WORKER_TABLE + " WHERE "
                + WorkerTableConsts.WORKER_USERID +
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
    public void add(Worker worker) {
        String insert = "INSERT INTO " + WorkerTableConsts.WORKER_TABLE + "("
                + WorkerTableConsts.WORKER_SURNAME + "," + WorkerTableConsts.WORKER_NAME +
                "," + WorkerTableConsts.WORKER_LASTNAME + "," + WorkerTableConsts.WORKER_POSITION
                + "," + WorkerTableConsts.WORKER_WAGE + "," + WorkerTableConsts.WORKER_HOURSWORKED
                + "," + WorkerTableConsts.WORKER_USERID + ")"
                + "VALUES (?,?,?,?,?,?,?)";
        try
        {

            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(insert);
            prSt.setString(1,worker.getSurname());
            prSt.setString(2,worker.getName());
            prSt.setString(3, worker.getLastname());
            prSt.setString(4, worker.getPosition());
            prSt.setString(5, String.valueOf(worker.getWage()));
            prSt.setString(6, String.valueOf(worker.getHoursworked()));
            prSt.setString(7, String.valueOf(session.getCurrentID()));
            prSt.executeUpdate();
        }

        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Worker worker) {
        ResultSet result = null;
        String delete = "DELETE FROM " + WorkerTableConsts.WORKER_TABLE + " WHERE "
                + "(" + WorkerTableConsts.WORKER_ID +
                " = ? " + ")";
        try {
            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(delete);
            prSt.setString(1, String.valueOf(worker.getId()));
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Worker worker) {
        String update = "UPDATE " + WorkerTableConsts.WORKER_TABLE
                + " SET " + WorkerTableConsts.WORKER_SURNAME + " = ? "
                + "," + WorkerTableConsts.WORKER_NAME + " = ? "
                + "," + WorkerTableConsts.WORKER_LASTNAME + " = ? "
                + "," + WorkerTableConsts.WORKER_POSITION + " = ? "
                + "," + WorkerTableConsts.WORKER_WAGE + " = ? "
                + "," + WorkerTableConsts.WORKER_HOURSWORKED + " = ? "
                + " WHERE " + "(" + WorkerTableConsts.WORKER_ID +
                " = ? " + ")";

        try
        {
            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(update);
            prSt.setString(1, worker.getSurname());
            prSt.setString(2, worker.getName());
            prSt.setString(3, worker.getLastname());
            prSt.setString(4, worker.getPosition());
            prSt.setString(5, String.valueOf(worker.getWage()));
            prSt.setString(6, String.valueOf(worker.getHoursworked()));
            prSt.setString(7, String.valueOf(worker.getId()));
            prSt.executeUpdate();
        } catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }


    @Override
    public ResultSet getWorkerBySurname(Worker worker) {
        ResultSet result = null;

        String select = "SELECT * FROM " + WorkerTableConsts.WORKER_TABLE + " WHERE "
                + WorkerTableConsts.WORKER_SURNAME +
                " = ? " + " AND " + WorkerTableConsts.WORKER_USERID + " = ? ";
        try
        {
            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(select);
            prSt.setString(1,worker.getSurname());
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
    public ResultSet getWorkerByPosition(Worker worker) {
        ResultSet result = null;

        String select = "SELECT * FROM " + WorkerTableConsts.WORKER_TABLE + " WHERE "
                + WorkerTableConsts.WORKER_POSITION +
                " = ? " + " AND " + WorkerTableConsts.WORKER_USERID + " = ? ";
        try
        {
            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(select);
            prSt.setString(1,worker.getPosition());
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
    public ResultSet getWorkerByHoursWorked(Worker worker) {
        ResultSet result = null;

        String select = "SELECT * FROM " + WorkerTableConsts.WORKER_TABLE + " WHERE "
                + WorkerTableConsts.WORKER_HOURSWORKED +
                " = ? " + " AND " + WorkerTableConsts.WORKER_USERID + " = ? ";
        try
        {
            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(select);
            prSt.setString(1, String.valueOf(worker.getHoursworked()));
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
