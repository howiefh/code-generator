package io.github.howiefh.generator.strategy;

import io.github.howiefh.generator.common.config.TypeCfg;
import io.github.howiefh.generator.common.exception.GeneratorException;
import io.github.howiefh.generator.common.util.FreemarkerUtils;
import io.github.howiefh.generator.common.util.StringUtils;

import java.io.File;
import java.util.Map;

/**
 * @author fenghao on 2016/5/23
 * @version 1.0
 * @since 1.0
 */
public class OverrideGeneratorStrategy extends AbstractGeneratorStrategy {
    public OverrideGeneratorStrategy() {
    }

    /**
     * 生成目标文件名
     *
     * @param modle
     * @param type
     * @return
     */
    @Override
    protected File generateTargetFile(Map<String, Object> model, TypeCfg type) throws GeneratorException {
        if (type == null)
            throw new NullPointerException("Generate target file error, please check config! type:" + type);
        String temp = type.getTarget()
                + File.separator
                + StringUtils.replaceEach(type.getPkg(), new String[]{"."}, new String[]{File.separator})
                + File.separator
                + type.getSuffix();
        String actual = FreemarkerUtils.generateString(model, temp, UTF8);
        return new File(actual);
    }

    @Override
    protected void beforeTemplateGenerate() throws GeneratorException {

    }

    @Override
    protected void afterTemplateGenerate() throws GeneratorException {

    }

    @Override
    protected void beforeGenerate() throws GeneratorException {

    }

    @Override
    protected void afterGenerate() throws GeneratorException {

    }
}
