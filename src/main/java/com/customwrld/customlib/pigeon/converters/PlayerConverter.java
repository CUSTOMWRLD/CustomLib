package com.customwrld.customlib.pigeon.converters;

import com.customwrld.pigeon.Converter;
import com.customwrld.pigeon.converters.UUIDConverter;
import com.google.gson.JsonElement;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Type;

public class PlayerConverter extends Converter<Player> {

    public PlayerConverter() { super(Player.class, true, false); }

    @Override
    public Player convertToValue(Type fieldType, JsonElement element) {
        return Utils.convertToValue(element);
    }

    @Override
    public JsonElement convertToSimple(Type fieldType, Player value) {
        return Utils.convertToSimple(value);
    }

    public static class Utils {
        public static Player convertToValue(JsonElement element) {
            return Bukkit.getPlayer(UUIDConverter.Utils.convertToValue(element));
        }

        public static JsonElement convertToSimple(Player value) {
            return UUIDConverter.Utils.convertToSimple(value.getUniqueId());
        }
    }

}
