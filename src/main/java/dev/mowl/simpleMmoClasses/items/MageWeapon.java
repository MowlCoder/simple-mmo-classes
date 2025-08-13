package dev.mowl.simpleMmoClasses.items;

import dev.mowl.simpleMmoClasses.config.MageConfig;
import dev.mowl.simpleMmoClasses.config.ConfigManager;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MageWeapon extends ClassWeapon {
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
        var itemStack = new ItemStack(Material.DIAMOND_HOE);
        var meta = itemStack.getItemMeta();
        meta.setDisplayName(getConfig().getWeaponName());
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public long attackLeft(Player player) {
        player.addPotionEffects(getConfig().getPotionEffects());
        player.getWorld().createExplosion(
                player.getLocation(),
                getConfig().getExplosionRadius(),
                true
        );
        player.setSprinting(true);
        player.setVelocity(player.getLocation().getDirection().multiply(1.3));
        return getConfig().getLeftAttackCooldown();
    }

    @Override
    public long attackRight(Player player) {
        player.launchProjectile(Fireball.class, player.getLocation().getDirection());
        return getConfig().getRightAttackCooldown();
    }

    private MageConfig getConfig() {
        return ConfigManager.getInstance().getMageConfig();
    }
}
