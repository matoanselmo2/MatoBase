package me.mato.plugin.base.data.database.config;

import me.mato.plugin.base.MatoBase;
import me.mato.plugin.base.data.database.DatabaseType;
import me.mato.plugin.base.data.database.config.impl.MySQLDatabaseConfig;
import me.mato.plugin.base.data.database.config.impl.SQLiteDatabaseConfig;

public class DatabaseConfigLoader {
    public static IDatabaseConfig load(MatoBase plugin) {
        plugin.saveDefaultConfig();
        var config = plugin.getConfig();
        var type = DatabaseType.valueOf(config.getString("database.type", "SQLITE"));

        return switch (type) {
            case MYSQL -> new MySQLDatabaseConfig(
                    config.getString("database.mysql.host"),
                    config.getInt("database.mysql.port"),
                    config.getString("database.mysql.database"),
                    config.getString("database.mysql.username"),
                    config.getString("database.mysql.password")
            );
            case SQLITE -> new SQLiteDatabaseConfig(
                    config.getString("database.sqlite.database")
            );
        };
    }
}
