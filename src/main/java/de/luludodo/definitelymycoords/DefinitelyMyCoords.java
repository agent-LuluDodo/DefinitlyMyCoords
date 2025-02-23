package de.luludodo.definitelymycoords;

import de.luludodo.definitelymycoords.config.Config;
import de.luludodo.definitelymycoords.keybinds.Keybindings;
import de.luludodo.definitelymycoords.modes.custom.RelativeF3Coords;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefinitelyMyCoords implements ClientModInitializer {
    public static final Logger DEBUG = LoggerFactory.getLogger("DefinitelyMyCoords DEBUG");
    public static final Logger LOG = LoggerFactory.getLogger("DefinitelyMyCoords");
    public static final Config CONFIG = new Config();
    @Override
    public void onInitializeClient() {
        Keybindings.register();
        RelativeF3Coords.init();
    }
}
