package dev.mowl.simpleMmoClasses.storages;

import dev.mowl.simpleMmoClasses.enums.PlayerClass;
import dev.mowl.simpleMmoClasses.storages.models.PlayerClassInfo;

import java.util.HashMap;
import java.util.UUID;

public class PlayerClassStorage {
    private final HashMap<UUID, PlayerClassInfo> storage = new HashMap<>();

    public PlayerClassInfo getPlayerInfo(UUID uuid) {
        return this.storage.get(uuid);
    }

    public PlayerClassInfo selectClass(UUID uuid, PlayerClass className) {
        var info = new PlayerClassInfo(className);
        this.storage.put(uuid, info);
        return info;
    }
}
