package me.mato.plugin.api.command.impl;

import me.mato.plugin.api.command.CommandBase;
import me.mato.plugin.util.Argument;
import me.mato.plugin.util.CommandUtil;
import me.mato.plugin.util.Permissions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public abstract class NormalCommand extends CommandBase {
    public NormalCommand(String name, Permissions permission, Argument... args) {
        super(name, permission, args);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!hasPermission(sender)) {
            CommandUtil.sendMessage(sender, plugin.getConfigHelper().getMessage("no-permission", sender));
            return true;
        }

        try {
            Object[] parsedArgs = parseArguments(sender, args);
            execute(sender, parsedArgs);
        } catch (CommandException e) {
            CommandUtil.sendMessage(sender, "§c" + e.getMessage());
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return Collections.emptyList();
    }
}