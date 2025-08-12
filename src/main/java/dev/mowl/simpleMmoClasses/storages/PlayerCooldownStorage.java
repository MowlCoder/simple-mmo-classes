package dev.mowl.simpleMmoClasses.storages;

import dev.mowl.simpleMmoClasses.enums.PlayerAbility;

import java.util.HashMap;
import java.util.UUID;

public class PlayerCooldownStorage {
    private final HashMap<UUID, HashMap<PlayerAbility, Long>> cooldowns = new HashMap<>();

    public void setCooldown(UUID playerId, PlayerAbility ability, long ms) {
        cooldowns
            .computeIfAbsent(playerId, id -> new HashMap<>())
            .put(ability, System.currentTimeMillis() + ms);
    }

    public boolean isOnCooldown(UUID playerId, PlayerAbility ability) {
        var playerCooldowns = cooldowns.get(playerId);
        if (playerCooldowns == null) return false;

        Long endTime = playerCooldowns.get(ability);
        return endTime != null && System.currentTimeMillis() < endTime;
    }

    public long getRemainingMillis(UUID playerId, PlayerAbility ability) {
        var playerCooldowns = cooldowns.get(playerId);
        if (playerCooldowns == null) return 0;

        Long endTime = playerCooldowns.get(ability);
        if (endTime == null) return 0;

        return Math.max(0, endTime - System.currentTimeMillis());
    }
}
