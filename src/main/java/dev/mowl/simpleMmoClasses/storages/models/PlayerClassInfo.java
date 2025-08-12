package dev.mowl.simpleMmoClasses.storages.models;

import dev.mowl.simpleMmoClasses.enums.PlayerClass;
import dev.mowl.simpleMmoClasses.items.ArcherWeapon;
import dev.mowl.simpleMmoClasses.items.ClassWeapon;
import dev.mowl.simpleMmoClasses.items.MageWeapon;
import dev.mowl.simpleMmoClasses.items.WarriorWeapon;

public class PlayerClassInfo {
    private final ClassWeapon weapon;
    private final PlayerClass selectedClass;

    public PlayerClassInfo(PlayerClass selectedClass) {
        this.weapon = this.createClassWeaponByClassName(selectedClass);
        this.selectedClass = selectedClass;
    }

    public ClassWeapon getWeapon() {
        return weapon;
    }

    public PlayerClass getSelectedClass() {
        return selectedClass;
    }

    private ClassWeapon createClassWeaponByClassName(PlayerClass className) {
        return switch (className) {
            case PlayerClass.MAGE -> new MageWeapon();
            case PlayerClass.ARCHER -> new ArcherWeapon();
            case PlayerClass.WARRIOR -> new WarriorWeapon();
        };
    }
}
