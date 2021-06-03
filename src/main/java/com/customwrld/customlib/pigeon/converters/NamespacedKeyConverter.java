package com.customwrld.customlib.pigeon.converters;

import com.customwrld.pigeon.Converter;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.bukkit.NamespacedKey;

import java.lang.reflect.Type;

public class NamespacedKeyConverter extends Converter<NamespacedKey> {

    public NamespacedKeyConverter() { super(NamespacedKey.class, true, false); }

    @Override
    public NamespacedKey convertToValue(Type fieldType, JsonElement element) {
        return Utils.convertToValue(element);
    }

    @Override
    public JsonElement convertToSimple(Type fieldType, NamespacedKey value) {
        return Utils.convertToSimple(value);
    }

    public static class Utils {
        public static NamespacedKey convertToValue(JsonElement element) {
            JsonObject data = element.getAsJsonObject();
            return new NamespacedKey(data.get("namespace").getAsString(), data.get("key").getAsString());
        }

        public static JsonElement convertToSimple(NamespacedKey value) {
            JsonObject data = new JsonObject();

            data.add("namespace", new JsonPrimitive(value.getNamespace()));
            data.add("key", new JsonPrimitive(value.getKey()));

            return data;
        }
    }

}
