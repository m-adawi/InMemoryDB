package DB.Commands;

import DB.Database;

public interface Command {
    Database database = Database.getDatabase();
    void execute();
}

