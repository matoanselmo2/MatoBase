package me.mato.plugin.game.manager;

import me.mato.plugin.MatoBase;
import me.mato.plugin.api.database.DatabaseConfig;
import me.mato.plugin.api.database.DatabaseFactory;
import me.mato.plugin.api.database.DatabaseType;
import me.mato.plugin.api.database.engine.DatabaseEngine;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class DatabaseManager {
    private DatabaseEngine engine;

    public void init(JavaPlugin plugin) {
        DatabaseConfig config = loadConfig(plugin);
        this.engine = DatabaseFactory.createEngine(config, plugin);

        try {
            engine.connect();
            engine.createTables(MatoBase.getInstance().getName()); // Example table creation, adjust as needed
        } catch (SQLException e) {
            plugin.getLogger().severe("Erro ao conectar no banco de dados: " + e.getMessage());
        }
    }

    private DatabaseConfig loadConfig(JavaPlugin plugin) {
        // Load the config file from the plugin's config directory
        YamlConfiguration config = YamlConfiguration.loadConfiguration(plugin.getDataFolder());
        String type = config.getString("database.type", "SQLITE").toUpperCase();
        DatabaseType databaseType = DatabaseType.valueOf(type.toUpperCase());

        switch (databaseType) {
            case MYSQL:
                return new DatabaseConfig (
                        databaseType,
                        config.getString("database.mysql.host", "localhost"),
                        config.getInt("database.mysql.port", 3306),
                        config.getString("database.mysql.username", "root"),
                        config.getString("database.mysql.password", ""),
                        config.getString("database.mysql.database", "my_database")
                );
            case SQLITE:
            default:
                return new DatabaseConfig(
                        databaseType,
                        null,
                        0,
                        null,
                        null,
                        "database.db"
                );
        }
    }

    public DatabaseEngine getEngine() {
        return engine;
    }
}