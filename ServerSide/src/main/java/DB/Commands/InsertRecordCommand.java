package DB.Commands;

import DB.DatabaseKey;
import DB.Record;

public class InsertRecordCommand implements SingleRecordCommand {
    private Record record;

    public InsertRecordCommand(Record record) {
        this.record = record;
    }

    public Record getRecord() {
        return record;
    }

    @Override
    public void execute() {
        database.insertRecord(record);
    }

    @Override
    public DatabaseKey getRecordKey() {
        return record.getKey();
    }
}
