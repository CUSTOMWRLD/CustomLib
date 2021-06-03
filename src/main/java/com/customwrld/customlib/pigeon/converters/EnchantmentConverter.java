package com.customwrld.customlib.pigeon.converters;

import com.customwrld.pigeon.Converter;
import com.google.gson.JsonElement;
import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Type;

public class EnchantmentConverter extends Converter<Enchantment> {

    public EnchantmentConverter() { super(Enchantment.class, true, false); }

    @Override
    public Enchantment convertToValue(Type fieldType, JsonElement element) {
        return Utils.convertToValue(element);
    }

    @Override
    public JsonElement convertToSimple(Type fieldType, Enchantment value) {
        return Utils.convertToSimple(value);
    }

    public static class Utils {
        public static Enchantment convertToValue(JsonElement element) {
            return Enchantment.getByKey(NamespacedKeyConverter.Utils.convertToValue(element));
        }

        public static JsonElement convertToSimple(Enchantment value) {
            return NamespacedKeyConverter.Utils.convertToSimple(value.getKey());
        }
    }

}
