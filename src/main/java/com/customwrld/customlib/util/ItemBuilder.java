package com.customwrld.customlib.util;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

public class ItemBuilder {

    private final HashMap<Enchantment, Integer> enchantments = new HashMap<>();
    private final Set<ItemFlag> flags = new HashSet<>();
    private final ItemStack itemStack;
    private Material material;
    private Short durability;
    private String title;
    private int amount = 1;
    private List<String> lores;
    private boolean unbreakable = false;
    private Color color;
    private Player headOwner;
    private PotionData potionData;
    private List<PotionEffect> potionEffects;

    public ItemBuilder() {
        this.itemStack = new ItemStack(Material.AIR);
    }

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public static String itemTo64(ItemStack stack) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeObject(stack);

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stack.", e);
        }
    }

    public static ItemStack itemFrom64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            try (BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream)) {
                return (ItemStack) dataInput.readObject();
            }
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    public ItemBuilder material(Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder durability(short durability) {
        this.durability = durability;
        return this;
    }

    public ItemBuilder title(String title) {
        this.title = title;
        return this;
    }

    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder lores(List<String> lores) {
        this.lores = lores;
        return this;
    }

    public ItemBuilder lores(String... lores) {
        this.lores = Arrays.asList(lores);
        return this;
    }

    public ItemBuilder enchantment(final Enchantment enchantment, final int level) {
        this.enchantments.put(enchantment, level);
        return this;
    }

    public ItemBuilder enchantment(final Enchantment enchantment) {
        this.enchantment(enchantment, 1);
        return this;
    }

    public ItemBuilder clearLore() {
        lores.clear();
        return this;
    }

    public ItemBuilder data(final int data) {
        byte materialData = (byte) data;
        return this;
    }

    public ItemBuilder clearEnchantments() {
        this.enchantments.clear();
        return this;
    }

    public ItemBuilder unbreakable(boolean b1) {
        this.unbreakable = b1;
        return this;
    }

    public ItemBuilder addFlag(ItemFlag flag){
        this.flags.add(flag);
        return this;
    }

    public ItemBuilder color(Color color) {
        this.color = color;
        return this;
    }

    public ItemBuilder headOwner(Player player){
        this.headOwner = player;
        return this;
    }

    public ItemBuilder basePotionData(PotionData potionData){
        this.potionData = potionData;
        return this;
    }

    public ItemBuilder customEffects(List<PotionEffect> potionEffects){
        this.potionEffects = potionEffects;
        return this;
    }

    public ItemStack build() {
        ItemStack itemStack = this.itemStack;
        if (this.material != null) {
            itemStack.setType(this.material);
        }
        for (Enchantment enchantment : enchantments.keySet()) {
            itemStack.addUnsafeEnchantment(enchantment, enchantments.get(enchantment));
        }
        ItemMeta meta = itemStack.getItemMeta();
        meta.setUnbreakable(this.unbreakable);
        if (this.amount > 0)
            itemStack.setAmount(this.amount);
        if (this.durability != null)
            itemStack.setDurability(this.durability);
        if (this.title != null)
            meta.setDisplayName(CC.translate("&r" + this.title));
        if (this.lores != null && this.lores.size() > 0)
            meta.setLore(CC.translate(this.lores));
        if (this.flags.size() > 0)
            meta.addItemFlags(flags.toArray(new ItemFlag[0]));

        if (this.color != null) {
            if (!(meta instanceof LeatherArmorMeta))
                throw new UnsupportedOperationException("Tried to set Color on ItemMeta that isn't LeatherArmorMeta.");
            ((LeatherArmorMeta) meta).setColor(color);
        }

        if (this.headOwner != null){
            if (!(meta instanceof SkullMeta))
                throw new UnsupportedOperationException("Tried to set OwningPlayer on ItemMeta that isn't SkullMeta.");
            ((SkullMeta) meta).setOwningPlayer(headOwner);
        }

        if (this.potionData != null){
            if (!(meta instanceof PotionMeta))
                throw new UnsupportedOperationException("Tried to set CustomEffect on ItemMeta that isn't PotionMeta.");
            ((PotionMeta) meta).setBasePotionData(potionData);
        }
        if (this.potionEffects != null){
            if (!(meta instanceof PotionMeta))
                throw new UnsupportedOperationException("Tried to set CustomEffect on ItemMeta that isn't PotionMeta.");
            PotionMeta potionMeta = (PotionMeta) meta;
            potionEffects.forEach(potionEffect -> potionMeta.addCustomEffect(potionEffect, true));
        }
        itemStack.setItemMeta(meta);
        return itemStack;
    }

}