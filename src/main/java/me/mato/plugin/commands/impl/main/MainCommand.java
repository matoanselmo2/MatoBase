package me.mato.plugin.commands.impl.main;

import me.mato.plugin.MatoBase;
import me.mato.plugin.commands.ParentCommand;
import me.mato.plugin.commands.impl.main.sub.ReloadCommand;
import me.mato.plugin.commands.impl.main.sub.TestCommand;
import me.mato.plugin.util.Permissions;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

public class MainCommand extends ParentCommand {
    public MainCommand() {
        super(MatoBase.getInstance().getName(), Permissions.ADMIN);
        registerSubCommand(new ReloadCommand());
        registerSubCommand(new TestCommand());
    }

    @Override
    protected void execute(CommandSender sender, Object[] parsedArgs) throws CommandException {
        sendHelp(sender);
    }
}
