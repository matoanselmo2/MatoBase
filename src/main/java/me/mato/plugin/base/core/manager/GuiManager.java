package me.mato.plugin.base.core.manager;

import com.google.inject.Inject;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.CustomInventoryImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GuiManager {

    private final List<CustomInventoryImpl> guis;

    @Inject
    public GuiManager(Set<CustomInventoryImpl> injectedGuis) {
        this.guis = new ArrayList<>(injectedGuis);
    }

    public <T extends CustomInventoryImpl> T getByClass(Class<T> clazz) {
        return guis.stream()
                .filter(gui -> clazz.isAssignableFrom(gui.getClass()))
                .map(clazz::cast)
                .findFirst()
                .orElse(null);
    }

    public List<CustomInventoryImpl> guis() {
        return guis;
    }
}

