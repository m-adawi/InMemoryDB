package Communication;

import java.io.*;
import java.net.Socket;

public class Connection implements AutoCloseable {
    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;

    public static Connection connectTo(String address, int port) throws IOException {
        return new Connection(address, port);
    }

    public static Connection getConnectionFromSocket(Socket socket) throws IOException {
        return new Connection(socket);
    }

    private Connection(String address, int port) throws IOException {
        this(new Socket(address, port));
    }

    private Connection(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void send(String string) throws IOException {
        out.write(string);
        out.newLine();
        out.flush();
    }

    public String receive() throws IOException {
        return in.readLine();
    }

    @Override
    public void close() throws IOException {
        if(in != null)
            in.close();
        if(out != null)
            out.close();
        if(socket != null)
            socket.close();
    }
}
