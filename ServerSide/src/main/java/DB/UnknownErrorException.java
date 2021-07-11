package DB;

public class UnknownErrorException extends RuntimeException {
    public UnknownErrorException() {
        super("Unknown error occurred");
    }

    public UnknownErrorException(String message) {
        super(message);
    }
}
