package dev.mowl.simpleMmoClasses.config;

import org.bukkit.configuration.ConfigurationSection;

public class MageConfig extends BaseClassConfig {
    private long leftAttackCooldown;
    private long rightAttackCooldown;

    private String weaponName;
    private int explosionRadius;

    @Override
    public void parse(ConfigurationSection section) {
        leftAttackCooldown = section.getInt("leftAttackCooldown", 7500);
        rightAttackCooldown = section.getInt("rightAttackCooldown", 350);
        weaponName = section.getString("weapon.name", "Mage staff");
        explosionRadius = section.getInt("explosionRadius", 4);

        this.parsePotionEffects(section.getConfigurationSection("potions"));
    }

    public int getExplosionRadius() {
        return explosionRadius;
    }

    public String getWeaponName() {
        return weaponName;
    }

    public long getRightAttackCooldown() {
        return rightAttackCooldown;
    }

    public long getLeftAttackCooldown() {
        return leftAttackCooldown;
    }
}
