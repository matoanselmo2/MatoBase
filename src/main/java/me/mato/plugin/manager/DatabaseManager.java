package me.mato.plugin.manager;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager (JavaPlugin plugin) {
        File dataFolder = new File(plugin.getDataFolder(), "data.db");
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder.getAbsolutePath());

            createTable("TEST",
                        "id INTEGER PRIMARY KEY AUTOINCREMENT",
                        "name TEXT NOT NULL",
                        "value INTEGER NOT NULL"); // Exemplo de tabela (Altere conforme necessário e/ou adicione mais tabelas)

        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void executeUpdate(String sql, Object... params) {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // criar métodos para consultas, fechamentos, etc.
    public Connection getConnection() {
        return connection;
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void createTable(String tableName, String... columns) {
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + " (");
        for (String column : columns) {
            sql.append(column).append(", ");
        }
        sql.setLength(sql.length() - 2); // Remove última vírgula
        sql.append(");");

        executeUpdate(sql.toString());
    }
}