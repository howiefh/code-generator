package io.github.howiefh.generator.spring.command;

import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.net.URI;

/**
 * @author fenghao on 2021/1/22
 * @version 1.0
 * @since 1.0
 */
@Component
public class CommandRunner implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandRunner.class);
    @Override
    public void run(String... args) throws Exception {
        String url = "http://localhost:8080";
        LOGGER.info("打开浏览器 {}", url);
        if (Desktop.isDesktopSupported()) {
            Desktop dp = Desktop.getDesktop();
            if (dp.isSupported(Desktop.Action.BROWSE)) {
                // 获取系统默认浏览器打开链接
                dp.browse(URI.create(url));
            }
        } else {
            if (SystemUtils.IS_OS_WINDOWS) {
                Runtime.getRuntime().exec(
                        "cmd /c start " + url);
            } else if (SystemUtils.IS_OS_MAC) {
                Runtime.getRuntime().exec(
                        "open " + url);
            } else if (SystemUtils.IS_OS_LINUX) {
                Runtime.getRuntime().exec(
                    "gnome-open " + url);
            }
        }
    }

}
