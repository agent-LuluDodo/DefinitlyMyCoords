package de.luludodo.dmc.config.serializer;

import com.google.gson.*;
import de.luludodo.dmc.api.config.serializer.MapSerializer;
import de.luludodo.dmc.modes.Mode;
import net.minecraft.util.Identifier;

import java.lang.reflect.Type;
import java.util.HashMap;

import static de.luludodo.dmc.api.config.serializer.MapSerializer.get;

public class ConfigSerializer implements MapSerializer<String, Object> {
    @Override
    public HashMap<String, Object> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        HashMap<String, Object> config = new HashMap<>();
        JsonObject configObject = jsonElement.getAsJsonObject();
        config.put("mode", get(configObject, "mode", json -> Mode.valueOf(json.getAsString())));
        config.put("offset-x", get(configObject, "offset-x", JsonElement::getAsLong));
        config.put("offset-y", get(configObject, "offset-y", JsonElement::getAsLong));
        config.put("offset-z", get(configObject, "offset-z", JsonElement::getAsLong));
        config.put("obscure-rotations", get(configObject, "obscure-rotations", JsonElement::getAsBoolean));
        config.put("spoof-biome", get(configObject, "spoof-biome", JsonElement::getAsBoolean));
        config.put("biome", get(configObject, "biome", json -> Identifier.tryParse(json.getAsString())));
        return config;
    }

    @Override
    public JsonElement serialize(HashMap<String, Object> config, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject configObject = new JsonObject();
        configObject.add("mode", new JsonPrimitive(config.get("mode").toString()));
        configObject.add("offset-x", new JsonPrimitive((long) config.get("offset-x")));
        configObject.add("offset-y", new JsonPrimitive((long) config.get("offset-y")));
        configObject.add("offset-z", new JsonPrimitive((long) config.get("offset-z")));
        configObject.add("obscure-rotations", new JsonPrimitive((boolean) config.get("obscure-rotations")));
        configObject.add("spoof-biome", new JsonPrimitive((boolean) config.get("spoof-biome")));
        configObject.add("biome", new JsonPrimitive(config.get("biome").toString()));
        return configObject;
    }
}
