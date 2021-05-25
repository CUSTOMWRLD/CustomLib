package com.customwrld.customlib.pigeon.converters;

import com.customwrld.pigeon.Converter;
import com.customwrld.pigeon.converters.StringConverter;
import org.bukkit.Material;

public class MaterialConverter extends Converter<Material> {
    public MaterialConverter() { super(Material.class); }

    @Override
    public Material convertToValue(Object simple) {
        return Utils.convertToValue(simple);
    }

    @Override
    public Object convertToSimple(Material value) {
        return Utils.convertToSimple(value);
    }

    public static class Utils {
        public static Material convertToValue(Object simple) {
            return Material.getMaterial(StringConverter.Utils.convertToValue(simple));
        }

        public static Object convertToSimple(Material value) {
            return StringConverter.Utils.convertToSimple(value.toString());
        }
    }
}
