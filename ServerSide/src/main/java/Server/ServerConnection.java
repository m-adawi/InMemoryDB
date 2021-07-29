package Server;

import Common.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection implements AutoCloseable {
    private final ServerSocket serverSocket;

    public ServerConnection(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public Connection acceptNewConnection() throws IOException {
        Socket socket = serverSocket.accept();
        return Connection.getConnectionFromSocket(socket);
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
    }
}
