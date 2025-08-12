package dev.mowl.simpleMmoClasses;

import dev.mowl.simpleMmoClasses.commands.ClassCommand;
import dev.mowl.simpleMmoClasses.config.MainConfig;
import dev.mowl.simpleMmoClasses.storages.PlayerClassStorage;
import dev.mowl.simpleMmoClasses.storages.PlayerCooldownStorage;
import dev.mowl.simpleMmoClasses.utils.ChatUtil;
import dev.mowl.simpleMmoClasses.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleMMOClassesPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        PlayerClassStorage playerClassStorage = new PlayerClassStorage();
        PlayerCooldownStorage playerCooldownStorage = new PlayerCooldownStorage();

        ChatUtil.init(this);
        PlayerUtil.init(playerClassStorage);

        MainConfig.parseFromPlugin(this);

        Bukkit.getPluginManager().registerEvents(
            new CustomEvents(playerClassStorage, playerCooldownStorage),
            this
        );

        ClassCommand classCommand = new ClassCommand(playerClassStorage);

        getCommand("class").setExecutor(classCommand);
        getCommand("class").setTabCompleter(classCommand);
    }
}
