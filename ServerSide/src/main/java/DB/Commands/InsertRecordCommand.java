package DB.Commands;

import DB.InvalidDatabaseOperationException;
import DB.Record;

public class InsertRecordCommand extends SingleRecordCommand {
    private Record record;

    public InsertRecordCommand(Record record) {
        this.record = record;
    }

    public Record getRecord() {
        return record;
    }

    @Override
    public String execute() {
        if(storage.getKeysCollection().containsKey(record.getKey()))
            throw new InvalidDatabaseOperationException("Record with ID: " + record.getKey() + " Already exists");
        storage.write(record);
        return "Done";
    }
}
