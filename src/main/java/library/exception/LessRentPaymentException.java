package library.exception;

public class LessRentPaymentException extends RuntimeException {
    public LessRentPaymentException(String message) {
        super(message);
    }
}
