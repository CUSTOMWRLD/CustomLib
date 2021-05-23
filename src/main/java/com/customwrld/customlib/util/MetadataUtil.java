package com.customwrld.customlib.util;

import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class MetadataUtil {

    public static Object retrieveValueSafe(Metadatable metadatable, String key, Plugin owningPlugin, Object defaultValue, boolean set) {
        if(!metadatable.hasMetadata(key)) { return defaultValue; }

        List<MetadataValue> values = metadatable.getMetadata(key);
        if(values.size() <= 0) { return defaultValue; }

        Optional<MetadataValue> value = metadatable.getMetadata(key).stream()
                .filter(v -> Objects.equals(v.getOwningPlugin(), owningPlugin))
                .findFirst();

        if(!value.isPresent()) {
            if(set) { metadatable.setMetadata(key, new FixedMetadataValue(owningPlugin, defaultValue)); }
            return defaultValue;
        }

        return value.get().value();
    }

    public static byte getByte(Metadatable metadatable, String key, Plugin owningPlugin, byte defaultValue, boolean set) {
        return (byte) retrieveValueSafe(metadatable, key, owningPlugin, defaultValue, set);
    }

    public static int getInteger(Metadatable metadatable, String key, Plugin owningPlugin, int defaultValue, boolean set) {
        return (int) retrieveValueSafe(metadatable, key, owningPlugin, defaultValue, set);
    }

    public static long getLong(Metadatable metadatable, String key, Plugin owningPlugin, long defaultValue, boolean set) {
        return (long) retrieveValueSafe(metadatable, key, owningPlugin, defaultValue, set);
    }

    public static short getShort(Metadatable metadatable, String key, Plugin owningPlugin, short defaultValue, boolean set) {
        return (short) retrieveValueSafe(metadatable, key, owningPlugin, defaultValue, set);
    }

    public static float getFloat(Metadatable metadatable, String key, Plugin owningPlugin, float defaultValue, boolean set) {
        return (float) retrieveValueSafe(metadatable, key, owningPlugin, defaultValue, set);
    }

    public static double getDouble(Metadatable metadatable, String key, Plugin owningPlugin, double defaultValue, boolean set) {
        return (double) retrieveValueSafe(metadatable, key, owningPlugin, defaultValue, set);
    }

    public static boolean getBoolean(Metadatable metadatable, String key, Plugin owningPlugin, boolean defaultValue, boolean set) {
        return (boolean) retrieveValueSafe(metadatable, key, owningPlugin, defaultValue, set);
    }

    public static String getString(Metadatable metadatable, String key, Plugin owningPlugin, String defaultValue, boolean set) {
        return String.valueOf(retrieveValueSafe(metadatable, key, owningPlugin, defaultValue, set));
    }

    public static char getCharacter(Metadatable metadatable, String key, Plugin owningPlugin, char defaultValue, boolean set) {
        return (char) retrieveValueSafe(metadatable, key, owningPlugin, defaultValue, set);
    }

    public static <T> T getValue(Metadatable metadatable, String key, Plugin owningPlugin, T defaultValue, Class<T> type, boolean set) {
        Object value = retrieveValueSafe(metadatable, key, owningPlugin, defaultValue, set);
        if(type.isInstance(value)) { return type.cast(value); } else { return defaultValue; }
    }

    public static <T> List<T> getList(Metadatable metadatable, String key, Plugin owningPlugin, List<T> defaultList, Class<T> type, boolean set) {
        List<T> list;

        List<?> genericList = getGenericList(metadatable, key, owningPlugin, defaultList, set);
        if(genericList.equals(defaultList)) { return defaultList; }

        list = CollectionsUtil.genericListToType(genericList, type);

        return list;
    }

    public static <K, V> Map<K, V> getMap(Metadatable metadatable, String key, Plugin owningPlugin, Map<K, V> defaultMap, Class<K> keyType, Class<V> valueType, boolean set) {
        Map<K, V> map;

        Map<?, ?> genericMap = getGenericMap(metadatable, key, owningPlugin, defaultMap, set);
        if(genericMap.equals(defaultMap)) { return defaultMap; }

        map = CollectionsUtil.genericMapToType(genericMap, keyType, valueType);

        return map;
    }

    public static List<?> getGenericList(Metadatable metadatable, String key, Plugin owningPlugin, List<?> defaultList, boolean set) {
        List<?> list;

        Object object = retrieveValueSafe(metadatable, key, owningPlugin, defaultList, set);
        if(object.equals(defaultList)) { return defaultList; }

        list = (List<?>) object;

        return list;
    }

    public static Map<?, ?> getGenericMap(Metadatable metadatable, String key, Plugin owningPlugin, Map<?, ?> defaultMap, boolean set) {
        Map<?, ?> map;

        Object object = retrieveValueSafe(metadatable, key, owningPlugin, defaultMap, set);
        if(object.equals(defaultMap)) { return defaultMap; }

        map = (Map<?, ?>) object;

        return map;
    }

    public static List<?> removeFromList(Metadatable metadatable, String key, Plugin owningPlugin, int index) {
        List<?> list = getGenericList(metadatable, key, owningPlugin, null, false);
        if(list == null) { return null; }

        list.remove(index);

        return list;
    }

    public static List<?> removeFromList(Metadatable metadatable, String key, Plugin owningPlugin, Object element) {
        List<?> list = getGenericList(metadatable, key, owningPlugin, null, false);
        if(list == null) { return null; }

        list.remove(element);

        return list;
    }

    public static List<?> removeFromListAndDeleteIfEmpty(Metadatable metadatable, String key, Plugin owningPlugin, int index) {
        List<?> list = removeFromList(metadatable, key, owningPlugin, index);
        if(list == null) { return null; }

        if(list.isEmpty()) {
            metadatable.removeMetadata(key, owningPlugin);
            return null;
        }

        return list;
    }

    public static List<?> removeFromListAndDeleteIfEmpty(Metadatable metadatable, String key, Plugin owningPlugin, Object element) {
        List<?> list = removeFromList(metadatable, key, owningPlugin, element);
        if(list == null) { return null; }

        if(list.isEmpty()) {
            metadatable.removeMetadata(key, owningPlugin);
            return null;
        }

        return list;
    }

}

