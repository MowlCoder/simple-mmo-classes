package dev.mowl.simpleMmoClasses.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatUtil {
    public static String prefix = "&6[Simple MMO Classes]&r ";

    public static String createColorMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void sendMessage(Player player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + message));
    }

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + message));
    }

    public static void sendTitle(
            Player player,
            String message,
            String subMessage,
            int fadeIn,
            int stay,
            int fadeOut
    ) {
        player.sendTitle(
                ChatColor.translateAlternateColorCodes('&', message),
                ChatColor.translateAlternateColorCodes('&', subMessage),
                fadeIn,
                stay,
                fadeOut
        );
    }

    public static void sendTitle(
            Player player,
            String message,
            String subMessage
    ) {
        player.sendTitle(
                ChatColor.translateAlternateColorCodes('&', message),
                ChatColor.translateAlternateColorCodes('&', subMessage),
                10,
                20,
                10
        );
    }

    public static void broadCastMessage(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            sendMessage(player, message);
        }
    }

    public static void broadCastTitle(
            String message,
            String subMessage,
            int fadeIn,
            int stay,
            int fadeOut
    ) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            sendTitle(player, message, subMessage, fadeIn, stay, fadeOut);
        }
    }

    public static void broadCastTitle(
            String message,
            String subMessage
    ) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            sendTitle(player, message, subMessage);
        }
    }
}