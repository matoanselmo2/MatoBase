package me.mato.plugin.base.api.database.config;

import me.mato.plugin.base.api.database.DatabaseType;

public interface IDatabaseConfig {

    DatabaseType getDatabaseType();
    String database();

}
