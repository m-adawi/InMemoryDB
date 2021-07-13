package DB.Commands;

import DB.Conditions.Condition;
import DB.DatabaseKey;
import DB.Record;

import java.util.List;

public class DeleteCommand extends ConditionalCommand {
    public DeleteCommand(DatabaseKey recordKey) {
        super(recordKey);
    }

    public DeleteCommand(Condition condition) {
        super(condition);
    }

    @Override
    protected String executeOnListOfRecords(List<Record> recordList) {
        for(Record record: recordList){
            executeOnRecord(record.getKey());
        }
        return "Done";
    }

    @Override
    public void executeOnRecord(DatabaseKey recordKey) {
        database.deleteRecordByKey(recordKey);
    }
}
