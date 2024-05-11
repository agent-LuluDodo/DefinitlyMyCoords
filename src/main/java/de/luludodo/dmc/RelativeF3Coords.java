package de.luludodo.dmc;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.Entity;

public class RelativeF3Coords {
    private static double oldX;
    private static double oldY;
    private static double oldZ;
    private static int oldBlockX;
    private static int oldBlockY;
    private static int oldBlockZ;
    private static boolean debugHudOpen = false;
    private static boolean closed = true;
    private static MinecraftClient CLIENT = null;

    public static void init() {
        // Initializer Class to register a tickEvent at START_CLIENT_TICK which sets oldX, oldY, etc. to the current corresponding coord
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            CLIENT = client;
            debugHudOpen = client.options.debugEnabled;
            if (debugHudOpen && closed) {
                // set oldX, oldY, etc. to corresponding coord
                oldX = getX();
                oldY = getY();
                oldZ = getZ();
                oldBlockX = getBlockX();
                oldBlockY = getBlockY();
                oldBlockZ = getBlockZ();
                // set closed to false so it only happens once
                closed = false;
            } else if (!debugHudOpen){
                // reset closed
                closed = true;
            }
        });
    }

    // A LOT OF GETTERS
    public static double getOldX() {
        return debugHudOpen? oldX : getX();
    }
    public static double getOldY() {
        return debugHudOpen? oldY : getY();
    }
    public static double getOldZ() {
        return debugHudOpen? oldZ : getZ();
    }
    public static int getOldBlockX() {
        return debugHudOpen? oldBlockX : getBlockX();
    }
    public static int getOldBlockY() {
        return debugHudOpen? oldBlockY : getBlockY();
    }
    public static int getOldBlockZ() {
        return debugHudOpen? oldBlockZ : getBlockZ();
    }

    private static double getX() {
        Entity entity = CLIENT == null? null : CLIENT.getCameraEntity();
        return entity == null? 0 : entity.getX();
    }
    private static double getY() {
        Entity entity = CLIENT == null? null : CLIENT.getCameraEntity();
        return entity == null? 0 : entity.getY();
    }
    private static double getZ() {
        Entity entity = CLIENT == null? null : CLIENT.getCameraEntity();
        return entity == null? 0 : entity.getZ();
    }
    private static int getBlockX() {
        Entity entity = CLIENT == null? null : CLIENT.getCameraEntity();
        return entity == null? 0 : entity.getBlockX();
    }
    private static int getBlockY() {
        Entity entity = CLIENT == null? null : CLIENT.getCameraEntity();
        return entity == null? 0 : entity.getBlockY();
    }
    private static int getBlockZ() {
        Entity entity = CLIENT == null? null : CLIENT.getCameraEntity();
        return entity == null? 0 : entity.getBlockZ();
    }
}
