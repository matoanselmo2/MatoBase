package me.mato.plugin.base.manager;

import com.google.inject.Inject;
import me.mato.plugin.base.MatoBase;
import me.mato.plugin.base.command.BaseCommand;
import me.mato.plugin.base.registry.PluginCommandWrapper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import java.lang.reflect.Field;
import java.util.Set;

public class CommandManager {

    private final MatoBase plugin;
    private final Set<BaseCommand> commands;

    @Inject
    public CommandManager(MatoBase plugin, Set<BaseCommand> commands) {
        this.plugin = plugin;
        this.commands = commands;
    }

    public void registerCommands() {
        CommandMap commandMap = getCommandMap();

        for (BaseCommand cmd : commands) {
            PluginCommandWrapper wrapper = new PluginCommandWrapper(plugin, cmd);
            commandMap.register(plugin.getName(), wrapper);
        }
    }

    private CommandMap getCommandMap() {
        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            return (CommandMap) commandMapField.get(Bukkit.getServer());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Não foi possível acessar o CommandMap via reflection", e);
        }
    }
}
