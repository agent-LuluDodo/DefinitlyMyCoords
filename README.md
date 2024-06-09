## Download on [Modrinth](https://modrinth.com/mod/definitelymycoords)!  
![Settings Menu](https://cdn.modrinth.com/data/M4Fyp5vW/images/3f356d1fdba6cc8c87533c3f349fc079b22358ea.png)
DefinitelyMyCoords helps you hide your coordinates on the F3 screen with 3 different modes.`

# Why do I want this mod?

If you're a streamer or share your screen with somebody, 
this mod could save you from leaking your coordinates. 
While still being able to use F3 to measure distances, 
check the direction you are facing, etc.

## Usage

To open the mods settings, press the **F6** key or your configured key.  
You can configure the settings key in the keybindings menu.

## Compatibility

A non-exhaustive list of compatible mods.  
*These mods are neither made by me nor affiliated with me.*

- [BetterF3](https://modrinth.com/mod/betterf3)
- [Xaero's World Map](https://modrinth.com/mod/xaeros-world-map)
- [Xaero's Minimap](https://modrinth.com/mod/xaeros-minimap)
- [Xaero's Minimap (Fair-Play)](https://modrinth.com/mod/xaeros-minimap-fair)
- [Sodium Extra](https://modrinth.com/mod/sodium-extra)

## Modes

### Vanilla
[suggested by @JackyXYZGaming](https://github.com/agent-LuluDodo/DefinitelyMyCoords/issues/9)

Doesn't modify your position at all.

### Relative

The coordinates you enter are relative to your current positions. That means if you are standing at 100 60 -30 and enter -10 5 20 your current coordinates will be -10 5 20 and 0 0 0 will have turned into -110 -55 50.

### Absolute

The coordinates you enter are relative to 0 0 0. In this case, if you are standing at 100 60 -30 and enter -10 5 20 your current coordinates will turn into 90 65 -10 and 0 0 0 will be -10 5 20.

### Custom

This mode sets your coordinates upon opening F3 to 0 0 0.

## Biome Spoofing
[suggested by @kittenvr](https://github.com/agent-LuluDodo/DefinitelyMyCoords/issues/5)

### On

To enable biome spoofing, enter a valid [biome id](https://minecraft.wiki/w/Biome#Biome_IDs) into the text widget.

### Off

To disable biome spoofing, simply leave the text empty.

## Block Rotations

### Random Rotations

If enabled, block rotations will be offset by a specified value, else block rotations will be offset by the coordinates offsetting your F3 position.

### Reload Rotations

Generates a new random value for "Random Rotations" and reloads the world.
