package me.mato.plugin.commands.impl.main.sub;

import me.mato.plugin.MatoBase;
import me.mato.plugin.commands.NormalCommand;
import me.mato.plugin.util.Argument;
import me.mato.plugin.util.CommandUtil;
import me.mato.plugin.util.Permissions;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand extends NormalCommand {

    public TestCommand() {
        super("test", Permissions.ADMIN, Argument.STRING, Argument.INTEGER, Argument.BOOLEAN, Argument.PLAYER);
    }

    @Override
    public void execute(CommandSender sender, Object[] parsedArgs) {
        String parsedString = (String) parsedArgs[0];
        int parsedInt = (Integer) parsedArgs[1];
        boolean parsedBoolean = (Boolean) parsedArgs[2];
        Player parsedPlayer = (Player) parsedArgs[3];

        CommandUtil.sendMessage(sender, "&aTest Command Executed with Arguments:");
        CommandUtil.sendMessage(sender, "&aString: &7" + parsedString);
        CommandUtil.sendMessage(sender, "&aInteger: &7" + parsedInt);
        CommandUtil.sendMessage(sender, "&aBoolean: &7" + parsedBoolean);
        CommandUtil.sendMessage(sender, "&aPlayer: &7" + parsedPlayer.getName() + " (" + (parsedPlayer.isOnline() ? "Online" : "Offline") + ")");
        CommandUtil.sendMessage(sender, "&aPlugin Version: &7" + MatoBase.getInstance().getDescription().getVersion());
        CommandUtil.sendMessage(sender, "&aPlugin Version: &7" + MatoBase.getInstance().getDescription().getVersion());
        CommandUtil.sendMessage(sender, "&aDatabase Connected? " + MatoBase.getInstance().getDatabaseManager().isConnected());
    }
}
