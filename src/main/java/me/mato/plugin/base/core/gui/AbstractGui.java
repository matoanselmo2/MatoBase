package me.mato.plugin.base.core.gui;

import com.google.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import me.mato.plugin.base.core.manager.GuiManager;
import org.bukkit.entity.Player;

@Setter
@Getter
public abstract class AbstractGui {
    private GuiManager guiManager;

    public abstract void open(Player player);
}
