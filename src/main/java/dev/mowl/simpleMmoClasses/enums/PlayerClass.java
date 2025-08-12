package dev.mowl.simpleMmoClasses.enums;

import java.util.Arrays;
import java.util.List;

public enum PlayerClass {
    MAGE,
    WARRIOR,
    ARCHER;

    public static List<String> toLowerStrings() {
        return Arrays.stream(values())
                .map(e -> e.name().toLowerCase())
                .toList();
    }
}