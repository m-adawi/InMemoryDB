package DB.Conditions;

import DB.Attributes.Attribute;
import DB.Attributes.StudentAttributeFactory;
import DB.Attributes.StudentAttributeType;
import DB.Record;

public abstract class SimpleCondition implements Condition {
    protected StudentAttributeType attributeType;
    protected Attribute attributeToBeComparedWith;

    public SimpleCondition(String attributeName, String attributeStrValue) {
        attributeType = StudentAttributeType.getTypeFromAttributeName(attributeName);
        attributeToBeComparedWith = StudentAttributeFactory.getByType(attributeType);
        attributeToBeComparedWith.setValue(attributeStrValue);
    }

    protected Attribute getRecordAttribute(Record record) {
        return record.getAttributeFromItsType(attributeType);
    }
}
