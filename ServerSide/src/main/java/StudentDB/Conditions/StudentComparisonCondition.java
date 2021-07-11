package StudentDB.Conditions;

import DB.Attribute;
import DB.Conditions.Condition;
import DB.Record;
import StudentDB.StudentAttributeFactory;
import StudentDB.StudentAttributeType;
import StudentDB.StudentRecord;

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
