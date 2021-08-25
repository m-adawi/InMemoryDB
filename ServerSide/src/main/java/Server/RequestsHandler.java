package Server;

import Common.Connection;
import DB.Commands.Command;
import DB.CommandGenerators.CommandsGenerator;
import DB.CommandGenerators.UnsupportedSQLStatementException;
import DB.Database;
import DB.InvalidDatabaseOperationException;
import org.gibello.zql.ParseException;
import org.gibello.zql.TokenMgrError;

import java.io.IOException;

public class RequestsHandler implements Runnable {
    private final static ServerConfigurations conf = ServerConfigurations.getConfigurations();
    private final static Database database = Database.getDatabase();
    private final static CommandsGenerator commandsGenerator = CommandsGenerator.getCommandGenerator();
    private final static Authenticator authenticator = new Authenticator(conf.getUsersDirectory());

    private final Connection connection;

    public RequestsHandler(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try(connection) {
            checkCredentials();
            connection.send("Login successful");
            while (true){
                String sqlQuery = connection.receive();
                if(isExitQuery(sqlQuery)){
                    connection.send("Existed");
                    return;
                }
                serveQuery(sqlQuery);
            }
        } catch (InvalidCredentialsException ignored) {
            // end connection
        } catch (Exception | Error e) {
            ServerLogger.log(e);
        }
    }

    private void checkCredentials() throws InvalidCredentialsException, IOException {
        String username = connection.receive();
        String password = connection.receive();
        if (!areValidCredentials(username, password)) {
            InvalidCredentialsException e = new InvalidCredentialsException();
            sendExceptionMessage(e);
            throw e;
        }
    }

    private boolean areValidCredentials(String username, String password) {
        return authenticator.areValidCredentials(username, password);
    }

    private void sendExceptionMessage(Throwable e) throws IOException {
        connection.send(e.getMessage());
    }

    private boolean isExitQuery(String sqlQuery) {
        return sqlQuery.equalsIgnoreCase("exit;");
    }

    private void serveQuery(String sqlCommand) throws IOException {
        try {
            Command command = commandsGenerator.generateFromSqlQuery(sqlCommand);
            String result = database.execute(command);
            connection.send(result);
        } catch (ParseException | TokenMgrError | InvalidDatabaseOperationException | UnsupportedSQLStatementException e) {
            sendExceptionMessage(e);
        }
    }
}
