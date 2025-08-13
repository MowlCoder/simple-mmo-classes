package dev.mowl.simpleMmoClasses.managers;

import dev.mowl.simpleMmoClasses.config.ConfigManager;
import dev.mowl.simpleMmoClasses.enums.PlayerClass;
import dev.mowl.simpleMmoClasses.storages.PlayerClassStorage;
import dev.mowl.simpleMmoClasses.storages.models.PlayerClassInfo;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class PlayerClassManager {
    private static PlayerClassStorage playerClassStorage;

    public static void init(PlayerClassStorage storage) {
        playerClassStorage = storage;
    }

    public static boolean giveWeapon(Player player, PlayerClassInfo playerClassInfo) {
        boolean alreadyHasWeapon = false;

        for (var item : player.getInventory()) {
            if (item == null) {
                continue;
            }

            if (playerClassInfo.getWeapon().isValid(item)) {
                alreadyHasWeapon = true;
                break;
            }
        }

        if (alreadyHasWeapon) {
            return false;
        }

        player.getInventory().addItem(playerClassInfo.getWeapon().getItemStack());
        return true;
    }

    public static void selectClass(Player player, PlayerClass playerClass) {
        var info = playerClassStorage.selectClass(player.getUniqueId(), playerClass);
        player.getPersistentDataContainer().set(
                ConfigManager.getInstance().getPlayerClassKey(),
                PersistentDataType.STRING,
                playerClass.name().toUpperCase()
        );

        PlayerClassManager.giveWeapon(player, info);
    }
}
