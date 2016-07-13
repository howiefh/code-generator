package io.github.howiefh.generator.ui.util;

import java.util.AbstractMap;
import java.util.Map;

/**
 * @author fenghao, 2016/7/14.
 * @version 1.0
 * @since 1.0
 */
public class SimpleEntry<K, V> extends AbstractMap.SimpleEntry<K, V>{

    private static final long serialVersionUID = -1948037495913992199L;

    public SimpleEntry(K key, V value) {
        super(key, value);
    }

    public SimpleEntry(Map.Entry<? extends K, ? extends V> entry) {
        super(entry);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Map.Entry))
            return false;
        Map.Entry<?,?> e = (Map.Entry<?,?>)o;
        return eq(getKey(), e.getKey());
    }

    private boolean eq(Object o1, Object o2) {
        return o1 == null ? o2 == null : o1.equals(o2);
    }

    @Override
    public int hashCode() {
        return getKey() == null ? 0 : getKey().hashCode();
    }

    @Override
    public String toString() {
        return getKey() + " : " + getValue();
    }
}
