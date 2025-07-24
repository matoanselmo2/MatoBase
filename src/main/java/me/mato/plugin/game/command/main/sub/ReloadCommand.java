package me.mato.plugin.game.command.main.sub;

import me.mato.plugin.api.command.impl.NormalCommand;
import me.mato.plugin.util.CommandUtil;
import me.mato.plugin.util.Permissions;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends NormalCommand {
    public ReloadCommand() {
        super("reload", Permissions.ADMIN);
    }

    @Override
    public void execute(CommandSender sender, Object[] parsedArgs) {
        plugin.getConfigHelper().reload();
        CommandUtil.sendMessage(sender, plugin.getConfigHelper().getMessage("reload-message", sender));
    }
}
