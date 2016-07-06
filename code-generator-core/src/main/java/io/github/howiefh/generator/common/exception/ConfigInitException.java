package io.github.howiefh.generator.common.exception;

/**
 * @author fenghao on 2016/5/21
 * @version 1.0
 * @since 1.0
 */
public class ConfigInitException extends GeneratorException {
    private static final long serialVersionUID = -5474978392498836243L;

    public ConfigInitException() {
        super();
    }

    public ConfigInitException(String message) {
        super(message);
    }

    public ConfigInitException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigInitException(Throwable cause) {
        super(cause);
    }

    protected ConfigInitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
