package me.mato.plugin.base.command;

import me.mato.plugin.base.util.Permission;

import java.util.*;

public abstract class BaseCommand {

    private final String name;
    private final String description;
    private final Permission permission;
    private final Map<String, SubCommand> subCommands = new HashMap<>();

    public BaseCommand(String name, String description, Permission permission) {
        this.name = name;
        this.description = description;
        this.permission = permission;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }

    public abstract void execute(CommandContext context);

    public List<String> tab(CommandContext context) {
        if (context.args().length == 0) {
            return List.of();
        }

        if (context.args().length == 1) {
            return subCommands.keySet().stream()
                    .filter(cmd -> cmd.startsWith(context.args()[0].toLowerCase()))
                    .sorted()
                    .toList();
        }

        String subName = context.args()[0].toLowerCase();
        SubCommand sub = subCommands.get(subName);

        if (sub != null) {
            return sub.tabComplete(context.subContext());
        }

        return List.of();
    }

    public void registerSubCommand(SubCommand sub) {
        subCommands.put(sub.name().toLowerCase(), sub);
    }

    public Optional<SubCommand> findSubCommand(String arg) {
        return Optional.ofNullable(subCommands.get(arg.toLowerCase()));
    }

    public List<String> subcommandTab(CommandContext ctx) {
        if (ctx.args().length == 1) {
            return subCommands.keySet().stream()
                    .filter(cmd -> cmd.startsWith(ctx.args()[0].toLowerCase()))
                    .sorted()
                    .toList();
        }

        if (ctx.args().length >= 2) {
            String subName = ctx.args()[0].toLowerCase();
            SubCommand sub = subCommands.get(subName);
            if (sub != null) {
                return sub.tabComplete(ctx.subContext());
            }
        }

        return List.of();
    }

    public Permission permission() {
        return permission;
    }

    public Map<String, SubCommand> getSubCommands() {
        return Collections.unmodifiableMap(subCommands);
    }
}