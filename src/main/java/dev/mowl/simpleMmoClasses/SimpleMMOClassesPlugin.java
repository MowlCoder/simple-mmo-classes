package dev.mowl.simpleMmoClasses;

import dev.mowl.simpleMmoClasses.commands.ClassCommand;
import dev.mowl.simpleMmoClasses.commands.WeaponCommand;
import dev.mowl.simpleMmoClasses.config.ConfigManager;
import dev.mowl.simpleMmoClasses.listeners.ClassImmunesListener;
import dev.mowl.simpleMmoClasses.listeners.PlayerJoinListener;
import dev.mowl.simpleMmoClasses.listeners.PlayerSkillsListener;
import dev.mowl.simpleMmoClasses.storages.PlayerClassStorage;
import dev.mowl.simpleMmoClasses.storages.PlayerCooldownStorage;
import dev.mowl.simpleMmoClasses.utils.ChatUtil;
import dev.mowl.simpleMmoClasses.managers.PlayerClassManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleMMOClassesPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        PlayerClassStorage playerClassStorage = new PlayerClassStorage();
        PlayerCooldownStorage playerCooldownStorage = new PlayerCooldownStorage();

        PlayerClassManager.init(playerClassStorage);

        ConfigManager.parseFromPlugin(this);

        Bukkit.getPluginManager().registerEvents(
            new PlayerJoinListener(),
            this
        );
        Bukkit.getPluginManager().registerEvents(
            new PlayerSkillsListener(playerClassStorage, playerCooldownStorage),
            this
        );
        Bukkit.getPluginManager().registerEvents(
            new ClassImmunesListener(playerClassStorage),
            this
        );

        ClassCommand classCommand = new ClassCommand(playerClassStorage);

        getCommand("class").setExecutor(classCommand);
        getCommand("class").setTabCompleter(classCommand);

        WeaponCommand weaponCommand = new WeaponCommand(playerClassStorage);

        getCommand("weapon").setExecutor(weaponCommand);
        getCommand("weapon").setTabCompleter(weaponCommand);
    }
}
