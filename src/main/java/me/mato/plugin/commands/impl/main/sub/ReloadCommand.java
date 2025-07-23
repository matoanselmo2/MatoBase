package me.mato.plugin.commands.impl.main.sub;

import me.mato.plugin.commands.NormalCommand;
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
