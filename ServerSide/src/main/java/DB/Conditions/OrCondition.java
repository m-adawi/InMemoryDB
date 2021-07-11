package DB.Conditions;

import DB.Record;

public class OrCondition extends ComplexCondition {
    public OrCondition(Condition... operands) {
        super(operands);
    }

    @Override
    public boolean isSatisfiedOnRecord(Record record) {
        for(Condition condition : operands){
            if(isSatisfiedOnRecord(record))
                return true;
        }
        return false;
    }
}
