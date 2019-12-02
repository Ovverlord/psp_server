package database.DAO;

import classes.User;

import java.sql.ResultSet;
import java.util.List;

public interface UserDAO {
    void add(User user);

    ResultSet getUser(User user);
    List<User> getAll();
    User getByID(Integer id);

    void update(User user);

    void delete(User user);
}
