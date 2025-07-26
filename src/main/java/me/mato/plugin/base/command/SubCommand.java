package me.mato.plugin.base.command;

import java.util.List;

public interface SubCommand {
    String name();

    void execute(CommandContext context);

    default List<String> tabComplete(CommandContext context) {
        return List.of();
    }
}
