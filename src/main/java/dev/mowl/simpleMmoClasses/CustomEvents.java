package dev.mowl.simpleMmoClasses;

import dev.mowl.simpleMmoClasses.config.MainConfig;
import dev.mowl.simpleMmoClasses.enums.PlayerAbility;
import dev.mowl.simpleMmoClasses.enums.PlayerClass;
import dev.mowl.simpleMmoClasses.storages.PlayerClassStorage;
import dev.mowl.simpleMmoClasses.storages.PlayerCooldownStorage;
import dev.mowl.simpleMmoClasses.utils.ChatUtil;
import dev.mowl.simpleMmoClasses.utils.PlayerUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;

public class CustomEvents implements Listener {
    private final PlayerClassStorage playerClassStorage;
    private final PlayerCooldownStorage playerCooldownStorage;

    public CustomEvents(
        PlayerClassStorage playerClassStorage,
        PlayerCooldownStorage playerCooldownStorage
    ) {
        this.playerClassStorage = playerClassStorage;
        this.playerCooldownStorage = playerCooldownStorage;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getHand() == null || !event.getHand().equals(EquipmentSlot.HAND)) {
            return;
        }

        PlayerAbility usedSkill = getUsedSkillByAction(event.getAction());
        if (usedSkill == PlayerAbility.NONE) {
            return;
        }

        Player player = event.getPlayer();
        var playerClassInfo = this.playerClassStorage.getPlayerInfo(player.getUniqueId());

        var itemInHand = player.getInventory().getItemInMainHand();
        if (!playerClassInfo.getWeapon().isValid(itemInHand)) {
            return;
        }

        long cooldownLeftMs = this.playerCooldownStorage.getRemainingMillis(player.getUniqueId(), usedSkill);

        if (cooldownLeftMs > 0) {
            ChatUtil.sendMessage(
                player,
                MainConfig.getInstance()
                    .getMessage("ability_cooldown")
                    .replace("%cooldown%", String.format("%.2f", ((double) cooldownLeftMs / 1000.0)))
            );
            return;
        }

        long cooldownMs = 0;

        if (usedSkill == PlayerAbility.RIGHT_ATTACK) {
            cooldownMs = playerClassInfo.getWeapon().attackRight(player);
        } else {
            cooldownMs = playerClassInfo.getWeapon().attackLeft(player);
        }

        this.playerCooldownStorage.setCooldown(player.getUniqueId(), usedSkill, cooldownMs);
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

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String className = player.getPersistentDataContainer().get(
            MainConfig.getInstance().getPlayerClassKey(),
            PersistentDataType.STRING
        );

        if (className != null) {
            PlayerUtil.selectClass(player, PlayerClass.valueOf(className));
        }
    }

    private PlayerAbility getUsedSkillByAction(Action action) {
        if (
            action.equals(Action.RIGHT_CLICK_AIR) ||
            action.equals(Action.RIGHT_CLICK_BLOCK)
        ) {
            return PlayerAbility.RIGHT_ATTACK;
        } else if (
            action.equals(Action.LEFT_CLICK_AIR) ||
            action.equals(Action.LEFT_CLICK_BLOCK)
        ) {
            return PlayerAbility.LEFT_ATTACK;
        }

        return PlayerAbility.NONE;
    }
}
