package Server;

public class ServerConfigurations {
    private static final ServerConfigurations configurations = new ServerConfigurations();
    private final int cacheSize;
    private final int serverPort;
    private static final Authenticator authenticator = new Authenticator("users");

    private ServerConfigurations(){
        cacheSize = 100;
        serverPort = 7979;
    }

    public static ServerConfigurations getConfigurations() {
        return configurations;
    }

    public int getCacheSize(){
        return cacheSize;
    }

    public int getServerPort(){
        return serverPort;
    }

    public Authenticator getAuthenticator() {
        return authenticator;
    }
}
