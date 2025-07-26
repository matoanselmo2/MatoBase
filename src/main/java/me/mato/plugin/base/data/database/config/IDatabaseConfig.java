package me.mato.plugin.base.data.database.config;

import me.mato.plugin.base.data.database.DatabaseType;

public interface IDatabaseConfig {

    DatabaseType getDatabaseType();
    String database();

}
