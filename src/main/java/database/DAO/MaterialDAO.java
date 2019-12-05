package database.DAO;

import classes.Material;

import java.sql.ResultSet;

public interface MaterialDAO {
    void add(Material material);
    void update(Material material);
    void delete(Material material);
    ResultSet getAllMaterials();
    ResultSet getMaterialByName(Material material);
    ResultSet getMaterialByUnitCost(Material material);
    ResultSet getMaterialByUsedAmount(Material material);
}
