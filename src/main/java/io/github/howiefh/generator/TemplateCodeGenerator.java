package io.github.howiefh.generator;

import io.github.howiefh.generator.common.config.Configuration;
import io.github.howiefh.generator.common.exception.ConfigInitException;
import io.github.howiefh.generator.common.exception.GeneratorException;
import io.github.howiefh.generator.strategy.GeneratorStrategy;
import io.github.howiefh.generator.strategy.MergeGeneratorStrategy;
import io.github.howiefh.generator.strategy.OverrideGeneratorStrategy;
import io.github.howiefh.generator.vcs.Gits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fenghao on 2016/5/17
 * @version 1.0
 * @since 1.0
 */
public class TemplateCodeGenerator {

    private static final String OVERWRITE = "-over";
    public static final Logger LOGGER = LoggerFactory.getLogger(TemplateCodeGenerator.class);

    public static void main(String[] args) {
        try {
            Configuration.init("config.json");
            GeneratorStrategy strategy;
            if (args.length > 0 && OVERWRITE.equalsIgnoreCase(args[0])) {
                strategy = new OverrideGeneratorStrategy();
            } else {
                strategy = new MergeGeneratorStrategy(Gits.DEFAULT_REPO_PATH);
            }
            strategy.generate();
        } catch (ConfigInitException e) {
            LOGGER.error("Config init error. {}", e.getMessage());
        } catch (GeneratorException e) {
            LOGGER.error("Generate error. {}", e.getMessage());
        }
    }
}
