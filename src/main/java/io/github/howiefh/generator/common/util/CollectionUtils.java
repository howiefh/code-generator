package io.github.howiefh.generator.common.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author fenghao on 2016/5/21
 * @version 1.0
 * @since 1.0
 */
public class CollectionUtils {
    /**
     * collection不空且size()不为0，返回true，否则返回false
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection collection){
        return collection != null && collection.size() != 0;
    }

    /**
     * collection空或size()为0，返回true，否则返回false
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection collection){
        return !isNotEmpty(collection);
    }

    /**
     * 如果set为null返回空集合,否则返回它本身
     * @param set
     * @param <T>
     * @return
     */
    public static <T> Set<T> emptySetIfNull(Set<T> set){
        return set == null ? Collections.<T>emptySet() : set;
    }

    /**
     *
     * 如果map为null返回空Map,否则返回它本身
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K,V> Map<K,V> emptyMapIfNull(Map<K,V> map){
        return map == null ? Collections.<K,V>emptyMap() : map;
    }
}
