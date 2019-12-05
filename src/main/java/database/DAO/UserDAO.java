package database.DAO;

import classes.User;

import java.sql.ResultSet;
import java.util.List;

public interface UserDAO {
    void add(User user);
    String update(User user);
    void delete(User user);
    ResultSet getUser(User user);
    ResultSet getUserByLogin(User user);
    ResultSet getAllUsers();
}
