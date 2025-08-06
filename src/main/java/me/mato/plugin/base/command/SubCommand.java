package me.mato.plugin.base.command;

import me.mato.plugin.base.api.permission.PermissionNode;

import java.util.List;

public interface SubCommand {
    String name();
    PermissionNode permission();

    void execute(CommandContext context);

    default List<String> tabComplete(CommandContext context) {
        return List.of();
    }
}
