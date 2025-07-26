package me.mato.plugin.base.command;

import java.util.*;

public abstract class BaseCommand {

    private final String name;
    private final String description;
    private final Map<String, SubCommand> subCommands = new HashMap<>();

    public BaseCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }

    public abstract void execute(CommandContext context);

    public abstract List<String> tab(CommandContext context);

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

    public Map<String, SubCommand> getSubCommands() {
        return Collections.unmodifiableMap(subCommands);
    }
}