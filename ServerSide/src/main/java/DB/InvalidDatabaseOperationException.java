package DB;

public class InvalidDatabaseOperationException extends RuntimeException {
    public InvalidDatabaseOperationException(String message) {
        super(message);
    }
}
