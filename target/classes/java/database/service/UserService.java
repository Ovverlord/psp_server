package database.service;

import classes.User;
import database.DAO.UserDAO;
import database.configs.DBHandler;
import database.consts.UserTableConsts;

import java.sql.*;
import java.util.List;

public class UserService extends DBHandler implements UserDAO {

    Connection dbConnection = getDbConnection();
    @Override
    public void add(User user) {

        String insert = "INSERT INTO " + UserTableConsts.USER_TABLE + "("
                + UserTableConsts.USER_LOGIN + "," + UserTableConsts.USER_PASSWORD +
                "," + UserTableConsts.isAdmin + ")"
                + "VALUES (?,?,?)";
        try
        {

            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(insert);

            prSt.setString(1,user.getLogin());
            prSt.setString(2,user.getPassword());
            if(user.isAdmin()==null){
                prSt.setString(3,"0");
            }
            else{
                prSt.setString(3, String.valueOf(user.isAdmin()));
            }

            prSt.executeUpdate();
        }

        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public ResultSet getUser(User user)
    {
        ResultSet result = null;

        String select = "SELECT * FROM " + UserTableConsts.USER_TABLE + " WHERE " + UserTableConsts.USER_LOGIN +
                " = ? AND " + UserTableConsts.USER_PASSWORD + " = ? " ;
        try
        {
            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(select);

            prSt.setString(1,user.getLogin());
            prSt.setString(2,user.getPassword());

            result = prSt.executeQuery();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }

        return result;
    }

    public ResultSet getUserByLogin(User user)
    {
        ResultSet result = null;

        String select = "SELECT * FROM " + UserTableConsts.USER_TABLE + " WHERE " + UserTableConsts.USER_LOGIN +
                " = ? ";
        try
        {
            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(select);
            prSt.setString(1,user.getLogin());
            result = prSt.executeQuery();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }

        return result;
    }

    @Override
    public ResultSet getAllUsers() {

        ResultSet result = null;
        String select = "SELECT * FROM " + UserTableConsts.USER_TABLE;
        try
        {
            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(select);
            result = prSt.executeQuery(select);
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public String update(User user) {
        String update = "UPDATE " + UserTableConsts.USER_TABLE
                + " SET " + UserTableConsts.USER_LOGIN + " = ? "
                + "," + UserTableConsts.USER_PASSWORD + " = ? "
                + " WHERE " + "(" + UserTableConsts.USER_ID +
                " = ? " + ")";

        try
        {
            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(update);
            prSt.setString(1,user.getLogin());
            prSt.setString(2,user.getPassword());
            prSt.setString(3, String.valueOf(user.getId()));
            prSt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException ex)
        {
            return "error";
        } catch(SQLException ex)
        {
            ex.printStackTrace();
            return "error";
        }
        return "successfull";
    }

    @Override
    public void delete(User user) {
        ResultSet result = null;
        String delete = "DELETE FROM " + UserTableConsts.USER_TABLE + " WHERE "
                + "(" + UserTableConsts.USER_LOGIN +
                " = ? " + ")";
        try {
            PreparedStatement prSt = null;
            prSt = dbConnection.prepareStatement(delete);
            prSt.setString(1,user.getLogin());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
