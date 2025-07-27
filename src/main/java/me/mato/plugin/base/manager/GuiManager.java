package me.mato.plugin.base.manager;

import com.google.inject.Inject;
import com.samjakob.spigui.SpiGUI;
import me.mato.plugin.base.gui.AbstractGui;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class GuiManager {
    private final List<AbstractGui> guis = new ArrayList<>();
    private final SpiGUI spiGUI;

    @Inject
    public GuiManager(JavaPlugin plugin) {
        this.spiGUI = new SpiGUI(plugin);
    }

    public void registerGui(AbstractGui gui) {
        if (gui == null) {
            throw new IllegalArgumentException("GUI cannot be null");
        }

        if (gui.getGuiManager() != this) {
            throw new IllegalArgumentException("GUI is not managed by this GuiManager");
        }

        if (guis.contains(gui)) {
            throw new IllegalArgumentException("GUI is already registered: " + gui.getClass().getSimpleName());
        }

        guis.add(gui);
    }

    public <T extends AbstractGui> T getByClass(Class<T> clazz) {
        return guis.stream()
                .filter(gui -> gui.getClass().equals(clazz))
                .map(clazz::cast)
                .findFirst()
                .orElse(null);
    }

    public List<AbstractGui> getGuis() {
        return guis;
    }

    public SpiGUI getSpiGUI() {
        return spiGUI;
    }
}
