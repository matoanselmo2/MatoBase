package me.mato.plugin.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandUtil {

    public static void sendMessage(CommandSender sender, String message) {
        if (sender != null && message != null && !message.isEmpty()) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
    }

}
