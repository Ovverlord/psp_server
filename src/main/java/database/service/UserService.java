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
    }

    public ResultSet getUser(User user)
    {
        ResultSet result = null;

        String select = "SELECT * FROM " + UserTableConsts.USER_TABLE + " WHERE " + UserTableConsts.USER_LOGIN +
                " = ? AND " + UserTableConsts.USER_PASSWORD + " = ? " ;

        try
        {
            PreparedStatement prSt = null;
            prSt = getDbConnection().prepareStatement(select);

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
    @Override
    public List<User> getAll() {

        return null;
    }

    @Override
    public User getByID(Integer id) {
        return null;
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(User user) {

    }
}
