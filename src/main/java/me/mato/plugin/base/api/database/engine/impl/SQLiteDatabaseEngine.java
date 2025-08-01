package me.mato.plugin.base.api.database.engine.impl;

import com.zaxxer.hikari.HikariConfig;
import me.mato.plugin.base.api.database.config.impl.SQLiteDatabaseConfig;
import me.mato.plugin.base.api.database.engine.AbstractDatabaseEngine;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class SQLiteDatabaseEngine extends AbstractDatabaseEngine {
    private final String databasePath;

    public SQLiteDatabaseEngine(JavaPlugin plugin, SQLiteDatabaseConfig config) {
        super(plugin, config);
        this.databasePath = new File(plugin.getDataFolder(), config.database()).getPath();
    }

    @Override
    protected String getJdbcUrl() {
        return "jdbc:sqlite:" + databasePath;
    }

    @Override
    protected void configure(HikariConfig config) {
        config.setConnectionTestQuery("SELECT 1"); // SQLite doesn't support some validation methods
        config.setMaximumPoolSize(1); // SQLite = single-connection engine
    }
}
