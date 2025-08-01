package me.mato.plugin.base.util;

public enum Permission {
    ADMIN("admin"),
    EXEMPLO("exemplo");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getId() {
        return permission;
    }

    public String getPermission() {
        return "matobase." + permission;
    }

    public String getPermissions(Permission... permissions) {
        StringBuilder sb = new StringBuilder();
        for (Permission perm : permissions) {
            if (!sb.isEmpty()) {
                sb.append(", ");
            }

            sb.append(perm.getPermission());
        }
        return sb.toString();
    }
}
