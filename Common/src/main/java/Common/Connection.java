package Common;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Connection implements Closeable {
    private final static int INT_SIZE = 4;
    //TODO use SSLSocket
    private final Socket socket;
    private final BufferedInputStream in;
    private final BufferedOutputStream out;

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
        in = new BufferedInputStream(socket.getInputStream());
        out = new BufferedOutputStream(socket.getOutputStream());
    }

    public void send(String message) throws IOException {
        byte[] messageBytes = message.getBytes();
        sendMessageSize(messageBytes.length);
        out.write(messageBytes);
        out.flush();
    }

    private void sendMessageSize(int messageSize) throws IOException {
        // convert int to byte array
        byte[] sizeByteArray = ByteBuffer.allocate(INT_SIZE).putInt(messageSize).array();
        out.write(sizeByteArray);
    }

    public String receive() throws IOException {
        int messageSize = receiveMessageSize();
        byte[] messageBytes = in.readNBytes(messageSize);
        return new String(messageBytes);
    }

    private int receiveMessageSize() throws IOException {
        // convert byte array to int
        byte[] sizeByteArray = in.readNBytes(INT_SIZE);
        if(sizeByteArray.length == 0)
            throw new IOException("Connection with host " + socket.getRemoteSocketAddress() + " closed suddenly");
        return ByteBuffer.wrap(sizeByteArray).getInt();
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
