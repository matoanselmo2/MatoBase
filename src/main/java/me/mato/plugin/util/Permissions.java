package me.mato.plugin.util;

import me.mato.plugin.MatoBase;

public enum Permissions {
    // Remember to define permissions in the plugin's plugin.yml

    ADMIN("admin");

    private final String permission;

    Permissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return MatoBase.getInstance().getName().toLowerCase() + permission;
        // Example: "matobase.admin"
    }
}
