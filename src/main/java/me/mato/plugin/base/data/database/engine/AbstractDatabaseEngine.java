package me.mato.plugin.base.data.database.engine;

import me.mato.plugin.base.data.database.config.IDatabaseConfig;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;
import java.util.function.Consumer;

public abstract class AbstractDatabaseEngine {

    protected final IDatabaseConfig databaseConfig;
    protected final JavaPlugin plugin;
    private Connection connection;

    public AbstractDatabaseEngine(JavaPlugin plugin, IDatabaseConfig databaseConfig) {
        this.plugin = plugin;
        this.databaseConfig = databaseConfig;
    }

    // Mesma lógica, mas:
    protected void logError(String message) {
        plugin.getLogger().severe(message);
    }

    public abstract void connect() throws SQLException;

    public abstract void disconnect() throws SQLException;

    public abstract void createTables(String... tables) throws SQLException;

    public void update(String sql, Consumer<PreparedStatement> handler) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            handler.accept(stmt);
            stmt.executeUpdate();

        } catch (SQLException e) {
            logError("Failed to execute update: " + e.getMessage());
        }
    }

    public void query(String sql, Consumer<ResultSet> handler) {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            handler.accept(rs);

        } catch (SQLException e) {
            logError("Failed to execute query: " + e.getMessage());
        }
    }

    public IDatabaseConfig getDatabaseConfig() {
        return databaseConfig;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }

            return connection;
        } catch (SQLException e) {
            logError("Failed to connect to the database: " + e.getMessage());
            throw new IllegalStateException("Database connection is not available", e);
        }
    }
}
