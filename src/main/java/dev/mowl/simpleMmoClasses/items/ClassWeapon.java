package dev.mowl.simpleMmoClasses.items;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class ClassWeapon {
    public abstract ItemStack getItemStack();
    public abstract long attackLeft(Player player);
    public abstract long attackRight(Player player);
    public abstract boolean isValid(ItemStack itemStack);
}
