package Server;

import org.mindrot.jbcrypt.BCrypt;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Authenticator {
    private final Map<String, String> map = new ConcurrentHashMap<>();
    private final File usersDirectory;

    public Authenticator(String usersDirectoryPath) {
        usersDirectory = new File(usersDirectoryPath);
        if(!usersDirectory.exists())
            usersDirectory.mkdir();
        loadUsers();
    }

    public void addUser(String username, String password) {
        if(map.containsKey(username))
            throw new InvalidCredentialsException("User with the username " + username + " already exists");
        String passwordHash = hashPassword(password);
        map.put(username, passwordHash);
        storeToDisk(username, passwordHash);
    }

    private synchronized void storeToDisk(String username, String passwordHash) {
        File userFile = new File(usersDirectory, username);
        try(BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(userFile))) {
            out.write(passwordHash.getBytes());
        } catch (IOException e) {
            ServerLogger.log(e);
        }
    }

    public void deleteUser(String username) {
        if(!map.containsKey(username))
            throw new InvalidCredentialsException("No such user: " + username);
        map.remove(username);
        deleteFromDisk(username);
    }

    private synchronized void deleteFromDisk(String username) {
        File userFile = new File(usersDirectory, username);
        userFile.delete();
    }

    public boolean areValidCredentials(String username, String password) {
        if(!map.containsKey(username))
            return false;
        return matchesHash(password, map.get(username));
    }

    protected void loadUsers() {
        File[] userFiles = usersDirectory.listFiles();
        if(userFiles == null)
            return;
        for (File userFile : userFiles) {
            try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(userFile))) {
                String passwordHash = new String(in.readAllBytes());
                map.put(userFile.getName(), passwordHash);
            } catch (IOException e) {
                ServerLogger.log(e);
            }
        }
    }

    protected String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private boolean matchesHash(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

    public Set<String> getAllUsernames() {
        return map.keySet();
    }
}
