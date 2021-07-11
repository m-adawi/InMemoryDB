package Client;

import Communication.Connection;

import java.io.IOException;
import java.util.Scanner;

public class ClientMain {
    private static Connection connection;

    public static void main(String[] args) throws IOException {
        if(args.length != 2){
            System.out.println("Usage: java -jar client.jar <server address> <server port number>");
            System.exit(0);
        }
        String address = args[0];
        int port = Integer.parseInt(args[1]);
        Scanner in = new Scanner(System.in);
        //TODO enhance
        String response;
        do {
            connection = Connection.connectTo(address, port);
            System.out.println("Enter user name");
            String name = in.nextLine();
            connection.send(name);
            System.out.println("Enter password");
            String password = in.nextLine();
            connection.send(password);
            response = connection.receive();
            System.out.println(response);
        }while (!response.equalsIgnoreCase("login successful"));
        while (true) {
            System.out.println("Enter sql query");
            String sqlCommand = in.nextLine();
            connection.send(sqlCommand);
            String result = connection.receive();
            System.out.println(result);
            if(sqlCommand.equalsIgnoreCase("exit;"))
                break;
        }
    }


}
