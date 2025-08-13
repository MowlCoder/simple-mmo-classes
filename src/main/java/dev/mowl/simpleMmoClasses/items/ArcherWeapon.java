package dev.mowl.simpleMmoClasses.items;

import dev.mowl.simpleMmoClasses.config.ArcherConfig;
import dev.mowl.simpleMmoClasses.config.ConfigManager;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class ArcherWeapon extends ClassWeapon {
    private int arrowsShotCount = 0;

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
        var weapon = new ItemStack(Material.DIAMOND_SHOVEL);
        var meta = weapon.getItemMeta();
        meta.setDisplayName(getConfig().getWeaponName());
        weapon.setItemMeta(meta);
        return weapon;
    }

    @Override
    public long attackLeft(Player player) {
        player.setSprinting(true);
        shotMultiArrows(player);

        player.setVelocity(player.getLocation().getDirection().multiply(-1.5));
        player.addPotionEffects(getConfig().getPotionEffects());

        return getConfig().getLeftAttackCooldown();
    }

    @Override
    public long attackRight(Player player) {
        arrowsShotCount++;

        if (arrowsShotCount >= getConfig().getArrowsForSpecial()) {
            shotMultiArrows(player);
            arrowsShotCount = 0;
        }

        player.launchProjectile(Arrow.class);
        return getConfig().getRightAttackCooldown();
    }

    private void shotMultiArrows(Player player) {
        var playerDirection = player.getLocation().getDirection();
        var playerLocation = player.getLocation();
        for (var angle : getConfig().getArrowAngles()) {
            player.launchProjectile(Arrow.class, this.getArrowDir(playerLocation.getYaw(), playerDirection.getY(), angle));
        }
    }

    private Vector getArrowDir(double yaw, double dirY, double angleAdd)
    {
        double dirX = Math.cos(Math.toRadians(yaw + 90 + angleAdd));
        double dirZ = Math.sin(Math.toRadians(yaw + 90 + angleAdd));
        return new Vector(dirX, dirY, dirZ).multiply(2);
    }

    private ArcherConfig getConfig() {
        return ConfigManager.getInstance().getArcherConfig();
    }
}
