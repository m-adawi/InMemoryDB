package StudentDB.Commands;

import StudentDB.Database;

public interface Command {
    Database database = Database.getDatabase();
    void execute();
}

