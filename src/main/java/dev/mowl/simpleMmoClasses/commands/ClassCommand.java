package dev.mowl.simpleMmoClasses.commands;

import dev.mowl.simpleMmoClasses.SimpleMMOClassesPlugin;
import dev.mowl.simpleMmoClasses.config.MainConfig;
import dev.mowl.simpleMmoClasses.enums.PlayerClass;
import dev.mowl.simpleMmoClasses.storages.PlayerClassStorage;
import dev.mowl.simpleMmoClasses.storages.PlayerCooldownStorage;
import dev.mowl.simpleMmoClasses.utils.ChatUtil;
import dev.mowl.simpleMmoClasses.utils.PlayerUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

public class ClassCommand implements CommandExecutor, TabExecutor {
    private final PlayerClassStorage playerClassStorage;
    private final List<String> CLASS_NAMES = PlayerClass.toLowerStrings();

    public ClassCommand(
        PlayerClassStorage playerClassStorage
    ) {
        this.playerClassStorage = playerClassStorage;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player player)) {
            return false;
        }

        if (strings.length != 1) {
            ChatUtil.sendMessage(
                player,
                MainConfig.getInstance().getMessage("invalid_usage")
            );
            return false;
        }

        String className = strings[0];
        PlayerClass playerClass;

        try {
            playerClass = PlayerClass.valueOf(className.toUpperCase());
        } catch (Exception e) {
            ChatUtil.sendMessage(
                player,
                MainConfig.getInstance()
                        .getMessage("invalid_usage")
                        .replace("%class_list%", CLASS_NAMES.toString())
            );
            return false;
        }

        PlayerUtil.selectClass(player, playerClass);
        ChatUtil.sendMessage(
            player,
            MainConfig.getInstance()
                    .getMessage("select_class_success")
                    .replace("%selected_class%", className.toUpperCase())
        );

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return CLASS_NAMES;
        }

        return null;
    }
}
