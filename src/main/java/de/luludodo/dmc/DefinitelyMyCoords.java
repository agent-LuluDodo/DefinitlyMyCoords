package de.luludodo.dmc;

import de.luludodo.dmc.config.Config;
import de.luludodo.dmc.modes.custom.RelativeF3Coords;
import de.luludodo.dmc.keybinds.Keybindings;
import net.fabricmc.api.ClientModInitializer;

public class DefinitelyMyCoords implements ClientModInitializer {
    public static final Config CONFIG = new Config();
    @Override
    public void onInitializeClient() {
        Keybindings.register();
        RelativeF3Coords.init();
    }
}
