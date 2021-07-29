package Client;

import Common.Connection;
import Common.UserInput;

import java.io.IOException;
import java.util.Scanner;

public class ClientMain {
    private static String serverAddress;
    private static int serverPort = 7979; // Default port
    private static final UserInput in = new UserInput();
    private static Connection connection;

    public static void main(String[] args) throws IOException {
        init(args);
        login();
        while (true) {
            String sqlCommand = in.prompt();
            connection.send(sqlCommand);
            String result = connection.receive();
            System.out.println(result);
            if(sqlCommand.equalsIgnoreCase("exit;"))
                break;
        }
    }

    private static void init(String[] args) {
        if(args.length != 2){
            System.out.println("Usage: java -jar client.jar <server address> [server port number]");
            System.exit(0);
        }
        serverAddress = args[0];
        serverPort = Integer.parseInt(args[1]);
    }

    private static void login() throws IOException {
        String response;
        do {
            String username = in.prompt("Enter user name:");
            String password = in.prompt("Enter password:");
            connection = Connection.connectTo(serverAddress, serverPort);
            connection.send(username);
            connection.send(password);
            response = connection.receive();
            System.out.println(response);
        } while (!response.equalsIgnoreCase("login successful"));
    }
}
