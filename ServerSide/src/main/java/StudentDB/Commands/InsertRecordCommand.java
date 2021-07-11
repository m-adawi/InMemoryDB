package StudentDB.Commands;

import DB.Record;

public class InsertRecordCommand implements Command {
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
}
