package Server;

import Communication.Connection;
import DB.InvalidDatabaseOperationException;
import StudentDB.Commands.Command;
import StudentDB.Commands.CommandsGenerator;
import StudentDB.Database;
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
        try {
            checkCredentials();
            connection.send("Login successful");
            while (true){
                String sqlCommand = connection.receive();
                if(sqlCommand == null)
                    return;
                else if(sqlCommand.equalsIgnoreCase("exit;")) {
                    connection.send("Exited");
                    return;
                }
                String result = serveQuery(sqlCommand);
                connection.send(result);
            }
        } catch (InvalidCredentialsException | InvalidDatabaseOperationException e) {
            sendExceptionMessage(e);
        } catch (IOException e) {
            ServerLogger.log(e);
        }
        finally {
            try {
                connection.close();
            } catch (IOException e) {
                ServerLogger.log(e);
            }
        }
    }

    private void checkCredentials() throws InvalidCredentialsException {
        try {
            String username = connection.receive();
            String password = connection.receive();
            if (!areValidCredentials(username, password))
                throw new InvalidCredentialsException();
        } catch (IOException e) {
            ServerLogger.log(e);
        }
    }

    private boolean areValidCredentials(String username, String password) {
        // TODO
        return username.equals("malek") && password.equals("123");
    }

    private void sendExceptionMessage(Exception e){
        try {
            connection.send(e.getMessage());
        } catch (IOException ioException) {
            ServerLogger.log(e);
        }
    }

    private String serveQuery(String sqlCommand) {
        Command command = null;
        try {
            command = commandsGenerator.generateFromSqlQuery(sqlCommand);
        } catch (ParseException e) {
            return "Syntax error";
        }
        return database.execute(command);
    }
}
