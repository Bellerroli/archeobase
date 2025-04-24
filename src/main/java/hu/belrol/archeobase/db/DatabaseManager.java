package hu.belrol.archeobase.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hu.belrol.archeobase.base.PropertiesManager;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {
    private static HikariDataSource ds;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(PropertiesManager.getProperty("db.url"));
        hikariConfig.setMaximumPoolSize(PropertiesManager.getIntProperty("pool.size"));
        hikariConfig.setPoolName("base-pool");

        ds = new HikariDataSource(hikariConfig);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
