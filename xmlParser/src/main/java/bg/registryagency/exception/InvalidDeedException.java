package bg.registryagency.exception;

public class InvalidDeedException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidDeedException() {
    }

    public InvalidDeedException(String message) {
        super(message);
    }

    public InvalidDeedException(Throwable cause) {
        super(cause);
    }

    public InvalidDeedException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDeedException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
