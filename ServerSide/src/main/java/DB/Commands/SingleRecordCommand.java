package DB.Commands;

import DB.DatabaseKey;

public interface SingleRecordCommand extends Command {
    DatabaseKey getRecordKey();
}
