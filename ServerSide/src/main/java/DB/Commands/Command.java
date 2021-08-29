package DB.Commands;

import DB.Database;
import DB.Storages.DatabaseStorage;

public abstract class Command {
    protected DatabaseStorage storage;

    public void setStorage(DatabaseStorage storage) {
        this.storage = storage;
    }

    public abstract String execute();
}

