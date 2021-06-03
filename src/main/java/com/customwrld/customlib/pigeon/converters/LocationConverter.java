package com.customwrld.customlib.pigeon.converters;

import com.customwrld.pigeon.Converter;
import com.customwrld.pigeon.converters.StringConverter;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.lang.reflect.Type;
import java.util.Map;

public class LocationConverter extends Converter<Location> {

    public LocationConverter() { super(Location.class, true, false); }

    @Override
    public Location convertToValue(Type fieldType, JsonElement element) {
        return Utils.convertToValue(element);
    }

    @Override
    public JsonElement convertToSimple(Type fieldType, Location value) {
        return Utils.convertToSimple(value);
    }

    public static class Utils {
        /** Receiving same data like {@link Location#deserialize(Map)} ()}. */
        public static Location convertToValue(JsonElement element) {
            var data = element.getAsJsonObject();

            return new Location(
                    data.has("world") ? Bukkit.getWorld(StringConverter.Utils.convertToValue(data.get("world"))) : null,

                    data.get("x").getAsDouble(),
                    data.get("y").getAsDouble(),
                    data.get("z").getAsDouble(),

                    data.get("yaw").getAsFloat(),
                    data.get("pitch").getAsFloat()
            );
        }

        /** Sending same data like {@link Location#serialize()}. */
        public static JsonElement convertToSimple(Location value) {
            JsonObject data = new JsonObject();

            World world = value.getWorld();
            if(world != null) {
                data.add("world", StringConverter.Utils.convertToSimple(value.getWorld().getName()));
            }

            data.add("x", new JsonPrimitive(value.getX()));
            data.add("y", new JsonPrimitive(value.getY()));
            data.add("z", new JsonPrimitive(value.getZ()));

            data.add("yaw", new JsonPrimitive(value.getYaw()));
            data.add("pitch", new JsonPrimitive(value.getPitch()));

            return data;
        }
    }

}
