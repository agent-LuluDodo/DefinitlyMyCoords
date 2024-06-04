package de.luludodo.dmc.client;

import de.luludodo.dmc.RelativeF3Coords;
import de.luludodo.dmc.config.Config;
import de.luludodo.dmc.keybinds.Keybindings;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefinitelyMyCoordsClient implements ClientModInitializer {
    public static final Logger log = LoggerFactory.getLogger("Definitely My Coords");
    public static final Config config = new Config();
    @Override
    public void onInitializeClient() {
        Keybindings.register();
        RelativeF3Coords.init();
    }
}
