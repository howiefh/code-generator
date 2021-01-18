package io.github.howiefh.generator.spring.controller;

import java.util.List;

/**
 * @author fenghao on 2021/1/16
 * @version 1.0
 * @since 1.0
 */
public class Paging<T> {
    private long total;
    private List<T> items;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public static <T> Paging<T> newPaging(long total, List<T> data) {
        Paging<T> paging = new Paging<T>();
        paging.setTotal(total);
        paging.setItems(data);
        return paging;
    }
}
