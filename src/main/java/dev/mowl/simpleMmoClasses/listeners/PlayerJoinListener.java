package dev.mowl.simpleMmoClasses.listeners;

import dev.mowl.simpleMmoClasses.config.ConfigManager;
import dev.mowl.simpleMmoClasses.enums.PlayerClass;
import dev.mowl.simpleMmoClasses.managers.PlayerClassManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataType;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String className = player.getPersistentDataContainer().get(
            ConfigManager.getInstance().getPlayerClassKey(),
            PersistentDataType.STRING
        );

        if (className != null) {
            PlayerClassManager.selectClass(player, PlayerClass.valueOf(className));
        }
    }
}
