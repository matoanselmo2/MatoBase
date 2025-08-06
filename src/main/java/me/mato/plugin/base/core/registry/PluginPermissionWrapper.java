package me.mato.plugin.base.core.registry;

import me.mato.plugin.base.api.permission.PermissionNode;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.FileWriter;
import java.util.Set;

@Singleton
public class PluginPermissionWrapper {

    private final Plugin plugin;
    private final Set<PermissionNode[]> permissionGroups;

    @Inject
    public PluginPermissionWrapper(Plugin plugin, Set<PermissionNode[]> permissionGroups) {
        this.plugin = plugin;
        this.permissionGroups = permissionGroups;
    }

    public void registerAll() {
        for (PermissionNode[] group : permissionGroups) {
            for (PermissionNode node : group) {
                if (node instanceof Enum<?> en) {
                    try {
                        var method = en.getClass().getMethod("register");
                        method.invoke(en);
                    } catch (NoSuchMethodException ignored) {
                        // ok
                    } catch (Exception e) {
                        plugin.getLogger().warning("Erro ao registrar permissão: " + node.getPermission());
                        plugin.getLogger().warning(e.getMessage());
                    }
                }

                String permId = node.getPermission();
                if (Bukkit.getPluginManager().getPermission(permId) == null) {
                    Permission perm = new Permission(permId, node.getDescription(), node.getDefaultValue());
                    Bukkit.getPluginManager().addPermission(perm);
                }
            }
        }

        generateDocumentation();
    }

    private void generateDocumentation() {
        File docFile = new File(plugin.getDataFolder(), "permissions.md");

        try {
            if (!docFile.exists()) {
                docFile.getParentFile().mkdirs();
                docFile.createNewFile();
            }

            try (FileWriter writer = new FileWriter(docFile)) {
                writer.write("# 📜 Lista de Permissões do Plugin\n\n");

                for (PermissionNode[] group : permissionGroups) {
                    if (group.length == 0) continue;

                    String groupName = group[0].getClass().getSimpleName().replace("Permission", "");
                    writer.write("## " + groupName + "\n\n");

                    for (PermissionNode node : group) {
                        writer.write("- `" + node.getPermission() + "` - *" + node.getDefaultValue() + "*\n");
                        writer.write("  > " + node.getDescription() + "\n");
                    }

                    writer.write("\n");
                }

                plugin.getLogger().info("📄 Documentação de permissões gerada em: " + docFile.getAbsolutePath());
            }
        } catch (Exception e) {
            plugin.getLogger().warning("❌ Falha ao gerar documentação de permissões: " + e.getMessage());
        }
    }
}