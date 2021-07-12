package DB.Commands;

public class UnsupportedSQLStatementException extends RuntimeException {
    public UnsupportedSQLStatementException() {
        super("Unsupported SQL statement");
    }
}
