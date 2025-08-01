package me.mato.plugin.base.data.database.engine.impl;

import com.zaxxer.hikari.HikariConfig;
import me.mato.plugin.base.data.database.config.impl.MySQLDatabaseConfig;
import me.mato.plugin.base.data.database.engine.AbstractDatabaseEngine;
import org.bukkit.plugin.java.JavaPlugin;

public class MySQLDatabaseEngine extends AbstractDatabaseEngine {
    private final String host;
    private final int port;
    private final String username;
    private final String password;

    public MySQLDatabaseEngine(JavaPlugin plugin, MySQLDatabaseConfig config) {
        super(plugin, config);
        this.host = config.host();
        this.port = config.port();
        this.username = config.username();
        this.password = config.password();
    }

    @Override
    protected String getJdbcUrl() {
        return "jdbc:mysql://" + host + ":" + port + "/" + getDatabaseConfig().database();
    }

    @Override
    protected void configure(HikariConfig config) {
        config.setUsername(username);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("useSSL", "false");
        config.addDataSourceProperty("autoReconnect", "true");
    }
}