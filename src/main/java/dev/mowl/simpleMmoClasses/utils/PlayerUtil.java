package dev.mowl.simpleMmoClasses.utils;

import dev.mowl.simpleMmoClasses.config.MainConfig;
import dev.mowl.simpleMmoClasses.enums.PlayerClass;
import dev.mowl.simpleMmoClasses.storages.PlayerClassStorage;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class PlayerUtil {
    private static PlayerClassStorage playerClassStorage;

    public static void init(PlayerClassStorage storage) {
        playerClassStorage = storage;
    }

    public static void selectClass(Player player, PlayerClass playerClass) {
        var info = playerClassStorage.selectClass(player.getUniqueId(), playerClass);
        player.getPersistentDataContainer().set(
                MainConfig.getInstance().getPlayerClassKey(),
                PersistentDataType.STRING,
                playerClass.name().toUpperCase()
        );

        boolean alreadyHasWeapon = false;

        for (var item : player.getInventory()) {
            if (item == null) {
                continue;
            }

            if (info.getWeapon().isValid(item)) {
                alreadyHasWeapon = true;
                break;
            }
        }

        if (!alreadyHasWeapon) {
            player.getInventory().addItem(info.getWeapon().getItemStack());
        }
    }
}
