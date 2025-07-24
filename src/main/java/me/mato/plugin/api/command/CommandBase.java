package me.mato.plugin.api.command;

import me.mato.plugin.MatoBase;
import me.mato.plugin.api.config.ConfigHelper;
import me.mato.plugin.util.Argument;
import me.mato.plugin.util.Permissions;
import org.bukkit.command.*;

public abstract class CommandBase implements CommandExecutor, TabCompleter {
    protected MatoBase plugin = MatoBase.getInstance();

    private final String name;
    private final String permission;
    private final String description;
    private final Argument[] args;

    public CommandBase(String name, Permissions permission, Argument... args) {
        this.name = name;
        this.permission = permission.getPermission();
        this.description = plugin.getConfigHelper().getMessage("command.no-description", null);
        this.args = args;
    }

    public CommandBase(String name, String permission, String description, Argument... args) {
        this.name = name;
        this.permission = permission;
        this.description = description;
        this.args = args;
    }

    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(permission);
    }

    protected abstract void execute(CommandSender sender, Object[] parsedArgs) throws CommandException;

    protected Object[] parseArguments(CommandSender sender, String[] rawArgs) throws CommandException {
        ConfigHelper config = MatoBase.getInstance().getConfigHelper();

        if (rawArgs.length < args.length) {
            throw new CommandException(config.getMessage("command.insufficient-args",
                    sender,
                    "usage", getUsage(),
                    "expected", args.length,
                    "received", rawArgs.length
            ));
        }

        Object[] parsedArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            try {
                parsedArgs[i] = args[i].parse(sender, rawArgs[i]);
            } catch (IllegalArgumentException e) {
                throw new CommandException(e.getMessage());
            }
        }
        return parsedArgs;
    }

    public String getUsage() {
        StringBuilder sb = new StringBuilder();
        for (Argument arg : args) {
            sb.append("<").append(arg.name().toLowerCase()).append("> ");
        }
        return sb.toString().trim();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
