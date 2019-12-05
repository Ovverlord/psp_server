package database.DAO;

import classes.Tariff;

import java.sql.ResultSet;

public interface TariffDAO {
    void add(Tariff tariff);
    void update(Tariff tariff);
    void delete(Tariff tariff);
    ResultSet getTariff();
}
