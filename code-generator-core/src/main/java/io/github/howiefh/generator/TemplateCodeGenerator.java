package io.github.howiefh.generator;

import io.github.howiefh.generator.common.config.Config;
import io.github.howiefh.generator.common.config.Configuration;
import io.github.howiefh.generator.common.exception.ConfigInitException;
import io.github.howiefh.generator.common.exception.GeneratorException;
import io.github.howiefh.generator.common.util.StringUtils;
import io.github.howiefh.generator.strategy.GeneratorStrategy;
import io.github.howiefh.generator.strategy.MergeGeneratorStrategy;
import io.github.howiefh.generator.strategy.OverrideGeneratorStrategy;
import io.github.howiefh.generator.vcs.Gits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static io.github.howiefh.generator.common.util.Messages.getString;

/**
 * @author fenghao on 2016/5/17
 * @version 1.0
 * @since 1.0
 */
public class TemplateCodeGenerator {

    private static final String OVERWRITE = "-overwrite";
    public static final String CONFIG = "-config";
    public static final String HELP_1 = "-?";
    public static final String HELP_2 = "-h";
    public static final Logger LOGGER = LoggerFactory.getLogger(TemplateCodeGenerator.class);

    public static void main(String[] args) {
        try {
            Map<String, String> arguments = parseCommandLine(args);

            if (arguments.containsKey(HELP_1)) {
                System.out.println(getString("usage"));
                System.exit(0);
                return;
            }

            if (StringUtils.isNoneBlank(arguments.get(CONFIG))) {
                Configuration.init(arguments.get(CONFIG));
            } else {
                Configuration.init(Config.DEFAULT_CONFIG);
            }

            generate(arguments.containsKey(OVERWRITE));
        } catch (IllegalArgumentException e) {
            LOGGER.error("Argument error. {}", e.getMessage());
        } catch (ConfigInitException e) {
            LOGGER.error("Config init error. {}", e.getMessage());
        } catch (GeneratorException e) {
            LOGGER.error("Generate error. {}", e.getMessage());
        }
    }

    public static void generate(boolean override) {
        try {
            GeneratorStrategy strategy;
            if (override) {
                strategy = new OverrideGeneratorStrategy();
            } else {
                strategy = new MergeGeneratorStrategy(Gits.DEFAULT_REPO_PATH);
            }
            strategy.generate();
        } catch (IllegalArgumentException e) {
            LOGGER.error("Argument error. {}", e.getMessage());
        } catch (ConfigInitException e) {
            LOGGER.error("Config init error. {}", e.getMessage());
        } catch (GeneratorException e) {
            LOGGER.error("Generate error. {}", e.getMessage());
        }
    }

    public static Map<String, String> parseCommandLine(String[] args) {
        Map<String, String> arguments = new HashMap<String, String>();

        for (int i = 0; i < args.length; i++) {
            if (CONFIG.equalsIgnoreCase(args[i])) {
                if ((i + 1) < args.length) {
                    arguments.put(CONFIG, args[i + 1]);
                } else {
                    throw new IllegalArgumentException("Value missing after " + CONFIG);
                }
                i++;
            } else if (OVERWRITE.equalsIgnoreCase(args[i])) {
                arguments.put(OVERWRITE, "Y");
            } else if (HELP_1.equalsIgnoreCase(args[i])) {
                arguments.put(HELP_1, "Y");
            } else if (HELP_2.equalsIgnoreCase(args[i])) {
                arguments.put(HELP_1, "Y");
            }
        }

        return arguments;
    }
}
