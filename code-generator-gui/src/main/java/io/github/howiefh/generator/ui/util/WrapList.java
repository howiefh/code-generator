package io.github.howiefh.generator.ui.util;

import io.github.howiefh.generator.ui.model.WrapModel;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

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

    public WrapList(Set c, Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        super();
        entries = c;
        for (Object o : c) {
            Constructor<T> constructor = clazz.getConstructor(new Class[]{o.getClass()});
            T t = constructor.newInstance(new Object[]{o});
            add(t);
        }
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
