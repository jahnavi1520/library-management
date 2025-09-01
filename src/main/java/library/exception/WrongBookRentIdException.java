package library.exception;

public class WrongBookRentIdException extends RuntimeException {
    public WrongBookRentIdException(String message) {
        super(message);
    }
}
