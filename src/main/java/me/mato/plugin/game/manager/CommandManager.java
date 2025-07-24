package me.mato.plugin.game.manager;

import me.mato.plugin.MatoBase;
import me.mato.plugin.api.command.CommandBase;
import me.mato.plugin.game.command.main.MainCommand;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private final JavaPlugin plugin;
    private final Map<String, CommandBase> commands = new HashMap<>();

    public CommandManager(JavaPlugin plugin) {
        this.plugin = plugin;

        plugin.getCommand(MatoBase.getInstance().getName()).setExecutor(new MainCommand());
    }

    public void registerCommand(CommandBase command) {
        commands.put(command.getName(), command);
        PluginCommand cmd = plugin.getCommand(command.getName());
        if (cmd != null) {
            cmd.setExecutor(command);
            cmd.setTabCompleter(command);
        }
    }
}