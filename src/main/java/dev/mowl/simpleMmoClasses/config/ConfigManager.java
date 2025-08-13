package dev.mowl.simpleMmoClasses.config;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {
    private final MageConfig mageConfig;
    private final WarriorConfig warriorConfig;
    private final ArcherConfig archerConfig;
    private ConfigurationSection messageSection;

    private NamespacedKey playerClassKey;

    private static ConfigManager instance;

    public ConfigManager() {
        mageConfig = new MageConfig();
        warriorConfig = new WarriorConfig();
        archerConfig = new ArcherConfig();
    }

    public static void parseFromPlugin(JavaPlugin plugin) {
        FileConfiguration pluginConfig = plugin.getConfig();

        var mageConfigSection = pluginConfig.getConfigurationSection("mage");
        var warriorConfigSection = pluginConfig.getConfigurationSection("warrior");
        var archerConfigSection = pluginConfig.getConfigurationSection("archer");

        getInstance().mageConfig.parse(mageConfigSection);
        getInstance().warriorConfig.parse(warriorConfigSection);
        getInstance().archerConfig.parse(archerConfigSection);
        getInstance().setMessageSection(pluginConfig.getConfigurationSection("messages"));
        getInstance().setPlayerClassKey(plugin, "selected_class");
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }

        return instance;
    }

    public String getMessage(String messageId) {
        return this.messageSection.getString(messageId);
    }

    public NamespacedKey getPlayerClassKey() {
        return this.playerClassKey;
    }

    public MageConfig getMageConfig() {
        return mageConfig;
    }

    public WarriorConfig getWarriorConfig() {
        return warriorConfig;
    }

    public ArcherConfig getArcherConfig() {
        return archerConfig;
    }

    private void setMessageSection(ConfigurationSection section) {
        this.messageSection = section;
    }

    private void setPlayerClassKey(JavaPlugin plugin, String key) {
        this.playerClassKey = new NamespacedKey(plugin, key);
    }
}
