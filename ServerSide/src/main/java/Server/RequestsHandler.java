package Server;

import Communication.Connection;
import DB.InvalidDatabaseOperationException;
import DB.Commands.Command;
import DB.Commands.CommandsGenerator;
import DB.Commands.UnsupportedSQLStatementException;
import DB.Database;
import org.gibello.zql.ParseException;

import java.io.IOException;

public class RequestsHandler implements Runnable {
    private final static ServerConfigurations conf = ServerConfigurations.getConfigurations();
    private final static Database database = Database.getDatabase();
    private final static CommandsGenerator commandsGenerator = CommandsGenerator.getCommandGenerator();

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
        // TODO authentication
        return username.equals("malek") && password.equals("123");
    }

    private void sendExceptionMessage(Exception e) throws IOException {
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
        } catch (ParseException | InvalidDatabaseOperationException | UnsupportedSQLStatementException e) {
            sendExceptionMessage(e);
        }
    }
}
