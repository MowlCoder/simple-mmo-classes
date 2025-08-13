# Simple MMO Classes

A simple Minecraft plugin that adds 3 classes — Mage, Archer, and Warrior.
Feel free to download it and have fun on your server.

## Features
- Simple classes with only 2 skills each
- Fully configurable classes — feel free to find your best combinations

## Classes

### Warrior
- Left-click is the default attack, but once every N attacks it strikes with X lightning bolts
- Right-click is a charge that grants buffs (fully configurable in the config)
- Immune to lightning damage

### Archer
- Left-click performs a backflip jump that grants buffs (fully configurable) and shoots N arrows
- Right-click shoots an arrow, but once every N attacks it shoots N arrows
- Immune to fall damage from up to 15 blocks

### Mage
- Left-click performs a short charge that creates an explosion and grants buffs (fully configurable)
- Right-click shoots fireballs
- Immune to explosions

Plugin is available for version 1.21 using Paper

## Configuration

The plugin uses `config.yml` file located in the plugin's resources folder. All classes are fully configurable with their own sections.

### General Structure

```yaml
archer:
  # Archer class configuration
warrior:
  # Warrior class configuration  
mage:
  # Mage class configuration
messages:
  # Plugin messages
```

### Archer Configuration

```yaml
archer:
  leftAttackCooldown: 5000    # Cooldown for left-click ability in milliseconds
  rightAttackCooldown: 300    # Cooldown for right-click ability in milliseconds
  weapon:
    name: "Hunter's bow"       # Name of the archer's weapon
  arrowAngles:                # Angles for multi-arrow shot (left-click)
    - 0
    - 10
    - 20
    - 30
    - 330
    - 340
    - 350
  potions:                    # Potion effects applied on left-click
    INVISIBILITY:
      duration: 60            # Duration in ticks (20 ticks = 1 second)
      amplifier: 10
    ABSORPTION:
      duration: 60
      amplifier: 3
    SPEED:
      duration: 60
      amplifier: 1
```

### Warrior Configuration

```yaml
warrior:
  leftAttackCooldown: 0       # No cooldown for basic attacks
  rightAttackCooldown: 5000   # Cooldown for right-click ability in milliseconds
  weapon:
    name: "Warrior's sword"   # Name of the warrior's weapon
  hitCountForSpecial: 6       # Number of hits before lightning special attack
  lightingCount: 3            # Number of lightning bolts in special attack
  potions:                    # Potion effects applied on right-click
    STRENGTH:
      duration: 100
      amplifier: 3
    RESISTANCE:
      duration: 100
      amplifier: 1
    ABSORPTION:
      duration: 100
      amplifier: 6
```

### Mage Configuration

```yaml
mage:
  leftAttackCooldown: 7500    # Cooldown for left-click ability in milliseconds
  rightAttackCooldown: 350    # Cooldown for right-click ability in milliseconds
  weapon:
    name: "Magician's staff"  # Name of the mage's weapon
  explosionRadius: 4          # Radius of explosion created by left-click
  potions:                    # Potion effects applied on left-click
    FIRE_RESISTANCE:
      duration: 60
      amplifier: 10
    INVISIBILITY:
      duration: 60
      amplifier: 10
    ABSORPTION:
      duration: 60
      amplifier: 3
```

### Messages Configuration

```yaml
messages:
  invalid_usage: "&4Invalid usage of command, use /class <class_name>"
  invalid_class: "&4Invalid class name! Available classes - %class_list%"
  select_class_success: "&2You successfully selected class - %selected_class%"
  ability_cooldown: "&4You can't use this ability now. Wait &l%cooldown%&r&4 seconds!"
```

**Available Placeholders:**
- `%class_list%` - List of available classes
- `%selected_class%` - Name of the selected class
- `%cooldown%` - Remaining cooldown time in seconds

### Commands

- `/class <class_name>` - Select a class (mage, warrior, archer)
- `/weapon` - Get selected class weapon

### Notes

- All cooldowns are in milliseconds
- Potion effect durations are in ticks (20 ticks = 1 second)
- Potion effect amplifiers start from 0 (level 1 = amplifier 0)
- The plugin automatically saves player class selections
- Changes to config.yml require server restart to take effect