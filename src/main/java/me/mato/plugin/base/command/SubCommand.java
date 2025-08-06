package me.mato.plugin.base.command;

import me.mato.plugin.base.util.Permission;

import java.util.List;

public interface SubCommand {
    String name();
    Permission permission();

    void execute(CommandContext context);

    default List<String> tabComplete(CommandContext context) {
        return List.of();
    }
}
