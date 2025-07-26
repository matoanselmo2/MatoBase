package me.mato.plugin.base.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public record CommandContext(CommandSender sender, String[] args) {

    public boolean isPlayer() {
        return sender instanceof Player;
    }

    public Player player() {
        if (!isPlayer()) throw new IllegalStateException("Esse comando só pode ser usado por jogadores.");
        return (Player) sender;
    }

    public CommandSender sender() {
        return sender;
    }

    public String arg(int index) {
        if (index < 0 || index >= args.length) {
            throw new IllegalArgumentException("Argumento " + index + " está fora do alcance.");
        }
        return args[index];
    }

    public int intArg(int index) {
        try {
            return Integer.parseInt(arg(index));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O argumento '" + arg(index) + "' não é um número inteiro.");
        }
    }

    public Player player(int index) {
        Player target = Bukkit.getPlayerExact(arg(index));
        if (target == null) throw new IllegalArgumentException("Jogador não encontrado: " + arg(index));
        return target;
    }

    public CommandContext subContext() {
        if (args.length <= 1) return new CommandContext(sender, new String[0]);

        String[] subArgs = new String[args.length - 1];
        System.arraycopy(args, 1, subArgs, 0, subArgs.length);
        return new CommandContext(sender, subArgs);
    }
}