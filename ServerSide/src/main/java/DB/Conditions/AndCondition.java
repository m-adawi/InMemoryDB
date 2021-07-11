package DB.Conditions;

import DB.Record;

public class AndCondition extends ComplexCondition {

    public AndCondition(Condition... operands) {
        super(operands);
    }

    @Override
    public boolean isSatisfiedOnRecord(Record record) {
        for(Condition condition : operands){
            if (!condition.isSatisfiedOnRecord(record))
                return false;
        }
        return true;
    }
}
