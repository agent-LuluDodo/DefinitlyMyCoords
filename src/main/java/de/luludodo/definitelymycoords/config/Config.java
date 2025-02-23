package de.luludodo.definitelymycoords.config;

import de.luludodo.definitelymycoords.api.config.JsonMapConfig;
import de.luludodo.definitelymycoords.config.serializer.ConfigSerializer;
import de.luludodo.definitelymycoords.modes.Mode;
import net.minecraft.util.Identifier;

import java.util.Map;

public class Config extends JsonMapConfig<String, Object> {
    public Config() {
        super("definitelymycoords/config", new ConfigSerializer(), "dmc/config");
    }

    @Override
    protected Map<String, Object> getDefaults() {
        return Map.of(
                "mode", Mode.VANILLA,
                "offset-x", 0L,
                "offset-y", 0L,
                "offset-z", 0L,
                "obscure-rotations", true,
                "spoof-biome", false,
                "biome", new Identifier("minecraft", "plains")
        );
    }
}
