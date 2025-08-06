package me.mato.plugin.base.core.gui;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.simple.SimpleViewer;
import me.mato.plugin.base.util.ComponentUtil;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;

public class ExampleMenu  extends SimpleInventory {
    public ExampleMenu() {
        super("example.menu", "&a&lExample Menu", 27);

        configuration(config -> config.secondUpdate(3));
    }

    @Override
    protected void configureViewer(@NotNull SimpleViewer viewer) {
        viewer.getConfiguration().titleInventory("&a&lExample Menu");
    }

    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        ItemStack diamond = new ItemStack(Material.DIAMOND);

        ItemMeta meta = diamond.getItemMeta();

        if (meta != null) {
            meta.displayName(ComponentUtil.compose()
                    .text("Diamante", NamedTextColor.AQUA, TextDecoration.BOLD)
                    .build()
            );
            Objects.requireNonNull(meta.lore()).add((ComponentUtil.compose()
                    .text("Clique para coletar", NamedTextColor.GOLD, TextDecoration.BOLD)
                    .build()));

            diamond.setItemMeta(meta);
        }

        InventoryItem item = InventoryItem.of(diamond).callback(ClickType.LEFT, event -> {;
            Player clickedPlayer = event.getPlayer();
            clickedPlayer.sendMessage(ComponentUtil.composeToString("Você coletou um diamante!", NamedTextColor.GREEN, TextDecoration.BOLD));
            clickedPlayer.getInventory().addItem(diamond);
            clickedPlayer.closeInventory();
        });

        editor.setItem(4, item);
    }

    @Override
    protected void update(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {
        Random rand = new Random();

        ItemStack randomItem = new ItemStack(Material.values()[rand.nextInt(Material.values().length)]);
        ItemMeta meta = randomItem.getItemMeta();

        if (meta != null) {
            meta.displayName(ComponentUtil.compose()
                    .text("Item Aleatório", NamedTextColor.YELLOW, TextDecoration.BOLD)
                    .build()
            );
            Objects.requireNonNull(meta.lore()).add((ComponentUtil.compose()
                    .text("Clique para coletar", NamedTextColor.GOLD, TextDecoration.BOLD)
                    .build()));
            randomItem.setItemMeta(meta);
        }

        InventoryItem randomInventoryItem = InventoryItem.of(randomItem).callback(ClickType.LEFT, event -> {
            Player clickedPlayer = event.getPlayer();
            clickedPlayer.sendMessage(ComponentUtil.composeToString("Você coletou um item aleatório!", NamedTextColor.GREEN, TextDecoration.BOLD));
            clickedPlayer.getInventory().addItem(randomItem);
            clickedPlayer.closeInventory();
        });

        Random random = new Random();
        int randomSlot = random.nextInt(9, 27); // Slots 9 to 26
        editor.setItem(randomSlot, randomInventoryItem);
    }
}
