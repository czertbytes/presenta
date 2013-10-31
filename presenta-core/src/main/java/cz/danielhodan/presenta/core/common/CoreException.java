package cz.danielhodan.presenta.core.common;

public class CoreException extends Exception {

    public CoreException(String message) {
        super(message);
    }

    public CoreException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public CoreException(Throwable throwable) {
        super(throwable);
    }
}
