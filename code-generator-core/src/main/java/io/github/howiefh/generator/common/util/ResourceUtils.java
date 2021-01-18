package io.github.howiefh.generator.common.util;

import com.google.common.io.Resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

/**
 * @author fenghao on 2016/7/4.
 * @version 1.0
 * @since 1.0
 */
public class ResourceUtils {
    public static File getResourceFileOrCopyFileIfNotExists(String path) throws URISyntaxException, IOException {
        File file = new File(path);
        if (!file.exists()) {
            try (OutputStream os = new FileOutputStream(file)) {
                Resources.copy(Resources.getResource(path), os);
            }
        }
        return file;
    }

    public static File getResourceFile(String path) throws URISyntaxException {
        File file = new File(path);
        if (!file.exists()) {
            file = new File(Resources.getResource(path).toURI());
        }
        return file;
    }
}
