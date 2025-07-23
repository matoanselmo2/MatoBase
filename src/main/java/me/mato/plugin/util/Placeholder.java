package me.mato.plugin.util;

import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Placeholder {
    private final Map<String, Function<CommandSender, String>> placeholders = new HashMap<>();

    /**
     * Registra um novo placeholder.
     * @param key A chave (ex: "player" para "%player%").
     * @param resolver Função que resolve o valor (ex: retorna o nome do jogador).
     */
    public void register(String key, Function<CommandSender, String> resolver) {
        placeholders.put(key.toLowerCase(), resolver);
    }

    /**
     * Substitui todos os placeholders em uma mensagem.
     * @param sender Quem executou a ação (Player, Console, etc.).
     * @param text Mensagem com placeholders (ex: "Bem-vindo, %player%!").
     */
    public String resolve(CommandSender sender, String text) {
        for (Map.Entry<String, Function<CommandSender, String>> entry : placeholders.entrySet()) {
            String placeholder = "%" + entry.getKey() + "%";
            if (text.contains(placeholder)) {
                text = text.replace(placeholder, entry.getValue().apply(sender));
            }
        }
        return text;
    }
}
