package DB.Commands;

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
        storage.write(record);
        return "Done";
    }
}
