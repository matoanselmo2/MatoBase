package me.mato.plugin.base.api.permission;

import org.bukkit.permissions.PermissionDefault;

import java.util.LinkedHashMap;
import java.util.Map;

public class PermissionBuilder {

    private final String permission;
    private String description = "Sem descrição.";
    private PermissionDefault defaultValue = PermissionDefault.FALSE;
    private final Map<String, Boolean> children = new LinkedHashMap<>();

    public PermissionBuilder(String permission) {
        this.permission = permission;
    }

    public static PermissionBuilder create(String permission) {
        return new PermissionBuilder(permission);
    }

    public PermissionBuilder description(String description) {
        this.description = description;
        return this;
    }

    public PermissionBuilder defaultValue(PermissionDefault defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public PermissionBuilder child(String permission, boolean value) {
        this.children.put(permission, value);
        return this;
    }

    public String getPermission() {
        return permission;
    }

    public String getDescription() {
        return description;
    }

    public PermissionDefault getDefaultValue() {
        return defaultValue;
    }

    public Map<String, Boolean> getChildren() {
        return children;
    }

    public void register() {
        if (org.bukkit.Bukkit.getPluginManager().getPermission(permission) != null) return;

        org.bukkit.permissions.Permission perm = new org.bukkit.permissions.Permission(
                permission, description, defaultValue
        );

        perm.getChildren().putAll(children);
        perm.recalculatePermissibles();

        org.bukkit.Bukkit.getPluginManager().addPermission(perm);
    }
}

