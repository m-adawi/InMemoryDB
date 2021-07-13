package Server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerLogger {
    private static final Logger logger = LogManager.getLogger("Server.ServerLogger");

    public static void log(Object logMessage){
        logger.error(logMessage.toString());
    }
}
