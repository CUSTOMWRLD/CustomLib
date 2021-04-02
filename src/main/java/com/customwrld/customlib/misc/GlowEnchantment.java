package com.customwrld.customlib.misc;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;

public class GlowEnchantment extends EnchantmentWrapper {
    private static EnchantmentWrapper enchant;

    public static EnchantmentWrapper getGlowEnchantment() {
        return enchant;
    }

    public GlowEnchantment(String id) {
        super(id);
    }

    public static void register() {
        try {
            if (!Enchantment.isAcceptingRegistrations()) {
                Field f = Enchantment.class.getDeclaredField("acceptingNew");
                f.setAccessible(true);
                f.set(null, true);

                enchant = new GlowEnchantment("glow");
                Enchantment.registerEnchantment(enchant);

                f.set(null, false);
                f.setAccessible(false);
            } else {
                enchant = new GlowEnchantment("glow");
                Enchantment.registerEnchantment(enchant);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public int getStartLevel() {
        return 0;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return null;
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return true;
    }

    @Override
    public String getName() {
        return "Glow";
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(Enchantment other) {
        return false;
    }
}