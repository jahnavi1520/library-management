package library.exception;

public class CopiesNotAvailableException extends RuntimeException {
    public CopiesNotAvailableException(String message) {
        super(message);
    }
}
