package DB.Conditions;

import DB.Attributes.Attribute;
import DB.Record;

public class GreaterThanAttributeCondition extends StudentComparisonCondition {
    public GreaterThanAttributeCondition(String attributeName, String attributeStrValue) {
        super(attributeName, attributeStrValue);
    }

    @Override
    public boolean isSatisfiedOnRecord(Record record) {
        Attribute recordAttribute = getRecordAttribute(record);
        return recordAttribute.compareTo(attributeToBeComparedWith) > 0;
    }
}
