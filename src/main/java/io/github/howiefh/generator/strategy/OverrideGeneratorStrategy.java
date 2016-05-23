package io.github.howiefh.generator.strategy;

import io.github.howiefh.generator.common.config.TypeCfg;
import io.github.howiefh.generator.common.exception.GeneratorException;
import io.github.howiefh.generator.common.util.StringUtils;

import java.io.File;

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
     * @param type
     * @param filename
     * @return
     */
    @Override
    protected File generateTargetFile(TypeCfg type, String filename) {
        if (type == null || filename == null)
            throw new NullPointerException("Generate target file error, please check config! type:" + type + "filename:" + filename);
        return  new File(type.getTarget()
                + File.separator
                + StringUtils.replaceEach(type.getPkg(), new String[]{"."}, new String[]{File.separator})
                + File.separator
                + filename
                + type.getSuffix());
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
