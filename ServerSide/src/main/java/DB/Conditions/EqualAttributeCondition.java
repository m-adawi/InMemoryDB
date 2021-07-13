package DB.Conditions;

import DB.Attributes.Attribute;
import DB.Record;

public class EqualAttributeCondition extends SimpleCondition {
    public EqualAttributeCondition(String attributeName, String attributeStrValue) {
        super(attributeName, attributeStrValue);
    }

    @Override
    public boolean isSatisfiedOnRecord(Record record) {
        Attribute recordAttribute = getRecordAttribute(record);
        return recordAttribute.equals(attributeToBeComparedWith);
    }
}
