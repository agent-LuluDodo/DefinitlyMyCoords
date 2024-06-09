package de.luludodo.dmc.keybinds;

import de.luludodo.dmc.config.ConfigAPI;
import de.luludodo.dmc.gui.ConfigScreen;
import de.luludodo.dmc.modes.Mode;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Keybindings {
    public static void register() {
        // KeyBindings
        KeyBinding configKeyBinding = generate("config", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F6);
        KeyBinding modeVanillaKeyBinding = generate("mode-vanilla", InputUtil.Type.KEYSYM, -1);
        KeyBinding modeAbsoluteKeyBinding = generate("mode-absolute", InputUtil.Type.KEYSYM, -1);
        KeyBinding modeRelativeKeyBinding = generate("mode-relative", InputUtil.Type.KEYSYM, -1);
        KeyBinding modeCustomKeyBinding = generate("mode-custom", InputUtil.Type.KEYSYM, -1);
        // END KeyBindings
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Variables
            GameOptions gameOptions = client.options;
            // Call Method
            if (configKeyBinding.wasPressed())
                client.setScreen(new ConfigScreen(gameOptions));
            if (modeCustomKeyBinding.wasPressed())
                ConfigAPI.setMode(Mode.CUSTOM);
            if (modeRelativeKeyBinding.wasPressed())
                ConfigAPI.setMode(Mode.RELATIVE);
            if (modeAbsoluteKeyBinding.wasPressed())
                ConfigAPI.setMode(Mode.ABSOLUTE);
            if (modeVanillaKeyBinding.wasPressed())
                ConfigAPI.setMode(Mode.VANILLA);
            // END Call Method
        });
    }

    private static KeyBinding generate(String name, InputUtil.Type type, int key) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding("keybinding.dmc." + name, type, key, "category.dmc.keybindings"));
    }
}
