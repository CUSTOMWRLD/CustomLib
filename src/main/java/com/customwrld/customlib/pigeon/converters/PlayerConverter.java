package com.customwrld.customlib.pigeon.converters;

import com.customwrld.pigeon.Converter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerConverter extends Converter<Player> {
    public PlayerConverter() { super(Player.class); }

    @Override
    public Player convertToValue(Object simple) {
        return Utils.convertToValue(simple);
    }

    @Override
    public Object convertToSimple(Player value) {
        return Utils.convertToSimple(value);
    }

    public static class Utils {
        public static Player convertToValue(Object simple) {
            return Bukkit.getPlayer(UUID.fromString((String) simple));
        }

        public static Object convertToSimple(Player value) {
            return value.getUniqueId().toString();
        }
    }
}
