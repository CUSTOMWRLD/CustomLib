package com.customwrld.customlib.pigeon.converters;

import com.customwrld.customlib.util.CollectionsUtil;
import com.customwrld.pigeon.Converter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class LocationConverter extends Converter<Location> {
    public LocationConverter() { super(Location.class); }

    @Override
    public Location convertToValue(Object simple) {
        return Utils.convertToValue(simple);
    }

    @Override
    public Object convertToSimple(Location value) {
        return Utils.convertToSimple(value);
    }

    public static class Utils {
        public static Location convertToValue(Object simple) {
            return Location.deserialize(
                    CollectionsUtil.genericMapToType((Map<?, ?>) simple, String.class, Object.class));
        }

        public static Object convertToSimple(Location value) {
            return value.serialize();
        }
    }
}
