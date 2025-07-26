package me.mato.plugin.base.data.database.config.impl;

import me.mato.plugin.base.data.database.DatabaseType;
import me.mato.plugin.base.data.database.config.IDatabaseConfig;

public record SQLiteDatabaseConfig(String database) implements IDatabaseConfig {

    @Override
    public DatabaseType getDatabaseType() {
        return DatabaseType.SQLITE;
    }
}
