package me.mato.plugin.base.data.database.engine.impl;

import me.mato.plugin.base.data.database.config.impl.MySQLDatabaseConfig;
import me.mato.plugin.base.data.database.engine.AbstractDatabaseEngine;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDatabaseEngine extends AbstractDatabaseEngine {
    private final String host;
    private final int port;
    private final String username;
    private final String password;

    public MySQLDatabaseEngine(JavaPlugin plugin, MySQLDatabaseConfig databaseConfig) {
        super(plugin, databaseConfig);

        this.host = databaseConfig.host();
        this.port = databaseConfig.port();
        this.username = databaseConfig.username();
        this.password = databaseConfig.password();
    }

    @Override
    public void connect() throws SQLException {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + getDatabaseConfig().database();
        setConnection(DriverManager.getConnection(url, username, password));
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
            if (getConnection() == null || getConnection().isClosed()) {
                connect();
            }

            getConnection().createStatement().execute(table);
        }
    }
}
