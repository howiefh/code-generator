package io.github.howiefh.generator.ui.util;

import io.github.howiefh.generator.ui.model.AbstractModel;
import io.github.howiefh.generator.ui.model.WrapModel;

import java.lang.reflect.Constructor;
import java.util.*;

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
        entries = new LinkedHashSet();
    }

    public WrapList(List <T> list) {
        this();
        addAll(list);
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

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean result = false;
        if (c != null){
            super.addAll(new LinkedHashSet<T>(c));
            if (c instanceof WrapList) {
                entries = new LinkedHashSet(((WrapList)c).getEntries());
            } else {
                for (Object o : c) {
                    if (o instanceof WrapModel) {
                        entries.add(((WrapModel) o).getEntry());
                    }
                }
            }
            result = true;
        }
        return result;
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
