package com.customwrld.customlib.util;

import com.destroystokyo.paper.profile.PlayerProfile;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.BlockState;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.TropicalFish;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.map.MapView;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class ItemBuilder {

    private final ItemStack itemStack;

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(ItemStack itemStack){
        this.itemStack = itemStack;
    }

    public ItemBuilder(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
    }

    public ItemBuilder setName(String name) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setDisplayName(CC.translate("&r" + name));
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setUnbreakable(unbreakable);
        return this;
    }

    public ItemBuilder setModel(int model) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setCustomModelData(model);
        return this;
    }

    public ItemBuilder setDamage(int damage) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof Damageable))
            throw new UnsupportedOperationException("Tried to use setDamage on ItemMeta that isn't Damageable.");

        ((Damageable) meta).setDamage(damage);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLeatherColor(Color color) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof LeatherArmorMeta))
            throw new UnsupportedOperationException("Tried to use setColor on ItemMeta that isn't LeatherArmorMeta.");

        ((LeatherArmorMeta) meta).setColor(color);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setPlayerProfile(PlayerProfile profile) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof SkullMeta))
            throw new UnsupportedOperationException("Tried to use setPlayerProfile on ItemMeta that isn't SkullMeta.");

        ((SkullMeta) meta).setPlayerProfile(profile);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setOwningPlayer(Player player) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof SkullMeta))
            throw new UnsupportedOperationException("Tried to use setOwningPlayer on ItemMeta that isn't SkullMeta.");

        ((SkullMeta) meta).setOwningPlayer(player);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setLore(lore);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder setLoreLine(String line, int pos){
        ItemMeta meta = this.itemStack.getItemMeta();

        List<String> lore = new ArrayList<>();

        if(meta.getLore() != null && meta.hasLore())
            lore = new ArrayList<>(meta.getLore());

        lore.set(pos, line);
        meta.setLore(lore);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addLoreLine(String line) {
        ItemMeta meta = this.itemStack.getItemMeta();

        List<String> lore = new ArrayList<>();

        if(meta.getLore() != null && meta.hasLore())
            lore = new ArrayList<>(meta.getLore());

        lore.add(line);
        meta.setLore(lore);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder clearLore() {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setLore(Collections.emptyList());
        return this;
    }

    public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments) {
        this.itemStack.addUnsafeEnchantments(enchantments);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        this.itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        this.itemStack.removeEnchantment(enchantment);
        return this;
    }

    public ItemBuilder clearEnchantments() {
        for(Enchantment enchantment : this.itemStack.getEnchantments().keySet()) {
            this.itemStack.removeEnchantment(enchantment);
        }

        return this;
    }

    public ItemBuilder addFlags(ItemFlag... flags) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder removeFlags(ItemFlag... flags) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.removeItemFlags(flags);
        return this;
    }

    public ItemBuilder addAttribute(Attribute attribute, AttributeModifier modifier) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.addAttributeModifier(attribute, modifier);
        return this;
    }

    public ItemBuilder removeAttribute(EquipmentSlot slot) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.removeAttributeModifier(slot);
        return this;
    }

    public ItemBuilder removeAttribute(Attribute attribute) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.removeAttributeModifier(attribute);
        return this;
    }

    public ItemBuilder removeAttribute(Attribute attribute, AttributeModifier modifier) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.removeAttributeModifier(attribute, modifier);
        return this;
    }

    public ItemBuilder setPotionData(PotionData potionData) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof PotionMeta))
                throw new UnsupportedOperationException("Tried to use setBasePotionData on ItemMeta that isn't PotionMeta.");

        ((PotionMeta) meta).setBasePotionData(potionData);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addPotionEffects(List<PotionEffect> effects) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof PotionMeta))
            throw new UnsupportedOperationException("Tried to use addCustomEffect on ItemMeta that isn't PotionMeta.");

        PotionMeta potionMeta = (PotionMeta) meta;
        effects.forEach(potionEffect -> potionMeta.addCustomEffect(potionEffect, true));
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addPotionEffects(PotionEffect... effects) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof PotionMeta))
            throw new UnsupportedOperationException("Tried to use addCustomEffect on ItemMeta that isn't PotionMeta.");

        PotionMeta potionMeta = (PotionMeta) meta;
        Arrays.asList(effects).forEach(potionEffect -> potionMeta.addCustomEffect(potionEffect, true));
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder removePotionEffects(List<PotionEffectType> types) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof PotionMeta))
            throw new UnsupportedOperationException("Tried to use removeCustomEffect on ItemMeta that isn't PotionMeta.");

        PotionMeta potionMeta = (PotionMeta) meta;
        types.forEach(potionMeta::removeCustomEffect);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder removePotionEffects(PotionEffectType... types) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof PotionMeta))
            throw new UnsupportedOperationException("Tried to use removeCustomEffect on ItemMeta that isn't PotionMeta.");

        PotionMeta potionMeta = (PotionMeta) meta;
        Arrays.asList(types).forEach(potionMeta::removeCustomEffect);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setBannerPatterns(List<Pattern> patterns) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof BannerMeta))
            throw new UnsupportedOperationException("Tried to use setPatterns on ItemMeta that isn't BannerMeta.");

        ((BannerMeta) meta).setPatterns(patterns);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setBannerPatterns(Pattern... patterns) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof BannerMeta))
            throw new UnsupportedOperationException("Tried to use setPatterns on ItemMeta that isn't BannerMeta.");

        ((BannerMeta) meta).setPatterns(Arrays.asList(patterns));
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setBannerPattern(Pattern pattern, Integer index) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof BannerMeta))
            throw new UnsupportedOperationException("Tried to use setPattern on ItemMeta that isn't BannerMeta.");

        ((BannerMeta) meta).setPattern(index, pattern);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addBannerPattern(Pattern pattern) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof BannerMeta))
            throw new UnsupportedOperationException("Tried to use addPattern on ItemMeta that isn't BannerMeta.");

        ((BannerMeta) meta).addPattern(pattern);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder removeBannerPattern(int index) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof BannerMeta))
            throw new UnsupportedOperationException("Tried to use removePattern on ItemMeta that isn't BannerMeta.");

        ((BannerMeta) meta).removePattern(index);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setBookPages(List<String> pages) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof BookMeta))
            throw new UnsupportedOperationException("Tried to use setPages on ItemMeta that isn't BookMeta.");

        ((BookMeta) meta).setPages(pages);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setBookPages(String... pages) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof BookMeta))
            throw new UnsupportedOperationException("Tried to use setPages on ItemMeta that isn't BookMeta.");

        ((BookMeta) meta).setPages(pages);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setBookAuthor(String author) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof BookMeta))
            throw new UnsupportedOperationException("Tried to use setAuthor on ItemMeta that isn't BookMeta.");

        ((BookMeta) meta).setAuthor(author);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setBookGeneration(BookMeta.Generation generation) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof BookMeta))
            throw new UnsupportedOperationException("Tried to use setGeneration on ItemMeta that isn't BookMeta.");

        ((BookMeta) meta).setGeneration(generation);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setBookTitle(String title) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof BookMeta))
            throw new UnsupportedOperationException("Tried to use setTitle on ItemMeta that isn't BookMeta.");

        ((BookMeta) meta).setTitle(title);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setBookPage(int page, String data) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof BookMeta))
            throw new UnsupportedOperationException("Tried to use setPage on ItemMeta that isn't BookMeta.");

        ((BookMeta) meta).setPage(page, data);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setCompassLodestone(Location lodestone) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof CompassMeta))
            throw new UnsupportedOperationException("Tried to use setLodestone on ItemMeta that isn't CompassMeta.");

        ((CompassMeta) meta).setLodestone(lodestone);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setCompassLodestoneTracked(boolean tracked) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof CompassMeta))
            throw new UnsupportedOperationException("Tried to use setLodestoneTracked on ItemMeta that isn't CompassMeta.");

        ((CompassMeta) meta).setLodestoneTracked(tracked);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setCrossbowChargedProjectiles(List<ItemStack> projectiles) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof CrossbowMeta))
            throw new UnsupportedOperationException("Tried to use setChargedProjectiles on ItemMeta that isn't CrossbowMeta.");

        ((CrossbowMeta) meta).setChargedProjectiles(projectiles);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setCrossbowChargedProjectiles(ItemStack... projectiles) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof CrossbowMeta))
            throw new UnsupportedOperationException("Tried to use setChargedProjectiles on ItemMeta that isn't CrossbowMeta.");

        ((CrossbowMeta) meta).setChargedProjectiles(Arrays.asList(projectiles));
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addCrossbowChargedProjectile(ItemStack projectile) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof CrossbowMeta))
            throw new UnsupportedOperationException("Tried to use addChargedProjectile on ItemMeta that isn't CrossbowMeta.");

        ((CrossbowMeta) meta).addChargedProjectile(projectile);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setMapColor(Color color) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof MapMeta))
            throw new UnsupportedOperationException("Tried to use setColor on ItemMeta that isn't MapMeta.");

        ((MapMeta) meta).setColor(color);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setMapLocationName(String name) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof MapMeta))
            throw new UnsupportedOperationException("Tried to use setLocationName on ItemMeta that isn't MapMeta.");

        ((MapMeta) meta).setLocationName(name);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setMapView(MapView mapView) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof MapMeta))
            throw new UnsupportedOperationException("Tried to use setMapView on ItemMeta that isn't MapMeta.");

        ((MapMeta) meta).setMapView(mapView);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setMapScaling(boolean scaling) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof MapMeta))
            throw new UnsupportedOperationException("Tried to use setScaling on ItemMeta that isn't MapMeta.");

        ((MapMeta) meta).setScaling(scaling);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setKnowledgeBookRecipes(List<NamespacedKey> recipes) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof KnowledgeBookMeta))
            throw new UnsupportedOperationException("Tried to use setRecipes on ItemMeta that isn't KnowledgeBookMeta.");

        ((KnowledgeBookMeta) meta).setRecipes(recipes);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setKnowledgeBookRecipes(NamespacedKey... recipes) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof KnowledgeBookMeta))
            throw new UnsupportedOperationException("Tried to use setRecipes on ItemMeta that isn't KnowledgeBookMeta.");

        ((KnowledgeBookMeta) meta).setRecipes(Arrays.asList(recipes));
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addKnowledgeBookRecipes(NamespacedKey... recipes) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof KnowledgeBookMeta))
            throw new UnsupportedOperationException("Tried to use addRecipe on ItemMeta that isn't KnowledgeBookMeta.");

        ((KnowledgeBookMeta) meta).addRecipe(recipes);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setRocketEffect(FireworkEffect fireworkEffect) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof FireworkEffectMeta))
            throw new UnsupportedOperationException("Tried to use setEffect on ItemMeta that isn't FireworkEffectMeta.");

        ((FireworkEffectMeta) meta).setEffect(fireworkEffect);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setRocketPower(int power) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof FireworkMeta))
            throw new UnsupportedOperationException("Tried to use setPower on ItemMeta that isn't FireworkMeta.");

        ((FireworkMeta) meta).setPower(power);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addRocketEffect(FireworkEffect fireworkEffect) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof FireworkMeta))
            throw new UnsupportedOperationException("Tried to use addEffect on ItemMeta that isn't FireworkMeta.");

        ((FireworkMeta) meta).addEffect(fireworkEffect);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addRocketEffects(Iterable<FireworkEffect> fireworkEffect) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof FireworkMeta))
            throw new UnsupportedOperationException("Tried to use addEffects on ItemMeta that isn't FireworkMeta.");

        ((FireworkMeta) meta).addEffects(fireworkEffect);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addRocketEffects(FireworkEffect... fireworkEffect) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof FireworkMeta))
            throw new UnsupportedOperationException("Tried to use addEffects on ItemMeta that isn't FireworkMeta.");

        ((FireworkMeta) meta).addEffects(fireworkEffect);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder clearRocketEffects() {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof FireworkMeta))
            throw new UnsupportedOperationException("Tried to use clearEffects on ItemMeta that isn't FireworkMeta.");

        ((FireworkMeta) meta).clearEffects();
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder removeRocketEffect(int index) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof FireworkMeta))
            throw new UnsupportedOperationException("Tried to use removeEffect on ItemMeta that isn't FireworkMeta.");

        ((FireworkMeta) meta).removeEffect(index);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addStewEffect(PotionEffect potionEffect) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof SuspiciousStewMeta))
            throw new UnsupportedOperationException("Tried to use addCustomEffect on ItemMeta that isn't SuspiciousStewMeta.");

        ((SuspiciousStewMeta) meta).addCustomEffect(potionEffect, true);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder removeStewEffect(PotionEffectType type) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof SuspiciousStewMeta))
            throw new UnsupportedOperationException("Tried to use removeCustomEffect on ItemMeta that isn't SuspiciousStewMeta.");

        ((SuspiciousStewMeta) meta).removeCustomEffect(type);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder clearStewEffects() {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof SuspiciousStewMeta))
            throw new UnsupportedOperationException("Tried to use clearCustomEffects on ItemMeta that isn't SuspiciousStewMeta.");

        ((SuspiciousStewMeta) meta).clearCustomEffects();
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setFishColor(DyeColor color) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof TropicalFishBucketMeta))
            throw new UnsupportedOperationException("Tried to use setBodyColor on ItemMeta that isn't TropicalFishBucketMeta.");

        ((TropicalFishBucketMeta) meta).setBodyColor(color);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setFishPatternColor(DyeColor color) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof TropicalFishBucketMeta))
            throw new UnsupportedOperationException("Tried to use setPatternColor on ItemMeta that isn't TropicalFishBucketMeta.");

        ((TropicalFishBucketMeta) meta).setPatternColor(color);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setFishPattern(TropicalFish.Pattern pattern) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof TropicalFishBucketMeta))
            throw new UnsupportedOperationException("Tried to use setPattern on ItemMeta that isn't TropicalFishBucketMeta.");

        ((TropicalFishBucketMeta) meta).setPattern(pattern);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addStoredEnchantment(Enchantment enchantment, int level) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof EnchantmentStorageMeta))
            throw new UnsupportedOperationException("Tried to use addStoredEnchant on ItemMeta that isn't EnchantmentStorageMeta.");

        ((EnchantmentStorageMeta) meta).addStoredEnchant(enchantment, level, true);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder removeStoredEnchantment(Enchantment enchantment) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof EnchantmentStorageMeta))
            throw new UnsupportedOperationException("Tried to use removeStoredEnchant on ItemMeta that isn't EnchantmentStorageMeta.");

        ((EnchantmentStorageMeta) meta).removeStoredEnchant(enchantment);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setBlockState(BlockState state) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof BlockStateMeta))
            throw new UnsupportedOperationException("Tried to use setBlockState on ItemMeta that isn't BlockStateMeta.");

        ((BlockStateMeta) meta).setBlockState(state);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setBlockData(BlockData data) {
        ItemMeta meta = this.itemStack.getItemMeta();

        if (!(meta instanceof BlockDataMeta))
            throw new UnsupportedOperationException("Tried to use setBlockData on ItemMeta that isn't BlockDataMeta.");

        ((BlockDataMeta) meta).setBlockData(data);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder clone() {
        return new ItemBuilder(itemStack);
    }

    public ItemStack build() {
        return this.itemStack;
    }

}