package dev.mowl.simpleMmoClasses.items;

import dev.mowl.simpleMmoClasses.config.MainConfig;
import dev.mowl.simpleMmoClasses.config.WarriorConfig;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WarriorWeapon extends ClassWeapon {
    private int hitBeforeSpecial = 0;

    @Override
    public boolean isValid(ItemStack itemStack) {
        var meta = itemStack.getItemMeta();
        if (meta == null) {
            return false;
        }

        return meta.getDisplayName().equals(getConfig().getWeaponName());
    }

    @Override
    public ItemStack getItemStack() {
        var weapon = new ItemStack(Material.DIAMOND_SWORD);
        var meta = weapon.getItemMeta();
        meta.setDisplayName(getConfig().getWeaponName());
        weapon.setItemMeta(meta);
        return weapon;
    }

    @Override
    public long attackLeft(Player player) {
        hitBeforeSpecial++;

        if (hitBeforeSpecial >= getConfig().getHitCountForSpecial()) {
            var playerLocation = player.getLocation();

            for (int i = 1; i <= getConfig().getLightingCount(); i++) {
                player.getWorld().strikeLightning(
                        playerLocation.add(playerLocation.getDirection().multiply(i))
                );
            }

            hitBeforeSpecial = 0;
        }

        return getConfig().getLeftAttackCooldown();
    }

    @Override
    public long attackRight(Player player) {
        player.setSprinting(true);
        player.setVelocity(player.getLocation().getDirection().multiply(2));
        player.addPotionEffects(getConfig().getPotionEffects());

        return getConfig().getRightAttackCooldown();
    }

    private WarriorConfig getConfig() {
        return MainConfig.getInstance().getWarriorConfig();
    }
}
