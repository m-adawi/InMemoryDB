package DB.Commands;

import DB.Database;

public abstract class Command {
    protected final static Database database = Database.getDatabase();

    public abstract String execute();
}

