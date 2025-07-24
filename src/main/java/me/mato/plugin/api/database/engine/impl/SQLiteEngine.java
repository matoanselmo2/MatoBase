package me.mato.plugin.api.database.engine.impl;

import me.mato.plugin.MatoBase;
import me.mato.plugin.api.database.engine.DatabaseEngine;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class SQLiteEngine implements DatabaseEngine {
    private Connection connection;
    private final File databaseFile;

    public SQLiteEngine(File dataFolder, String filename) {
        this.databaseFile = new File(dataFolder, filename);
    }

    @Override
    public void connect() throws SQLException {
        String url = "jdbc:sqlite:" + databaseFile.getPath();
        connection = DriverManager.getConnection(url);
    }

    @Override
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                MatoBase.getInstance().getLogger().log(Level.WARNING, e.getMessage());
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
                MatoBase.getInstance().getLogger().log(Level.SEVERE, "Failed to connect to SQLite database: " + e.getMessage());
            }
        }

        return connection;
    }

    @Override
    public void createTables(String table) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + table + " (id INTEGER PRIMARY KEY AUTOINCREMENT, data TEXT)";
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
            return null;
        }
    }

}