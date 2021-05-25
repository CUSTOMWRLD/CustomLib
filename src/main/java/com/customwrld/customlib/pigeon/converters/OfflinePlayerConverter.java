package com.customwrld.customlib.pigeon.converters;

import com.customwrld.pigeon.Converter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class OfflinePlayerConverter extends Converter<OfflinePlayer> {
    public OfflinePlayerConverter() { super(OfflinePlayer.class); }

    @Override
    public OfflinePlayer convertToValue(Object simple) {
        return Utils.convertToValue(simple);
    }

    @Override
    public Object convertToSimple(OfflinePlayer value) {
        return Utils.convertToSimple(value);
    }

    public static class Utils {
        public static OfflinePlayer convertToValue(Object simple) {
            return Bukkit.getOfflinePlayer(UUID.fromString((String) simple));
        }

        public static Object convertToSimple(OfflinePlayer value) {
            return value.getUniqueId().toString();
        }
    }
}
