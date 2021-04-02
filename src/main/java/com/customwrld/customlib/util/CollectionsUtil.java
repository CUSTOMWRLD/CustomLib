package com.customwrld.customlib.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionsUtil {
    public static <T> List<T> genericListToType(List<?> list, Class<T> type) {
        ArrayList<T> convertedList = new ArrayList<>();
        for(Object element : list) {
            convertedList.add(type.cast(element));
        }
        return convertedList;
    }

    public static <K, V> Map<K, V> genericMapToType(Map<?, ?> map, Class<K> keyType, Class<V> valueType) {
        HashMap<K, V> convertedMap = new HashMap<>();
        for(Map.Entry<?, ?> ent : map.entrySet()) {
            convertedMap.put(keyType.cast(ent.getKey()), valueType.cast(ent.getValue()));
        }
        return convertedMap;
    }
}
