package me.mato.plugin;

import me.mato.plugin.api.config.ConfigHelper;
import me.mato.plugin.game.manager.CommandManager;
import me.mato.plugin.game.manager.DatabaseManager;
import me.mato.plugin.util.Placeholder;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class MatoBase extends JavaPlugin {

    private static MatoBase instance;

    private CommandManager commandManager;
    private DatabaseManager databaseManager;
    private ConfigHelper configHelper;

    private Placeholder placeholder;

    @Override
    public void onEnable() {
        instance = this;

        placeholder = new Placeholder();
        registerDefaultPlaceholders();

        configHelper = new ConfigHelper(this, placeholder);
        commandManager = new CommandManager(this);

        databaseManager = new DatabaseManager();
        databaseManager.init(this);

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getLogger().info("PlaceholderAPI found! Enabling placeholders.");
        } else {
            getLogger().warning("PlaceholderAPI not found! Placeholders will not work.");
        }
    }

    private void registerDefaultPlaceholders() {
        placeholder.register("player", sender -> {
            if (sender instanceof Player) {
                return sender.getName();
            }
            return "Console";
        });

        placeholder.register("n", sender -> "\n");
    }

    public static MatoBase getInstance() {
        return instance;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public ConfigHelper getConfigHelper() {
        return configHelper;
    }
}
