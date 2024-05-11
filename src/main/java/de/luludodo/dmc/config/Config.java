package de.luludodo.dmc.config;

import com.google.gson.*;

import java.util.Map;
import java.util.function.Function;

public abstract class Config implements JsonSerializer<Map<String, Object>>, JsonDeserializer<Map<String, Object>> {
    protected final String filename;
    public Config(String filename) {
        this.filename = filename;
    }

    abstract Map<String, Object> getDefaultConfig();

    @SuppressWarnings("unchecked")
    protected <T> T getOrDefault(JsonObject config, String memberName, Function<JsonElement, T> parse) {
        return config.has(memberName)? parse.apply(config.get(memberName)) : (T) getDefaultConfig().get(memberName);
    }
}
