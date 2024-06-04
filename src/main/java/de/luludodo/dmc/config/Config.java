package de.luludodo.dmc.config;

import de.luludodo.dmc.api.config.JsonMapConfig;
import de.luludodo.dmc.config.serializer.ConfigSerializer;
import de.luludodo.dmc.coords.Mode;
import net.minecraft.util.Identifier;

import java.util.Map;

public class Config extends JsonMapConfig<String, Object> {
    public Config() {
        super("dmc/config", new ConfigSerializer());
    }

    @Override
    protected Map<String, Object> getDefaults() {
        return Map.of(
                "mode", Mode.RELATIVE,
                "offset-x", 0L,
                "offset-y", 0L,
                "offset-z", 0L,
                "obscure-rotations", false,
                "spoof-biome", false,
                "biome", new Identifier("minecraft", "plains")
        );
    }
}
