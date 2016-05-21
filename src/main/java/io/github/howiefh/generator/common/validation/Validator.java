package io.github.howiefh.generator.common.validation;

/**
 * @author fenghao on 2016/5/21
 * @version 1.0
 * @since 1.0
 */
public interface Validator<T> {
    boolean validate(T obj);
}
