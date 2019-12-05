package database.DAO;

import classes.Worker;

import java.sql.ResultSet;

public interface WorkerDAO {
    void add(Worker worker);
    void update(Worker worker);
    void delete(Worker worker);
    ResultSet getAllWorkers();
    ResultSet getWorkerBySurname(Worker worker);
    ResultSet getWorkerByPosition(Worker worker);
    ResultSet getWorkerByHoursWorked(Worker worker);
}
