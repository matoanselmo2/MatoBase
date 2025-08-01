package me.mato.plugin.base.core.manager;

import com.google.inject.Inject;
import com.samjakob.spigui.SpiGUI;
import lombok.Getter;
import me.mato.plugin.base.core.gui.AbstractGui;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
public class GuiManager {

    private final List<AbstractGui> guis;
    private final SpiGUI spiGUI;

    @Inject
    public GuiManager(JavaPlugin plugin, Set<AbstractGui> injectedGuis) {
        this.spiGUI = new SpiGUI(plugin);
        this.guis = new ArrayList<>(injectedGuis);

        for (AbstractGui gui : guis) {
            gui.setGuiManager(this);
        }
    }

    public <T extends AbstractGui> T getByClass(Class<T> clazz) {
        return guis.stream()
                .filter(gui -> clazz.isAssignableFrom(gui.getClass()))
                .map(clazz::cast)
                .findFirst()
                .orElse(null);
    }
}

