package me.mato.plugin.base.gui;

import com.google.inject.Inject;
import me.mato.plugin.base.manager.GuiManager;
import org.bukkit.entity.Player;

public abstract class AbstractGui {
    private final GuiManager guiManager;

    @Inject
    public AbstractGui(GuiManager guiManager) {
        this.guiManager = guiManager;
    }

    public abstract void open(Player player);

    public GuiManager getGuiManager() {
        return guiManager;
    }

}
