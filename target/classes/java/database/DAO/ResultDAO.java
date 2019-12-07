package database.DAO;

import classes.Result;

import java.sql.ResultSet;

public interface ResultDAO {
    Result calculateCost(Result result);
    void saveResult(Result result);
    ResultSet getAllResults();
    ResultSet getResultByCost(Result result);
    ResultSet getResultByWageCost(Result result);
    ResultSet getResultByMaterialCost(Result result);
    void delete(Result result);
    void generateReport(Result result);
}
