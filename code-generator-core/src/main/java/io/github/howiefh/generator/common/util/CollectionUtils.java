package io.github.howiefh.generator.common.util;

import java.util.*;

/**
 * @author fenghao on 2016/5/21
 * @version 1.0
 * @since 1.0
 */
public class CollectionUtils {
    /**
     * collection不空且size()不为0，返回true，否则返回false
     *
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(final Collection collection) {
        return collection != null && collection.size() != 0;
    }

    /**
     * collection空或size()为0，返回true，否则返回false
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(final Collection collection) {
        return !isNotEmpty(collection);
    }

    /**
     * 在集合中查找元素并返回
     *
     * @param collection
     * @param ele
     * @param <T>
     * @return 如果在集合中找到元素，则返回找到的元素，否则返回null
     */
    public static <T> T search(final Collection<T> collection, final T ele) {
        if (collection == null || ele == null) {
            return null;
        }
        T result = null;
        for (T e : collection) {
            if (ele.equals(e)) {
                result = e;
                break;
            }
        }
        return result;
    }

    /**
     * 如果set为null返回空集合,否则返回它本身
     *
     * @param set
     * @param <T>
     * @return
     */
    public static <T> Set<T> emptySetIfNull(final Set<T> set) {
        return set == null ? Collections.<T>emptySet() : set;
    }

    /**
     * 如果map为null返回空Map,否则返回它本身
     *
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> emptyMapIfNull(final Map<K, V> map) {
        return map == null ? Collections.<K, V>emptyMap() : map;
    }

    /**
     * 将map转换为实体列表
     *
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> List<Map.Entry<K, V>> convertMapToEntryList(final Map<K, V> map) {
        return map == null ? null : new ArrayList<Map.Entry<K, V>>(map.entrySet());
    }

    /**
     * 将实体列表转换为map
     * @param list
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> convertEntryListToMap(final List<Map.Entry<K, V>> list) {
        if (list == null) {
            return null;
        }
        Map<K,V> map = new HashMap<K, V>();
        for (Map.Entry<K,V> entry: list) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    /**
     * List转Set
     * @param list
     * @param <E>
     * @return
     */
    public static <E> Set<E> convertListToSet(final List<E> list) {
        return list == null ? null : new LinkedHashSet<E>(list);
    }

    /**
     * Set转List
     * @param set
     * @param <E>
     * @return
     */
    public static <E> List<E> convertSetToList(final Set<E> set) {
        return set == null ? null : new ArrayList<E>(set);
    }
}
