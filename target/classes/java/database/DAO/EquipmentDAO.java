package database.DAO;

import classes.Equipment;

import java.sql.ResultSet;

public interface EquipmentDAO {
    void add(Equipment equipment);
    void update(Equipment equipment);
    void delete(Equipment equipment);
    ResultSet getAllEquipment();
    ResultSet getEquipmentByName(Equipment equipment);
    ResultSet getEquipmentByHoursWorked(Equipment equipment);
}
