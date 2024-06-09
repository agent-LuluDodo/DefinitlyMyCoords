package de.luludodo.dmc.api;

import de.luludodo.dmc.modes.custom.RelativeF3Coords;
import de.luludodo.dmc.config.ConfigAPI;
import de.luludodo.dmc.modes.Mode;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldView;

public class DMCApi {

    public static long getOffsetBlockX(long x) {
        if (isVanilla()) return x;

        if (isCustom())
            return x - RelativeF3Coords.getOldBlockX();

        return x + (long) Math.ceil(ConfigAPI.getOffsetX());
    }
    public static long getOffsetBlockY(long y) {
        if (isVanilla()) return y;

        if (isCustom())
            return y - RelativeF3Coords.getOldBlockY();

        return y + (long) Math.ceil(ConfigAPI.getOffsetY());
    }
    public static long getOffsetBlockZ(long z) {
        if (isVanilla())
            return z;

        if (isCustom())
            return z - RelativeF3Coords.getOldBlockZ();

        return z + (long) Math.ceil(ConfigAPI.getOffsetZ());
    }
    public static BlockPos getOffsetBlock(BlockPos pos) {
        return new BlockPos((int) getOffsetBlockX(pos.getX()), (int) getOffsetBlockY(pos.getY()), (int) getOffsetBlockZ(pos.getZ()));
    }

    public static double getOffsetX(double x) {
        if (isVanilla())
            return x;

        if (isCustom())
            return x - RelativeF3Coords.getOldX();

        return x + ConfigAPI.getOffsetX();
    }
    public static double getOffsetY(double y) {
        if (isVanilla())
            return y;

        if (isCustom())
            return y - RelativeF3Coords.getOldY();

        return y + ConfigAPI.getOffsetY();
    }
    public static double getOffsetZ(double z) {
        if (isVanilla())
            return z;

        if (isCustom())
            return z - RelativeF3Coords.getOldZ();

        return z + ConfigAPI.getOffsetZ();
    }
    public static Vec3d getOffset(Vec3d pos) {
        return new Vec3d(getOffsetX(pos.getX()), getOffsetY(pos.getY()), getOffsetZ(pos.getZ()));
    }

    public static boolean isCustom() {
        return ConfigAPI.getMode() == Mode.CUSTOM;
    }

    public static boolean isVanilla() {
        return ConfigAPI.getMode() == Mode.VANILLA;
    }

    public static boolean obscureRotations() {
        return ConfigAPI.getObscureRotations();
    }

    public static boolean isValidBiome(WorldView world, Identifier biome) {
        return world.getRegistryManager().get(RegistryKeys.BIOME).containsId(biome);
    }
}
