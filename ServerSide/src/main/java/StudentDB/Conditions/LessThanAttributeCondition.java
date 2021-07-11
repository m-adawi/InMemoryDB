package StudentDB.Conditions;

import DB.Attribute;
import DB.Record;

public class LessThanAttributeCondition extends StudentComparisonCondition {
    public LessThanAttributeCondition(String attributeName, String attributeStrValue) {
        super(attributeName, attributeStrValue);
    }

    @Override
    public boolean isSatisfiedOnRecord(Record record) {
        Attribute recordAttribute = getRecordAttribute(record);
        return recordAttribute.compareTo(attributeToBeComparedWith) < 0;
    }
}
