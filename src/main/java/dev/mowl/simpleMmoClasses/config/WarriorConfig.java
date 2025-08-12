package dev.mowl.simpleMmoClasses.config;

import org.bukkit.configuration.ConfigurationSection;

public class WarriorConfig extends BaseClassConfig {
    private long leftAttackCooldown;
    private long rightAttackCooldown;

    private String weaponName;
    private int hitCountForSpecial;
    private int lightingCount;

    @Override
    public void parse(ConfigurationSection section) {
        leftAttackCooldown = section.getInt("leftAttackCooldown", 7500);
        rightAttackCooldown = section.getInt("rightAttackCooldown", 350);
        weaponName = section.getString("weapon.name", "Mage staff");
        hitCountForSpecial = section.getInt("hitCountForSpecial", 6);
        lightingCount = section.getInt("lightingCount", 3);

        this.parsePotionEffects(section.getConfigurationSection("potions"));
    }

    public int getHitCountForSpecial() {
        return hitCountForSpecial;
    }

    public int getLightingCount() {
        return lightingCount;
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
