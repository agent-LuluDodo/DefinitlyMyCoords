package de.luludodo.dmc.api;

import de.luludodo.dmc.RelativeF3Coords;
import de.luludodo.dmc.config.ConfigAPI;
import de.luludodo.dmc.coords.Mode;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import org.spongepowered.asm.mixin.Unique;

public class DMCApi {

    public static long getOffsetBlockX(long x) {
        if (isCustom()) {
            return x - RelativeF3Coords.getOldBlockX();
        }
        return x + (long) Math.ceil(ConfigAPI.getOffsetX());
    }
    public static long getOffsetBlockY(long y) {
        if (isCustom()) {
            return y - RelativeF3Coords.getOldBlockY();
        }
        return y + (long) Math.ceil(ConfigAPI.getOffsetY());
    }
    public static long getOffsetBlockZ(long z) {
        if (isCustom()) {
            return z - RelativeF3Coords.getOldBlockZ();
        }
        return z + (long) Math.ceil(ConfigAPI.getOffsetZ());
    }
    public static BlockPos getOffsetBlock(BlockPos pos) {
        return new BlockPos((int) getOffsetBlockX(pos.getX()), (int) getOffsetBlockY(pos.getY()), (int) getOffsetBlockZ(pos.getZ()));
    }

    public static double getOffsetX(double x) {
        if (isCustom()) {
            return x - RelativeF3Coords.getOldX();
        }
        return x + ConfigAPI.getOffsetX();
    }
    public static double getOffsetY(double y) {
        if (isCustom()) {
            return y - RelativeF3Coords.getOldY();
        }
        return y + ConfigAPI.getOffsetY();
    }
    public static double getOffsetZ(double z) {
        if (isCustom()) {
            return z - RelativeF3Coords.getOldZ();
        }
        return z + ConfigAPI.getOffsetZ();
    }

    public static boolean isCustom() {
        return ConfigAPI.getMode() == Mode.CUSTOM;
    }

    public static boolean obscureRotations() {
        return ConfigAPI.getObscureRotations();
    }

    public static boolean isValidBiome(WorldView world, Identifier biome) {
        return world.getRegistryManager().get(RegistryKeys.BIOME).containsId(biome);
    }
}
