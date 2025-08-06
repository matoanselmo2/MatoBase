package me.mato.plugin.base.api.permission;

import org.bukkit.permissions.PermissionDefault;

public interface PermissionNode {
    String getPermission();              // matobase.use
    String getDescription();             // Usar matobase
    PermissionDefault getDefaultValue(); // TRUE, OP, etc
}

