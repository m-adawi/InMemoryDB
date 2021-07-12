package DB.Commands;

import DB.DatabaseKey;

public class DeleteSingleRecordCommand implements SingleRecordCommand {
    DatabaseKey recordKey;

    public DeleteSingleRecordCommand(DatabaseKey recordKey) {
        this.recordKey = recordKey;
    }

    @Override
    public void execute() {
        database.deleteRecordByKey(recordKey);
    }

    @Override
    public DatabaseKey getRecordKey() {
        return recordKey;
    }
}
