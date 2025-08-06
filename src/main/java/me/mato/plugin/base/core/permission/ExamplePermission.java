package me.mato.plugin.base.core.permission;

import me.mato.plugin.base.api.permission.PermissionBuilder;
import me.mato.plugin.base.api.permission.PermissionNode;
import org.bukkit.permissions.PermissionDefault;

public enum ExamplePermission implements PermissionNode {
    ADMIN(PermissionBuilder.create("matobase.admin")
            .description("Permite usar os comandos administrativos do MatoBase")
            .defaultValue(PermissionDefault.OP)),

    USE(PermissionBuilder.create("matobase.use")
            .description("Permite usar o MatoBase")
            .defaultValue(PermissionDefault.TRUE)),

    OTHER(PermissionBuilder.create("matobase.other")
            .description("Exemplo de permissão com filhos")
            .defaultValue(PermissionDefault.FALSE)
            .child("matobase.other.example", true)
            .child("matobase.other.coolperm", true)
    );

    private final PermissionBuilder builder;

    ExamplePermission(PermissionBuilder builder) {
        this.builder = builder;
    }

    public PermissionBuilder getBuilder() {
        return builder;
    }

    @Override
    public String getPermission() {
        return builder.getPermission();
    }

    @Override
    public String getDescription() {
        return builder.getDescription();
    }

    @Override
    public PermissionDefault getDefaultValue() {
        return builder.getDefaultValue();
    }

    public void register() {
        builder.register();
    }
}