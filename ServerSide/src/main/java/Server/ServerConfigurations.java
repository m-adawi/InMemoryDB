package Server;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class ServerConfigurations {
    private static final ServerConfigurations configurations = new ServerConfigurations();
    private final String CONFIG_FILE = "config.properties";
    private final Properties properties;
    private Properties defaultProps;

    private enum ServerProperty {
        cache_size("256"),
        server_port("7979"),
        users_directory("Users"),
        records_directory("Records");

        final String defaultValue;
        ServerProperty(String defaultValue) {
            this.defaultValue = defaultValue;
        }
    }

    public static ServerConfigurations getConfigurations() {
        return configurations;
    }

    private ServerConfigurations() {
        properties = new Properties();
        try (FileReader reader = new FileReader(CONFIG_FILE)){
            properties.load(reader);
        } catch (IOException e) {
            System.out.println(CONFIG_FILE + " not found. Loaded default configurations");
        }
        checkProperties();
    }

    private void checkProperties() {
        Set<Object> propertiesSet = properties.keySet();
        for(Object key : propertiesSet) {
            try {
                ServerProperty.valueOf(key.toString());
            } catch (IllegalArgumentException e) {
                System.err.println("Warning: Invalid property name: \"" + key +"\" in the configuration file " + CONFIG_FILE);
            }
        }
    }

    public int getCacheSize(){
        String strCacheSize = getPropertyStrValue(ServerProperty.cache_size);
        return Integer.parseInt(strCacheSize);
    }

    public int getServerPort(){
        String strServerPort = getPropertyStrValue(ServerProperty.server_port);
        return Integer.parseInt(strServerPort);
    }

    public String getUsersDirectory() {
        return getPropertyStrValue(ServerProperty.users_directory);
    }

    public String getRecordsDirectory() {
        return getPropertyStrValue(ServerProperty.records_directory);
    }

    private String getPropertyStrValue(ServerProperty property) {
        String propertyStrValue = properties.getProperty(property.name());
        if(propertyStrValue == null)
            return property.defaultValue;
        else
            return propertyStrValue;
    }
}
