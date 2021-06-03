package com.customwrld.customlib.pigeon.converters;

import com.customwrld.pigeon.Converter;
import com.customwrld.pigeon.converters.UUIDConverter;
import com.google.gson.JsonElement;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.lang.reflect.Type;

public class OfflinePlayerConverter extends Converter<OfflinePlayer> {

    public OfflinePlayerConverter() { super(OfflinePlayer.class, true, false); }

    @Override
    public OfflinePlayer convertToValue(Type fieldType, JsonElement element) {
        return Utils.convertToValue(element);
    }

    @Override
    public JsonElement convertToSimple(Type fieldType, OfflinePlayer value) {
        return Utils.convertToSimple(value);
    }

    public static class Utils {
        public static OfflinePlayer convertToValue(JsonElement element) {
            return Bukkit.getOfflinePlayer(UUIDConverter.Utils.convertToValue(element));
        }

        public static JsonElement convertToSimple(OfflinePlayer value) {
            return UUIDConverter.Utils.convertToSimple(value.getUniqueId());
        }
    }

}
