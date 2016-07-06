package io.github.howiefh.generator.common.exception;

/**
 * @author fenghao on 2016/5/23
 * @version 1.0
 * @since 1.0
 */
public class GitException extends GeneratorException {

    private static final long serialVersionUID = -3786712304653862718L;

    public GitException() {
    }

    public GitException(String message) {
        super(message);
    }

    public GitException(String message, Throwable cause) {
        super(message, cause);
    }

    public GitException(Throwable cause) {
        super(cause);
    }

    public GitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
