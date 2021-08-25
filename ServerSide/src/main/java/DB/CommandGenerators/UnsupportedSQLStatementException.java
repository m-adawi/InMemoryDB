package DB.CommandGenerators;

public class UnsupportedSQLStatementException extends RuntimeException {
    public UnsupportedSQLStatementException() {
        super("Unsupported SQL statement");
    }
}
