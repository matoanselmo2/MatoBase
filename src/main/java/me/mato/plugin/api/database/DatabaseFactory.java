package me.mato.plugin.api.database;

import me.mato.plugin.api.database.engine.DatabaseEngine;
import me.mato.plugin.api.database.engine.impl.MySQLEngine;
import me.mato.plugin.api.database.engine.impl.SQLiteEngine;
import org.bukkit.plugin.java.JavaPlugin;

public class DatabaseFactory {
    public static DatabaseEngine createEngine(DatabaseConfig config, JavaPlugin plugin) {
        switch (config.getType()) {
            case MYSQL:
                return new MySQLEngine(config);
            case SQLITE:
            default:
                return new SQLiteEngine(plugin.getDataFolder(), "database.db");
        }
    }
}