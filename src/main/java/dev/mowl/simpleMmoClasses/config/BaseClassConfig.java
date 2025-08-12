package dev.mowl.simpleMmoClasses.config;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseClassConfig {
    private final List<PotionEffect> potionEffects = new ArrayList<>();

    abstract public void parse(ConfigurationSection section);

    public List<PotionEffect> getPotionEffects() {
        return potionEffects;
    }

    protected void parsePotionEffects(ConfigurationSection potionSection) {
        if (potionSection == null) {
            return;
        }

        for (var potionName : potionSection.getKeys(false)) {
            PotionEffectType potionEffectType = Registry.EFFECT.getOrThrow(NamespacedKey.minecraft(potionName.toLowerCase()));
            int duration = potionSection.getInt(potionName + ".duration", 20);
            int amplifier = potionSection.getInt(potionName + ".amplifier", 1);
            potionEffects.add(new PotionEffect(potionEffectType, duration, amplifier));
        }
    }
}
