package com.customwrld.customlib.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InventoryUtil {

    public static boolean isSimilar(ItemStack first, ItemStack second) {

        boolean similar = false;
        boolean sameItemMeta = true;
        boolean sameType = (first.getType() == second.getType());
        boolean sameAmount = (first.getAmount() == second.getAmount());
        boolean sameHasItemMeta = (first.hasItemMeta() == second.hasItemMeta());
        boolean sameEnchantments = (first.getEnchantments().equals(second.getEnchantments()));

        if (sameHasItemMeta) {
            sameItemMeta = Bukkit.getItemFactory().equals(first.getItemMeta(), second.getItemMeta());
        }

        if (sameType && sameAmount && sameHasItemMeta && sameEnchantments && sameItemMeta) {
            similar = true;
        }
        return similar;
    }

    public static String itemStackArrayToBase64(ItemStack[] items) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(items.length);

            for (ItemStack item : items) {
                dataOutput.writeObject(item);
            }

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }


    public static ItemStack[] itemStackArrayFromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];

            for (int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) dataInput.readObject();
            }

            dataInput.close();
            return items;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    public static boolean freeSpace(Inventory inventory) {
        for (ItemStack itemStack : inventory.getContents()) {
            if (itemStack == null) {
                return true;
            }
        }
        return false;
    }

    public static int freeSlots(Inventory inventory) {
        int i = 0;
        for (ItemStack is : inventory.getContents()) {
            if (is != null && is.getType() != Material.AIR) {
                continue;
            }
            i++;
        }
        return i;
    }

    public static boolean canFitItemStack(Inventory inventory, ItemStack itemStack) {
        if (freeSpace(inventory)) return true;
        for (ItemStack inventoryItem : inventory.getContents()) {
            if (inventoryItem.isSimilar(itemStack) && inventoryItem.getAmount() < inventoryItem.getMaxStackSize()) {
                return true;
            }
        }
        return false;
    }

    public static void randomizeItemPlacement(Inventory inventory) {
        Random random = new Random();

        List<ItemStack> items = new ArrayList<>();
        inventory.forEach(item -> {
            if(item != null && item.getType() != Material.AIR) {
                items.add(item.clone());
            }
        });

        inventory.clear();

        for(ItemStack item : items) {
            int i = 0;
            while (true) {
                if(random.nextInt(inventory.getSize()) == 0) {
                    inventory.setItem(i, item);
                    break;
                }
                i = (i + 1) % inventory.getSize();
            }
        }
    }

}
