package me.mato.plugin.base.registry;

import me.mato.plugin.base.MatoBase;
import me.mato.plugin.base.command.BaseCommand;
import me.mato.plugin.base.command.CommandContext;
import me.mato.plugin.base.command.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PluginCommandWrapper extends Command {

    private final BaseCommand command;
    private final MatoBase plugin;

    public PluginCommandWrapper(MatoBase plugin, BaseCommand command) {
        super(command.getName());
        this.plugin = plugin;
        this.command = command;
        this.setDescription(command.getDescription());
        this.setUsage("/" + command.getName());
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, String[] args) {
        CommandContext ctx = new CommandContext(sender, args);

        try {
            if (args.length >= 1) {
                String sub = args[0].toLowerCase();
                SubCommand subCommand = command.getSubCommands().get(sub);

                if (subCommand != null) {
                    subCommand.execute(ctx.subContext());
                    return true;
                }
            }

            if (!sender.hasPermission(command.permission().getPermissions())) {
                sender.sendMessage("§cVocê não tem permissão para executar este comando.");
                return true;
            }

            command.execute(ctx);
        } catch (IllegalArgumentException | IllegalStateException e) {
            sender.sendMessage("§cErro: " + e.getMessage());
        } catch (Exception e) {
            sender.sendMessage("§cUm erro inesperado ocorreu.");
            plugin.getLogger().severe(e.getMessage());
        }

        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, String[] args) {
        return command.tab(new CommandContext(sender, args));
    }
}
