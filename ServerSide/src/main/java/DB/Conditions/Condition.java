package DB.Conditions;

import DB.Record;

public interface Condition {
    boolean isSatisfiedOnRecord(Record record);
}
