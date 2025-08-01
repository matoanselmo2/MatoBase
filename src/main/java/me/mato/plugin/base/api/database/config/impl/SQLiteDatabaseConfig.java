package me.mato.plugin.base.api.database.config.impl;

import me.mato.plugin.base.api.database.DatabaseType;
import me.mato.plugin.base.api.database.config.IDatabaseConfig;

public record SQLiteDatabaseConfig(String database) implements IDatabaseConfig {

    @Override
    public DatabaseType getDatabaseType() {
        return DatabaseType.SQLITE;
    }
}
