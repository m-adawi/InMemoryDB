package DB.Conditions;

import DB.DatabaseKey;
import DB.Record;

public class IDEqualCondition implements Condition {
    private final DatabaseKey key;

    public IDEqualCondition(DatabaseKey key) {
        this.key = key;
    }

    @Override
    public boolean isSatisfiedOnRecord(Record record) {
        return record.getKey().equals(key);
    }

    public DatabaseKey getKey() {
        return key;
    }
}
