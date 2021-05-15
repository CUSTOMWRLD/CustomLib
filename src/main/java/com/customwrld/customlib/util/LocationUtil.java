package com.customwrld.customlib.util;

import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class LocationUtil {

    public static String parseToString(Location location) {
        return location.getX() + "," +
                location.getY() + "," +
                location.getZ() + "," +
                location.getYaw() + "," +
                location.getPitch() + "," +
                location.getWorld().getName();
    }

    public static Location parseToLocation(String string) {
        if (string == null) return null;
        String[] data = string.split(",");
        try {
            double x = Double.parseDouble(data[0]);
            double y = Double.parseDouble(data[1]);
            double z = Double.parseDouble(data[2]);
            float pitch = Float.parseFloat(data[4]);
            float yaw = Float.parseFloat(data[3]);
            org.bukkit.World world = Bukkit.getWorld(data[5]);
            return new Location(world, x, y, z, yaw, pitch);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static JsonObject parseToJson(Location location) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("x", location.getX());
        jsonObject.addProperty("y", location.getY());
        jsonObject.addProperty("z", location.getZ());
        jsonObject.addProperty("yaw", location.getYaw());
        jsonObject.addProperty("pitch", location.getPitch());
        jsonObject.addProperty("world", location.getWorld().getName());
        return jsonObject;
    }

    public static Location parseToLocation(JsonObject jsonObject) {
        double x = jsonObject.get("x").getAsDouble();
        double y = jsonObject.get("y").getAsDouble();
        double z = jsonObject.get("z").getAsDouble();
        float yaw = jsonObject.get("yaw").getAsFloat();
        float pitch = jsonObject.get("pitch").getAsFloat();
        World world = Bukkit.getWorld(jsonObject.get("world").getAsString());
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static Location absoluteLocation(Location location) {
        return location.clone().set(Math.abs(location.getX()), Math.abs(location.getY()), Math.abs(location.getZ()));
    }

    public static Location additiveInverseLocation(Location location) {
        return location.clone().set(-location.getX(), -location.getY(), -location.getZ());
    }

    /*
    TODO Reverse the calculation, so the origin is the first argument.
     This might take a bit, as all the usages of this function also need to have their arguments switched.
     */
    public static Location offsetLocation(Location other, Location origin) {
        return additiveInverseLocation(other.clone().subtract(origin));
    }

    /***
     * Based on {@link Location#equals(Object)}, but checks only two coordinates, X and Z.
     * Doesn't check if angles match, use {@link LocationUtil#equals2DIncludeAngles(Location, Location)} for that purpose.
     * @param location
     * @param other
     */
    public static boolean equals2D(@NotNull Location location, @NotNull Location other) {
        if (location.getClass() != other.getClass()) { return false; }

        World world = location.getWorld();
        if(world == null) { return false; }

        World otherWorld = other.getWorld();
        if(otherWorld == null) { return false; }

        if (!Objects.equals(world, otherWorld)) { return false; }

        if (Double.doubleToLongBits(location.getX()) != Double.doubleToLongBits(other.getX())) { return false; }
        if (Double.doubleToLongBits(location.getZ()) != Double.doubleToLongBits(other.getZ())) { return false; }

        return true;
    }

    /***
     * Based on {@link Location#equals(Object)}, but checks only two coordinates, X and Z.
     * Checks if angles match, use {@link LocationUtil#equals2D(Location, Location)} if you wish not to.
     * @param location
     * @param other
     */
    public static boolean equals2DIncludeAngles(@NotNull Location location, @NotNull Location other) {
        if (location.getClass() != other.getClass()) { return false; }

        World world = location.getWorld();
        if(world == null) { return false; }

        World otherWorld = other.getWorld();
        if(otherWorld == null) { return false; }

        if (!Objects.equals(world, otherWorld)) { return false; }

        if (Double.doubleToLongBits(location.getX()) != Double.doubleToLongBits(other.getX())) { return false; }
        if (Double.doubleToLongBits(location.getZ()) != Double.doubleToLongBits(other.getZ())) { return false; }
        if (Float.floatToIntBits(location.getPitch()) != Float.floatToIntBits(other.getPitch())) { return false; }
        if (Float.floatToIntBits(location.getYaw()) != Float.floatToIntBits(other.getYaw())) { return false; }

        return true;
    }

    /***
     * Allows you to copy angles (Pitch & Yaw) from one {@link Location} to another.
     * Both modifies the "to" {@link Location} and returns the modified location (not a copy) to allow for partial chaining.
     * @param from {@link Location} to copy angles from
     * @param to {@link Location} to copy angles to
     * @return Modified {@link Location}
     */
    public static Location copyAngles(@NotNull Location from, @NotNull Location to) {
        if (from.getClass() != to.getClass()) { return to; }

        to.setPitch(from.getPitch());
        to.setYaw(from.getYaw());

        return to;
    }

}
