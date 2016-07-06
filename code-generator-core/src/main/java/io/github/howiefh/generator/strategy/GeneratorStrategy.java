package io.github.howiefh.generator.strategy;

import io.github.howiefh.generator.common.exception.GeneratorException;

/**
 * @author fenghao on 2016/5/23
 * @version 1.0
 * @since 1.0
 */
public interface GeneratorStrategy {
    void generate() throws GeneratorException;
}
