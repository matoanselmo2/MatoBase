package me.mato.plugin.base.api.database;

import me.mato.plugin.base.api.database.config.IDatabaseConfig;
import me.mato.plugin.base.api.database.config.impl.MySQLDatabaseConfig;
import me.mato.plugin.base.api.database.config.impl.SQLiteDatabaseConfig;
import me.mato.plugin.base.api.database.engine.AbstractDatabaseEngine;
import me.mato.plugin.base.api.database.engine.impl.MySQLDatabaseEngine;
import me.mato.plugin.base.api.database.engine.impl.SQLiteDatabaseEngine;
import org.bukkit.plugin.java.JavaPlugin;

public class DatabaseFactory {

    public static AbstractDatabaseEngine createDatabaseEngine(JavaPlugin plugin, IDatabaseConfig databaseConfig) {
        switch (databaseConfig.getDatabaseType()) {
            case MYSQL -> {
                if (databaseConfig instanceof MySQLDatabaseConfig mySQLDatabaseConfig) {
                    return new MySQLDatabaseEngine(plugin, mySQLDatabaseConfig);
                }
                throw new IllegalArgumentException("Configuração inválida para MySQL");
            }
            case SQLITE -> {
                if (databaseConfig instanceof SQLiteDatabaseConfig sqliteDatabaseConfig) {
                    return new SQLiteDatabaseEngine(plugin, sqliteDatabaseConfig);
                }
                throw new IllegalArgumentException("Configuração inválida para SQLite");
            }
            default -> throw new UnsupportedOperationException("Tipo de banco de dados não suportado: " + databaseConfig.getDatabaseType());
        }
    }

}