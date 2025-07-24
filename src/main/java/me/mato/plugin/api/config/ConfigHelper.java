package me.mato.plugin.api.config;

import me.mato.plugin.util.Placeholder;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ConfigHelper {
    private final JavaPlugin plugin;
    private final Placeholder placeholder;
    private YamlConfiguration config, messages;

    public ConfigHelper(JavaPlugin plugin, Placeholder placeholder) {
        this.plugin = plugin;
        this.placeholder = placeholder;

        reload();
        saveDefaultConfig();
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
        messages = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "messages.yml"));
    }

    public String getMessage(String key, CommandSender sender, Object... replacements) {
        String message = messages.getString(key, "&cMessage not found: " + key);

        // Substitui placeholders %key%
        for (int i = 0; i < replacements.length; i += 2) {
            if (i+1 >= replacements.length) break;
            message = message.replace("%" + replacements[i] + "%",
                    replacements[i+1].toString());
        }

        // Placeholders globais (opcional)
        message = placeholder.resolve(sender, message);

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public String getConfigValue(String key, String defaultValue) {
        return config.getString(key, defaultValue);
    }

    public void saveDefaultConfig() {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        File messagesFile = new File(plugin.getDataFolder(), "messages.yml");

        if (configFile.exists()) {
            plugin.getLogger().info("Loading existing config.yml...");
        } else {
            plugin.saveResource("config.yml", false);
            plugin.getLogger().info("Creating default config.yml...");
        }

        if (messagesFile.exists()) {
            plugin.getLogger().info("Loading existing messages.yml...");
        } else {
            plugin.saveResource("messages.yml", false);
            plugin.getLogger().info("Creating default messages.yml...");
        }
    }
}