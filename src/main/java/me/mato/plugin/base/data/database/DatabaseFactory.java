package me.mato.plugin.base.data.database;

import me.mato.plugin.base.data.database.config.IDatabaseConfig;
import me.mato.plugin.base.data.database.config.impl.MySQLDatabaseConfig;
import me.mato.plugin.base.data.database.config.impl.SQLiteDatabaseConfig;
import me.mato.plugin.base.data.database.engine.AbstractDatabaseEngine;
import me.mato.plugin.base.data.database.engine.impl.MySQLDatabaseEngine;
import me.mato.plugin.base.data.database.engine.impl.SQLiteDatabaseEngine;
import org.bukkit.plugin.java.JavaPlugin;

public class DatabaseFactory {

    public static AbstractDatabaseEngine createDatabaseEngine(JavaPlugin plugin, IDatabaseConfig databaseConfig) {
        return switch (databaseConfig.getDatabaseType()) {
            case MYSQL -> {
                if (!(databaseConfig instanceof MySQLDatabaseConfig mySQLDatabaseConfig)) {
                    throw new IllegalArgumentException("Invalid database config for MySQL");
                }

                yield new MySQLDatabaseEngine(plugin, mySQLDatabaseConfig);
            }
            default -> {
                if (!(databaseConfig instanceof SQLiteDatabaseConfig sqliteDatabaseConfig)) {
                    throw new IllegalArgumentException("Invalid database config for SQLite");
                }

                yield new SQLiteDatabaseEngine(plugin, sqliteDatabaseConfig);
            }
        };
    }

}
