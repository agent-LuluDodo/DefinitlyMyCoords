package de.luludodo.dmc.keybinds;

import de.luludodo.dmc.coords.DMCConfigScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Keybindings {
    public static void register() {
        // KeyBindings
        KeyBinding f5Keybinding = generate("config", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F6);
        // END KeyBindings
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Variables
            Screen screen = client.currentScreen;
            GameOptions gameOptions = client.options;
            // Call Method
            if (f5Keybinding.wasPressed())
                client.setScreen(new DMCConfigScreen(screen, gameOptions));
            // END Call Method
        });
    }

    private static KeyBinding generate(String name, InputUtil.Type type, int key) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding("keybinding.dmc." + name, type, key, "category.dmc.keybindings"));
    }
}
