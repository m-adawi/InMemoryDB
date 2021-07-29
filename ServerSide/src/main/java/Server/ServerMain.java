package Server;


import Common.Connection;

import java.io.IOException;

public class ServerMain {
    private final static int PORT = ServerConfigurations.getConfigurations().getServerPort();

    public static void main(String[] args) throws IOException {
        while (true) {
            try (ServerConnection serverConnection = new ServerConnection(PORT)) {
                Connection connection = serverConnection.acceptNewConnection();
                Thread newTask = new Thread(new RequestsHandler(connection));
                newTask.start();
            } catch (Exception | Error e) {
                ServerLogger.log(e);
            }
        }
    }
}