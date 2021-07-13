package DB.Commands;

import DB.Conditions.Condition;
import DB.DatabaseKey;
import DB.Record;
import DB.RecordKeysCollection;

import java.util.LinkedList;
import java.util.List;

public abstract class ConditionalCommand extends Command {
    protected final DatabaseKey recordKey;
    protected final Condition condition;

    public ConditionalCommand(DatabaseKey recordKey) {
        this.recordKey = recordKey;
        condition = null;
    }

    public ConditionalCommand(Condition condition) {
        this.condition = condition;
        recordKey = null;
    }

    public boolean isOnSingleRecord() {
        return recordKey != null;
    }

    public DatabaseKey getRecordKey() {
        return recordKey;
    }

    @Override
    public String execute() {
        if(isOnSingleRecord()) {
            executeOnRecord(recordKey);
            return "Done";
        }
        else
            return executeOnListOfRecords(getAllRecordsSatisfiedByCondition());
    }

    protected abstract void executeOnRecord(DatabaseKey recordKey);

    protected abstract String executeOnListOfRecords(List<Record> recordList);

    protected List<Record> getAllRecordsSatisfiedByCondition() {
        List<Record> satisfied = new LinkedList<>();
        RecordKeysCollection allKeys = database.getKeysCollection();
        for (DatabaseKey recordKey : allKeys) {
            Record record = database.selectRecordByKey(recordKey);
            if(condition.isSatisfiedOnRecord(record))
                satisfied.add(record);
        }
        return satisfied;
    }
}
