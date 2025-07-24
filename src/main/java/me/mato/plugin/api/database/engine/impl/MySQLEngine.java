package me.mato.plugin.api.database.engine.impl;

import me.mato.plugin.MatoBase;
import me.mato.plugin.api.database.DatabaseConfig;
import me.mato.plugin.api.database.engine.DatabaseEngine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class MySQLEngine implements DatabaseEngine {
    private Connection connection;
    private final DatabaseConfig config;

    public MySQLEngine(DatabaseConfig config) {
        this.config = config;
    }

    @Override
    public void connect() throws SQLException {
        String url = "jdbc:mysql://" + config.getHost() + ":" + config.getPort() + "/" + config.getDatabase();
        connection = DriverManager.getConnection(url, config.getUsername(), config.getPassword());
    }

    @Override
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                MatoBase.getInstance().getLogger().log(Level.SEVERE, "Failed to close connection: " + e.getMessage());
            }
        }

        connection = null;
    }

    @Override
    public Connection getConnection() {
        if (connection == null) {
            try {
                connect();
            } catch (SQLException e) {
                MatoBase.getInstance().getLogger().log(Level.SEVERE, "Failed to connect to MySQL database: " + e.getMessage());
                throw new RuntimeException("Database connection failed", e);
            }
        }

        return connection;
    }

    @Override
    public void createTables(String table) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + table + " (id INT AUTO_INCREMENT PRIMARY KEY, data TEXT)";
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
            connection.createStatement().execute(createTableSQL);
        } catch (SQLException e) {
            MatoBase.getInstance().getLogger().log(Level.SEVERE, "Failed to create table: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void executeUpdate(String query) {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
            connection.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            MatoBase.getInstance().getLogger().log(Level.SEVERE, "Failed to execute update: " + e.getMessage());
            throw new RuntimeException("Database update failed", e);
        }
    }

    @Override
    public ResultSet executeQuery(String query) {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
            return connection.createStatement().executeQuery(query);
        } catch (SQLException e) {
            MatoBase.getInstance().getLogger().log(Level.SEVERE, "Failed to execute query: " + e.getMessage());
            throw new RuntimeException("Database query failed", e);
        }
    }
}
