package StudentDB.Conditions;

import DB.Attribute;
import DB.Record;

public class EqualAttributeCondition extends StudentComparisonCondition {
    public EqualAttributeCondition(String attributeName, String attributeStrValue) {
        super(attributeName, attributeStrValue);
    }

    @Override
    public boolean isSatisfiedOnRecord(Record record) {
        Attribute recordAttribute = getRecordAttribute(record);
        return recordAttribute.equals(attributeToBeComparedWith);
    }
}
