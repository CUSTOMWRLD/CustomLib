package com.customwrld.customlib.util;

import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class MetadataUtil {

    /***
     * You might want to use {@link MetadataUtil#getValue(Metadatable, String, Plugin, Object, Class, boolean)}
     * or other helper functions.
     *
     * Gets a value under the given metadata key with some additional safe-checks and features.
     * @param metadatable {@link Metadatable} to get the value from
     * @param key Metadata key to look for
     * @param owningPlugin {@link Plugin} that owns the metadata
     * @param defaultValue The default value
     * @param set Should the default value be put in that metadata if none was found?
     * @return Retrieved long, default value otherwise
     */
    public static Object retrieveValueSafe(Metadatable metadatable, String key, @NotNull Plugin owningPlugin, @Nullable Object defaultValue, boolean set) {
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

    /***
     * Gets an byte under the given metadata key, or returns the default value.
     * @param metadatable {@link Metadatable} to get the byte from
     * @param key Metadata key to look for
     * @param owningPlugin {@link Plugin} that owns the metadata
     * @param defaultValue The default value
     * @param set Should the default value be put in that metadata if none was found?
     * @return Retrieved byte, default value otherwise
     */
    public static byte getByte(Metadatable metadatable,
                               String key,
                               @NotNull Plugin owningPlugin,
                               byte defaultValue,
                               boolean set) {

        return (byte) retrieveValueSafe(metadatable, key, owningPlugin, defaultValue, set);
    }

    /***
     * Gets an integer under the given metadata key, or returns the default value.
     * @param metadatable {@link Metadatable} to get the integer from
     * @param key Metadata key to look for
     * @param owningPlugin {@link Plugin} that owns the metadata
     * @param defaultValue The default value
     * @param set Should the default value be put in that metadata if none was found?
     * @return Retrieved integer, default value otherwise
     */
    public static int getInteger(Metadatable metadatable,
                                 String key,
                                 @NotNull Plugin owningPlugin,
                                 int defaultValue,
                                 boolean set) {

        return (int) retrieveValueSafe(metadatable, key, owningPlugin, defaultValue, set);
    }

    /***
     * Gets a long under the given metadata key, or returns the default value.
     * @param metadatable {@link Metadatable} to get the long from
     * @param key Metadata key to look for
     * @param owningPlugin {@link Plugin} that owns the metadata
     * @param defaultValue The default value
     * @param set Should the default value be put in that metadata if none was found?
     * @return Retrieved long, default value otherwise
     */
    public static long getLong(Metadatable metadatable,
                               String key,
                               @NotNull Plugin owningPlugin,
                               long defaultValue,
                               boolean set) {

        return (long) retrieveValueSafe(metadatable, key, owningPlugin, defaultValue, set);
    }

    /***
     * Gets a short under the given metadata key, or returns the default value.
     * @param metadatable {@link Metadatable} to get the short from
     * @param key Metadata key to look for
     * @param owningPlugin {@link Plugin} that owns the metadata
     * @param defaultValue The default value
     * @param set Should the default value be put in that metadata if none was found?
     * @return Retrieved short, default value otherwise
     */
    public static short getShort(Metadatable metadatable,
                                 String key,
                                 @NotNull Plugin owningPlugin,
                                 short defaultValue,
                                 boolean set) {

        return (short) retrieveValueSafe(metadatable, key, owningPlugin, defaultValue, set);
    }

    /***
     * Gets a float under the given metadata key, or returns the default value.
     * @param metadatable {@link Metadatable} to get the float from
     * @param key Metadata key to look for
     * @param owningPlugin {@link Plugin} that owns the metadata
     * @param defaultValue The default value
     * @param set Should the default value be put in that metadata if none was found?
     * @return Retrieved float, default value otherwise
     */
    public static float getFloat(Metadatable metadatable,
                                 String key,
                                 @NotNull Plugin owningPlugin,
                                 float defaultValue,
                                 boolean set) {

        return (float) retrieveValueSafe(metadatable, key, owningPlugin, defaultValue, set);
    }

    /***
     * Gets a double under the given metadata key, or returns the default value.
     * @param metadatable {@link Metadatable} to get the double from
     * @param key Metadata key to look for
     * @param owningPlugin {@link Plugin} that owns the metadata
     * @param defaultValue The default value
     * @param set Should the default value be put in that metadata if none was found?
     * @return Retrieved double, default value otherwise
     */
    public static double getDouble(Metadatable metadatable,
                                   String key,
                                   @NotNull Plugin owningPlugin,
                                   double defaultValue,
                                   boolean set) {

        return (double) retrieveValueSafe(metadatable, key, owningPlugin, defaultValue, set);
    }

    /***
     * Gets an boolean under the given metadata key, or returns the default value.
     * @param metadatable {@link Metadatable} to get the boolean from
     * @param key Metadata key to look for
     * @param owningPlugin {@link Plugin} that owns the metadata
     * @param defaultValue The default value
     * @param set Should the default value be put in that metadata if none was found?
     * @return Retrieved integer, default value otherwise
     */
    public static boolean getBoolean(Metadatable metadatable,
                                     String key,
                                     @NotNull Plugin owningPlugin,
                                     boolean defaultValue,
                                     boolean set) {

        return (boolean) retrieveValueSafe(metadatable, key, owningPlugin, defaultValue, set);
    }

    /***
     * Gets a {@link String} under the given metadata key, or returns the default value.
     * @param metadatable {@link Metadatable} to get the {@link String} from
     * @param key Metadata key to look for
     * @param owningPlugin {@link Plugin} that owns the metadata
     * @param defaultValue The default value
     * @param set Should the default value be put in that metadata if none was found?
     * @return Retrieved {@link String}, default value otherwise
     */
    public static String getString(Metadatable metadatable,
                                   String key,
                                   @NotNull Plugin owningPlugin,
                                   @Nullable String defaultValue,
                                   boolean set) {

        return String.valueOf(retrieveValueSafe(metadatable, key, owningPlugin, defaultValue, set));
    }

    /***
     * Gets a character under the given metadata key, or returns the default value.
     * @param metadatable {@link Metadatable} to get the character from
     * @param key Metadata key to look for
     * @param owningPlugin {@link Plugin} that owns the metadata
     * @param defaultValue The default value
     * @param set Should the default value be put in that metadata if none was found?
     * @return Retrieved character, default value otherwise
     */
    public static char getCharacter(Metadatable metadatable,
                                    String key,
                                    @NotNull Plugin owningPlugin,
                                    char defaultValue,
                                    boolean set) {

        return (char) retrieveValueSafe(metadatable, key, owningPlugin, defaultValue, set);
    }

    /***
     * Gets a value under the given metadata key, or returns the default value.
     * @param metadatable {@link Metadatable} to get the value from
     * @param key Metadata key to look for
     * @param owningPlugin {@link Plugin} that owns the metadata
     * @param defaultValue The default value
     * @param type Class that represents the type of value
     * @param set Should the default value be put in that metadata if none was found?
     * @param <T> Type of value
     * @return Retrieved value, default value otherwise
     */
    public static <T> T getValue(Metadatable metadatable,
                                 String key,
                                 @NotNull Plugin owningPlugin,
                                 @Nullable T defaultValue,
                                 @NotNull Class<T> type,
                                 boolean set) {

        Object value = retrieveValueSafe(metadatable, key, owningPlugin, defaultValue, set);
        if(type.isInstance(value)) { return type.cast(value); } else { return defaultValue; }
    }

    /***
     * Gets a list under the given metadata key, or returns the default list.
     * @param metadatable {@link Metadatable} to get the list from
     * @param key Metadata key to look for
     * @param owningPlugin {@link Plugin} that owns the metadata
     * @param defaultList The default list
     * @param type Class that represents type of values inside the list
     * @param set Should the default list be put in that metadata if none was found?
     * @param <T> Type of values inside the list
     * @return Retrieved {@link List}, default list otherwise
     */
    public static <T> List<T> getList(Metadatable metadatable, String key, @NotNull Plugin owningPlugin, @Nullable List<T> defaultList, Class<T> type, boolean set) {

        List<T> list;

        List<?> genericList = getGenericList(metadatable, key, owningPlugin, defaultList, set);
        if(genericList.equals(defaultList)) { return defaultList; }

        list = CollectionsUtil.genericListToType(genericList, type);

        return list;

    }

    /***
     * Gets a map under the given metadata key, or returns the default map.
     * @param metadatable {@link Metadatable} to get the list from
     * @param key Metadata key to look for
     * @param owningPlugin {@link Plugin} that owns the metadata
     * @param defaultMap The default map
     * @param keyType Class that represents type of keys inside the map
     * @param valueType Class that represents type of values inside the map
     * @param set Should the default map be put in that metadata if none was found?
     * @param <K> Type of keys inside the list
     * @param <V> Type of values inside the list
     * @return Retrieved {@link Map}, default map otherwise
     */
    public static <K, V> Map<K, V> getMap(Metadatable metadatable, String key, @NotNull Plugin owningPlugin, @Nullable Map<K, V> defaultMap, Class<K> keyType, Class<V> valueType, boolean set) {

        Map<K, V> map;

        Map<?, ?> genericMap = getGenericMap(metadatable, key, owningPlugin, defaultMap, set);
        if(genericMap.equals(defaultMap)) { return defaultMap; }

        map = CollectionsUtil.genericMapToType(genericMap, keyType, valueType);

        return map;

    }

    /***
     * You probably don't want to use it! Use {@link MetadataUtil#getList(Metadatable, String, Plugin, List, Class, boolean)} instead.<br>
     * This was made in cases where you don't care about the type and want to decrease time caused by casting and type checking.<br>
     *<br>
     * Gets a list of any type under the given metadata key, or returns the default list.
     * @param metadatable {@link Metadatable} to get the list from
     * @param key Metadata key to look for
     * @param owningPlugin {@link Plugin} that owns the metadata
     * @param defaultList The default list
     * @param set Should the default list be put in that metadata if none was found?
     * @return Retrieved {@link List}, default list otherwise
     */
    public static List<?> getGenericList(Metadatable metadatable, String key, @NotNull Plugin owningPlugin, @Nullable List<?> defaultList, boolean set) {

        List<?> list;

        Object object = retrieveValueSafe(metadatable, key, owningPlugin, defaultList, set);
        if(object.equals(defaultList)) { return defaultList; }

        list = (List<?>) object;

        return list;

    }

    /***
     * You probably don't want to use it! Use {@link MetadataUtil#getMap(Metadatable, String, Plugin, Map, Class, Class, boolean)} instead.<br>
     * This was made in cases where you don't care about the type and want to decrease time caused by casting and type checking.<br>
     *<br>
     * Gets a map of any type under the given metadata key, or returns the default map.
     * @param metadatable {@link Metadatable} to get the map from
     * @param key Metadata key to look for
     * @param owningPlugin {@link Plugin} that owns the metadata
     * @param defaultMap The default map
     * @param set Should the default map be put in that metadata if none was found?
     * @return Retrieved {@link Map}, default map otherwise
     */
    public static Map<?, ?> getGenericMap(Metadatable metadatable, String key, @NotNull Plugin owningPlugin, @Nullable Map<?, ?> defaultMap, boolean set) {

        Map<?, ?> map;

        Object object = retrieveValueSafe(metadatable, key, owningPlugin, defaultMap, set);
        if(object.equals(defaultMap)) { return defaultMap; }

        map = (Map<?, ?>) object;

        return map;

    }

    /***
     * Removes an element from a list under the given metadata key.
     * @param metadatable {@link Metadatable} to get the list from
     * @param key Metadata key to look for
     * @param owningPlugin {@link Plugin} that owns the metadata
     * @param index Index to remove
     * @return Retrieved {@link List}, or null if not found
     */
    public static @Nullable List<?> removeFromList(Metadatable metadatable, String key, @NotNull Plugin owningPlugin, int index) {

        List<?> list = getGenericList(metadatable, key, owningPlugin, null, false);
        if(list == null) { return null; }

        list.remove(index);

        return list;

    }

    /***
     * Removes an element from a list under the given metadata key.
     * @param metadatable {@link Metadatable} to get the list from
     * @param key Metadata key to look for
     * @param owningPlugin {@link Plugin} that owns the metadata
     * @param element Element to remove
     * @return Retrieved {@link List}, or null if not found
     */
    public static @Nullable List<?> removeFromList(Metadatable metadatable, String key, @NotNull Plugin owningPlugin, Object element) {

        List<?> list = getGenericList(metadatable, key, owningPlugin, null, false);
        if(list == null) { return null; }

        list.remove(element);

        return list;

    }

    /***
     * Removes an element from a list under the given metadata key and deletes the metadata if that list is empty.
     * @param metadatable {@link Metadatable} to get the list from
     * @param key Metadata key to look for
     * @param owningPlugin {@link Plugin} that owns the metadata
     * @param index Index to remove
     * @return Retrieved {@link List}, or null if removed or not found
     */
    public static @Nullable List<?> removeFromListAndDeleteIfEmpty(Metadatable metadatable, String key, @NotNull Plugin owningPlugin, int index) {

        List<?> list = removeFromList(metadatable, key, owningPlugin, index);
        if(list == null) { return null; }

        if(list.isEmpty()) {
            metadatable.removeMetadata(key, owningPlugin);
            return null;
        }

        return list;

    }

    /***
     * Removes an element from a list under the given metadata key and deletes the metadata if that list is empty.
     * @param metadatable {@link Metadatable} to get the list from
     * @param key Metadata key to look for
     * @param owningPlugin {@link Plugin} that owns the metadata
     * @param element Element to remove
     * @return Retrieved {@link List}, or null if removed or not found
     */
    public static @Nullable List<?> removeFromListAndDeleteIfEmpty(Metadatable metadatable, String key, @NotNull Plugin owningPlugin, Object element) {

        List<?> list = removeFromList(metadatable, key, owningPlugin, element);
        if(list == null) { return null; }

        if(list.isEmpty()) {
            metadatable.removeMetadata(key, owningPlugin);
            return null;
        }

        return list;

    }

}

