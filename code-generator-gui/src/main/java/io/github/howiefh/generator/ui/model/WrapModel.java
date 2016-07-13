package io.github.howiefh.generator.ui.model;

/**
 * @author fenghao, 2016/7/12.
 * @version 1.0
 * @since 1.0
 */
public interface WrapModel<T> {
    T getEntry();
    void setEntry(T t);
}
