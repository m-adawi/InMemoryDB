package DB.Commands;

import DB.Conditions.Condition;
import DB.DatabaseKey;
import DB.Record;

import java.util.List;

public class DeleteCommand extends ConditionalCommand {
    public DeleteCommand(Condition condition) {
        super(condition);
    }

    @Override
    protected void executeOnRecord(Record record) {
        executeOnRecord(record.getKey());
    }

    @Override
    public void executeOnRecord(DatabaseKey recordKey) {
        database.deleteRecordByKey(recordKey);
    }
}
