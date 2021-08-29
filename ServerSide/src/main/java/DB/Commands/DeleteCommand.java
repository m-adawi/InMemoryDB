package DB.Commands;

import DB.Conditions.Condition;
import DB.Database;
import DB.DatabaseKey;
import DB.Record;

import java.util.ArrayList;
import java.util.List;

public class DeleteCommand extends ConditionalCommand {
    public DeleteCommand(Condition condition) {
        super(condition);
    }

    @Override
    protected String executeOnListOfRecords(List<Record> recordList) {
        List<DatabaseKey> keyList = new ArrayList<>();
        for(Record record : recordList) {
            keyList.add(record.getKey());
        }
        storage.delete(keyList);
        return "Deleted " + keyList.size() + " records";
    }
}
