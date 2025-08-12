package dev.mowl.simpleMmoClasses.config;

import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class ArcherConfig extends BaseClassConfig {
    private long leftAttackCooldown;
    private long rightAttackCooldown;

    private String weaponName;
    private List<Integer> arrowAngles;
    private int arrowsForSpecial;

    @Override
    public void parse(ConfigurationSection section) {
        leftAttackCooldown = section.getInt("leftAttackCooldown", 7500);
        rightAttackCooldown = section.getInt("rightAttackCooldown", 350);
        weaponName = section.getString("weapon.name", "Mage staff");
        arrowAngles = section.getIntegerList("arrowAngles");
        arrowsForSpecial = section.getInt("arrowsForSpecial", 10);

        this.parsePotionEffects(section.getConfigurationSection("potions"));
    }

    public List<Integer> getArrowAngles() {
        return arrowAngles;
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

    public int getArrowsForSpecial() {
        return arrowsForSpecial;
    }
}
