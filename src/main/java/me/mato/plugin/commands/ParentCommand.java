package me.mato.plugin.commands;

import me.mato.plugin.util.CommandUtil;
import me.mato.plugin.util.Permissions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

import java.util.*;

public abstract class ParentCommand extends CommandBase {
    protected final Map<String, CommandBase> subCommands = new HashMap<>();

    public ParentCommand(String name, Permissions permission) {
        super(name, permission);
    }

    public void registerSubCommand(CommandBase subCommand) {
        subCommands.put(subCommand.getName().toLowerCase(), subCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            return executeAsNormalCommand(sender);
        }

        CommandBase subCommand = subCommands.get(args[0].toLowerCase());
        if (subCommand != null) {
            if (!subCommand.hasPermission(sender)) {
                CommandUtil.sendMessage(sender, plugin.getConfigHelper().getMessage("no-permission", sender));
                return true;
            }

            try {
                String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
                if (subCommand instanceof NormalCommand) {
                    return subCommand.onCommand(sender, cmd, label, subArgs);
                } else {
                    executeSubCommand(sender, subCommand, subArgs);
                }
            } catch (Exception e) {
                CommandUtil.sendMessage(sender, "§cerr: " + e.getMessage());
            }
            return true;
        }

        return executeAsNormalCommand(sender);
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            List<String> subCommandNames = new ArrayList<>(subCommands.keySet());
            Collections.sort(subCommandNames);
            return subCommandNames;
        }

        String subCommandName = strings[0].toLowerCase();
        CommandBase subCommand = subCommands.get(subCommandName);
        if (subCommand != null) {
            return subCommand.onTabComplete(commandSender, command, s, Arrays.copyOfRange(strings, 1, strings.length));
        }

        return Collections.emptyList();
    }

    protected boolean executeAsNormalCommand(CommandSender sender) {
        try {
            execute(sender, new Object[0]);
        } catch (CommandException e) {
            sendHelp(sender);
        }
        return true;
    }

    protected void executeSubCommand(CommandSender sender, CommandBase subCommand, String[] args) throws CommandException {
        Object[] parsedArgs = subCommand.parseArguments(sender, args);
        subCommand.execute(sender, parsedArgs);
    }

    protected void sendHelp(CommandSender sender) {
        CommandUtil.sendMessage(sender, plugin.getConfigHelper().getMessage("help-header", sender));
        subCommands.forEach((name, cmd) -> CommandUtil.sendMessage(sender, "§7- /" + this.getName() + " " + name + " " + cmd.getUsage()));
    }
}