package DB.Conditions;

import DB.Attributes.Attribute;
import DB.Record;
import DB.Attributes.StudentAttributeFactory;
import DB.Attributes.StudentAttributeType;
import DB.StudentRecord;

public abstract class StudentComparisonCondition implements Condition {
    protected StudentAttributeType attributeType;
    protected Attribute attributeToBeComparedWith;

    public StudentComparisonCondition(String attributeName, String attributeStrValue) {
        attributeType = StudentAttributeType.getTypeFromAttributeName(attributeName);
        attributeToBeComparedWith = StudentAttributeFactory.getByType(attributeType);
        attributeToBeComparedWith.setValue(attributeStrValue);
    }

    protected Attribute getRecordAttribute(Record record) {
        return ((StudentRecord) record).getAttributeFromItsType(attributeType);
    }
}
