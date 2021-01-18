package io.github.howiefh.generator.spring;

import io.github.howiefh.generator.common.config.Config;
import io.github.howiefh.generator.common.config.Configuration;
import io.github.howiefh.generator.common.exception.ConfigInitException;
import io.github.howiefh.generator.common.util.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

import static io.github.howiefh.generator.TemplateCodeGenerator.*;
import static io.github.howiefh.generator.common.util.Messages.getString;

@SpringBootApplication
public class CodeGeneratorSpringBootApplication {

    public static void main(String[] args) throws ConfigInitException {
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
        SpringApplication.run(CodeGeneratorSpringBootApplication.class, args);
    }

}
