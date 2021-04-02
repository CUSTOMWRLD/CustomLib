package com.customwrld.customlib.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class ShapeUtil {

    public static List<Location> randomCylinder(Location origin, int radius, int height, float density) {
        List<Location> generatedLocations = ShapeUtil.getLocationsInCylinder(origin, radius, height);
        Random random = new Random();

        for(int i = generatedLocations.size() - 1; i >= 0; i--) {
            if(random.nextFloat() >= density) {
                generatedLocations.remove(i);
            }
        }

        return generatedLocations;
    }

    public static void setCylinder(Location loc, Material mat, int r, int h) {
        int centerX = loc.getBlockX();
        int centerY = loc.getBlockY();
        int centerZ = loc.getBlockZ();
        World w = loc.getWorld();
        int rSquared = r * r;
        for (int y = centerY; y < centerY + h; y++) {
            for (int x = centerX - r; x <= centerX + r; x++) {
                for (int z = centerZ - r; z <= centerZ + r; z++) {
                    if ((centerX - x) * (centerX - x) + (centerZ - z) * (centerZ - z) <= rSquared) {
                        w.getBlockAt(x, y, z).setType(mat);
                    }
                }
            }
        }
    }

    public static List<Location> getLocationsInCylinder(final Location loc, int r, int h) {
        List<Location> locations = new ArrayList<>();

        int centerX = loc.getBlockX();
        int centerY = loc.getBlockY();
        int centerZ = loc.getBlockZ();
        World w = loc.getWorld();
        int rSquared = r * r;
        for (int y = centerY; y < centerY + h; y++) {
            for (int x = centerX - r; x <= centerX + r; x++) {
                for (int z = centerZ - r; z <= centerZ + r; z++) {
                    if ((centerX - x) * (centerX - x) + (centerZ - z) * (centerZ - z) <= rSquared) {
                        locations.add(loc.clone().set(x, y, z));
                    }
                }
            }
        }

        return locations;
    }

    public static void callbackCylinder(final Location loc, int r, int h, Consumer<Location> onLocation) {
        int centerX = loc.getBlockX();
        int centerY = loc.getBlockY();
        int centerZ = loc.getBlockZ();
        World w = loc.getWorld();
        int rSquared = r * r;
        for (int y = centerY; y < centerY + h; y++) {
            for (int x = centerX - r; x <= centerX + r; x++) {
                for (int z = centerZ - r; z <= centerZ + r; z++) {
                    if ((centerX - x) * (centerX - x) + (centerZ - z) * (centerZ - z) <= rSquared) {
                        onLocation.accept(loc.clone().set(x, y, z));
                    }
                }
            }
        }
    }
}
