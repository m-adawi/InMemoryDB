package DB.Conditions;

import DB.Record;

public class InvertedCondition implements Condition {
    private Condition condition;

    public InvertedCondition(Condition condition) {
        this.condition = condition;
    }

    @Override
    public boolean isSatisfiedOnRecord(Record record) {
        return !condition.isSatisfiedOnRecord(record);
    }
}
