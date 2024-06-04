package de.luludodo.dmc.api.config.serializer;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.function.Function;

public interface MapSerializer<K, V> extends JsonSerializer<HashMap<K, V>>, JsonDeserializer<HashMap<K, V>> {
    @Override
    JsonElement serialize(HashMap<K, V> config, Type type, JsonSerializationContext jsonSerializationContext);

    @Override
    HashMap<K, V> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException;

    static <R> R get(JsonObject json, String name, Function<JsonElement, R> action) {
        JsonElement value = json.get(name);
        if (value != null) {
            return action.apply(value);
        }
        return null;
    }
}
