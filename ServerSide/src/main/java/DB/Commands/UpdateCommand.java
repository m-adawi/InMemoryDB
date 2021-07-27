package DB.Commands;

import DB.Attributes.*;
import DB.Conditions.Condition;
import DB.DatabaseKey;
import DB.Record;

public class UpdateCommand extends ConditionalCommand {
    private StudentAttributeType[] attributesToBeUpdated;
    private Attribute[] attributeValues;
    private int numberOfAttributesToBeUpdated;

    public UpdateCommand(Condition condition, String[] attributeName, String[] attributeStrValues) {
        super(condition);
        setAttributes(attributeName, attributeStrValues);
    }

    private void setAttributes(String[] attributeNames, String[] attributeStrValues) {
        numberOfAttributesToBeUpdated = attributeNames.length;
        attributesToBeUpdated = new StudentAttributeType[numberOfAttributesToBeUpdated];
        attributeValues = new Attribute[numberOfAttributesToBeUpdated];
        for(int i = 0; i < numberOfAttributesToBeUpdated; i++) {
            attributesToBeUpdated[i] = StudentAttributeType.getTypeFromAttributeName(attributeNames[i]);
            Attribute attribute = StudentAttributeFactory.getByType(attributesToBeUpdated[i]);
            attribute.setValue(attributeStrValues[i]);
            attributeValues[i] = attribute;
        }
    }

    @Override
    protected void executeOnRecord(DatabaseKey recordKey) {
        Record record = database.selectRecordByKey(recordKey);
        executeOnRecord(record);
    }

    @Override
    protected void executeOnRecord(Record record) {
        DatabaseKey oldKey = record.getKey();
        for(int i = 0; i < numberOfAttributesToBeUpdated; i++) {
            record.setAttributeFromTypeAndAnotherAttribute(attributesToBeUpdated[i], attributeValues[i]);
        }
        database.updateRecordByKey(oldKey, record);
    }
}
