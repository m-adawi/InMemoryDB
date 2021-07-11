package Server;

import StudentDB.Database;

public class ServerConfigurations {
    private static final ServerConfigurations configurations = new ServerConfigurations();
    private int cacheSize;
    private int serverPort;
    // private Database defaultDatabase = Database.getDatabase();

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
/*
    public Database getDefaultDatabase() {
        return defaultDatabase;
    }*/
}
