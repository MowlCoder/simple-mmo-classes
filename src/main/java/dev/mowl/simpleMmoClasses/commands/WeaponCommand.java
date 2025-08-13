package dev.mowl.simpleMmoClasses.commands;

import dev.mowl.simpleMmoClasses.config.ConfigManager;
import dev.mowl.simpleMmoClasses.storages.PlayerClassStorage;
import dev.mowl.simpleMmoClasses.managers.PlayerClassManager;
import dev.mowl.simpleMmoClasses.utils.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class WeaponCommand implements CommandExecutor, TabExecutor {
    private final PlayerClassStorage playerClassStorage;

    public WeaponCommand(
            PlayerClassStorage playerClassStorage
    ) {
        this.playerClassStorage = playerClassStorage;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player player)) {
            ChatUtil.sendMessage(
                commandSender,
                ConfigManager.getInstance().getMessage("invalid_sender")
            );
            return true;
        }

        var info = this.playerClassStorage.getPlayerInfo(player.getUniqueId());
        if (info == null) {
            ChatUtil.sendMessage(
                player,
                ConfigManager.getInstance().getMessage("not_have_class")
            );
            return true;
        }

        boolean isWeaponGiven = PlayerClassManager.giveWeapon(player, info);

        ChatUtil.sendMessage(
            player,
            ConfigManager.getInstance().getMessage(
                isWeaponGiven ? "give_weapon_success" : "already_have_weapon"
            )
        );

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return List.of();
    }
}
