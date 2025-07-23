package me.mato.plugin.util;

import me.mato.plugin.MatoBase;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public enum Argument {
    PLAYER {
        @Override
        public Object parse(CommandSender sender, String arg) {
            Player player = Bukkit.getPlayer(arg);
            if (player == null) {
                throw new IllegalArgumentException(
                        MatoBase.getInstance().getConfigHelper().getMessage(
                                "argument.invalid-player",
                                sender,
                                "name", arg
                        ));
            }
            return player;
        }
    },
    INTEGER {
        @Override
        public Object parse(CommandSender sender, String arg) {
            try {
                return Integer.parseInt(arg);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        MatoBase.getInstance().getConfigHelper().getMessage(
                                "argument.invalid-integer",
                                sender,
                                "value", arg
                        ));
            }
        }
    },
    STRING {
        @Override
        public Object parse(CommandSender sender, String arg) {
            return arg; // Retorna a própria String
        }
    },

    BOOLEAN {
        final List<String> trueValues = Arrays.asList("true", "yes", "on", "sim", "1");
        final List<String> falseValues = Arrays.asList("false", "no", "off", "não", "0");

        @Override
        public Object parse(CommandSender sender, String arg) {
            if (trueValues.contains(arg.toLowerCase())) {
                return true;
            } else if (falseValues.contains(arg.toLowerCase())) {
                return false;
            } else {
                throw new IllegalArgumentException(
                        MatoBase.getInstance().getConfigHelper().getMessage(
                                "argument.invalid-boolean",
                                sender,
                                "value", arg,
                                "expected", String.join(", ", trueValues) + " or " + String.join(", ", falseValues)
                        ));
            }
        }
    },
    // Adicione mais tipos conforme necessário (ex: WORLD, ITEM, BOOLEAN)
    ;

    /**
     * Converte uma String em um objeto do tipo esperado.
     * @throws IllegalArgumentException Se o argumento for inválido.
     */
    public abstract Object parse(CommandSender sender, String arg);
}