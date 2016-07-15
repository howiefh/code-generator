package io.github.howiefh.generator.ui.util;

import io.github.howiefh.generator.ui.model.AbstractModel;
import io.github.howiefh.generator.ui.model.WrapModel;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author fenghao, 2016/7/12.
 * @version 1.0
 * @since 1.0
 */
public class WrapList<T extends WrapModel> extends ArrayList<T>{

    private static final long serialVersionUID = 3162771895860994927L;

    private Set entries;

    public WrapList() {
        super();
        entries = new HashSet();
    }

    public static <T extends AbstractModel> WrapList<T> newWrapList(Set c, Class<T> clazz){
        WrapList<T> list = new WrapList<T>();
        list.setEntries(c);
        try {
            for (Object o : c) {
                Constructor<T> constructor = clazz.getConstructor(new Class[]{o.getClass()});
                T t = constructor.newInstance(new Object[]{o});
                list.add(t);
            }
        } catch (Exception e) {
            return null;
        }
        return list;
    }

    @Override
    public boolean add(T t) {
        entries.add(t.getEntry());
        return super.add(t);
    }

    @Override
    public T remove(int index) {
        T t = super.remove(index);
        entries.remove(t.getEntry());
        return t;
    }

    /**
     * @param entries
     */
    public void setEntries(Set entries) {
        this.entries = entries;
    }

    /**
     *
     */
    public Set getEntries() {
        return entries;
    }
}
