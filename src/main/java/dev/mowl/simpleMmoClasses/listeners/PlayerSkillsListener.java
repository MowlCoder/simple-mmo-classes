package dev.mowl.simpleMmoClasses.listeners;

import dev.mowl.simpleMmoClasses.config.ConfigManager;
import dev.mowl.simpleMmoClasses.enums.PlayerAbility;
import dev.mowl.simpleMmoClasses.storages.PlayerClassStorage;
import dev.mowl.simpleMmoClasses.storages.PlayerCooldownStorage;
import dev.mowl.simpleMmoClasses.utils.ChatUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerSkillsListener implements Listener {
    private final PlayerClassStorage playerClassStorage;
    private final PlayerCooldownStorage playerCooldownStorage;

    public PlayerSkillsListener(
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
                    ConfigManager.getInstance()
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
