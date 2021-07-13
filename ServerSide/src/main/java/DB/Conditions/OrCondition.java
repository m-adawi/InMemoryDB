package DB.Conditions;

import DB.Record;

public class OrCondition extends CompoundCondition {
    public OrCondition(Condition... operands) {
        super(operands);
    }

    @Override
    public boolean isSatisfiedOnRecord(Record record) {
        for(Condition condition : operands){
            if(condition.isSatisfiedOnRecord(record))
                return true;
        }
        return false;
    }
}
