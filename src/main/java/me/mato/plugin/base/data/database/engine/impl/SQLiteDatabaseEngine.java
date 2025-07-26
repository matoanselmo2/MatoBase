package me.mato.plugin.base.data.database.engine.impl;

import me.mato.plugin.base.data.database.config.impl.SQLiteDatabaseConfig;
import me.mato.plugin.base.data.database.engine.AbstractDatabaseEngine;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDatabaseEngine extends AbstractDatabaseEngine {
    private final String databasePath;

    public SQLiteDatabaseEngine(JavaPlugin plugin, SQLiteDatabaseConfig databaseConfig) {
        super(plugin, databaseConfig);

        this.databasePath = plugin.getDataFolder().getPath() + "/" + databaseConfig.database();
    }

    @Override
    public void connect() throws SQLException {
        String url = "jdbc:sqlite:" + databasePath;
        setConnection(DriverManager.getConnection(url));
    }

    @Override
    public void disconnect() throws SQLException {
        if (getConnection() != null) {
            getConnection().close();
        }

        setConnection(null);
    }

    @Override
    public void createTables(String... tables) throws SQLException {
        for (String table : tables) {
            String createTableSQL = table;

            if (getConnection() == null || getConnection().isClosed()) {
                connect();
            }

            getConnection().createStatement().execute(createTableSQL);
        }
    }

}
