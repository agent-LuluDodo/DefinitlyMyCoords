package de.luludodo.dmc.config;

import de.luludodo.dmc.client.DefinitelyMyCoordsClient;
import de.luludodo.dmc.coords.Mode;
import net.minecraft.util.Identifier;

public class ConfigAPI {
    public static Mode getMode() {
        return (Mode) DefinitelyMyCoordsClient.config.get("mode");
    }

    public static long getOffsetX() {
        return (long) DefinitelyMyCoordsClient.config.get("offset-x");
    }

    public static long getOffsetY() {
        return (long) DefinitelyMyCoordsClient.config.get("offset-y");
    }

    public static long getOffsetZ() {
        return (long) DefinitelyMyCoordsClient.config.get("offset-z");
    }

    public static boolean getObscureRotations() {
        return (boolean) DefinitelyMyCoordsClient.config.get("obscure-rotations");
    }

    public static boolean getSpoofBiome() {
        return (boolean) DefinitelyMyCoordsClient.config.get("spoof-biome");
    }

    public static Identifier getBiome() {
        return (Identifier) DefinitelyMyCoordsClient.config.get("biome");
    }

    public static void setMode(Mode mode) {
        DefinitelyMyCoordsClient.config.set("mode", mode);
    }

    public static void setOffsetX(long offsetX) {
        DefinitelyMyCoordsClient.config.set("offset-x", offsetX);
    }

    public static void setOffsetY(long offsetY) {
        DefinitelyMyCoordsClient.config.set("offset-y", offsetY);
    }

    public static void setOffsetZ(long offsetZ) {
        DefinitelyMyCoordsClient.config.set("offset-z", offsetZ);
    }

    public static void setObscureRotations(boolean obscureRotations) {
        DefinitelyMyCoordsClient.config.set("obscure-rotations", obscureRotations);
    }

    public static void setSpoofBiome(boolean spoofBiome) {
        DefinitelyMyCoordsClient.config.set("spoof-biome", spoofBiome);
    }

    public static void setBiome(Identifier biome) {
        DefinitelyMyCoordsClient.config.set("biome", biome);
    }
}
