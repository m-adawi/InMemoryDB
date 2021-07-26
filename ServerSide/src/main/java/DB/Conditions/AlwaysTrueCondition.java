package DB.Conditions;

import DB.Record;

public class AlwaysTrueCondition implements Condition {

    @Override
    public boolean isSatisfiedOnRecord(Record record) {
        return true;
    }
}
