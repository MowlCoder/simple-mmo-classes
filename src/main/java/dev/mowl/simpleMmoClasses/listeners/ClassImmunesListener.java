package dev.mowl.simpleMmoClasses.listeners;

import dev.mowl.simpleMmoClasses.enums.PlayerClass;
import dev.mowl.simpleMmoClasses.storages.PlayerClassStorage;
import dev.mowl.simpleMmoClasses.storages.PlayerCooldownStorage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class ClassImmunesListener implements Listener {
    private final PlayerClassStorage playerClassStorage;

    public ClassImmunesListener(
            PlayerClassStorage playerClassStorage
    ) {
        this.playerClassStorage = playerClassStorage;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        var playerClassInfo = this.playerClassStorage.getPlayerInfo(player.getUniqueId());
        if (playerClassInfo == null) {
            return;
        }

        if (playerClassInfo.getSelectedClass() == PlayerClass.ARCHER) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL && player.getFallDistance() <= 15) {
                event.setCancelled(true);
            }
        } else if (playerClassInfo.getSelectedClass() == PlayerClass.MAGE) {
            if (
                event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION ||
                event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION
            ) {
                event.setCancelled(true);
            }
        } else if (playerClassInfo.getSelectedClass() == PlayerClass.WARRIOR) {
            if (
                event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING
            ) {
                event.setCancelled(true);
            }
        }
    }
}
