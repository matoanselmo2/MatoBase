package me.mato.plugin.base.data.database.config.impl;

import me.mato.plugin.base.data.database.DatabaseType;
import me.mato.plugin.base.data.database.config.IDatabaseConfig;

public record MySQLDatabaseConfig(String host, int port, String username, String password, String database) implements IDatabaseConfig {

    @Override
    public DatabaseType getDatabaseType() {
        return DatabaseType.MYSQL;
    }
}
