package DB.Commands;

import DB.Attributes.*;
import DB.Conditions.Condition;
import DB.DatabaseKey;
import DB.Record;

import java.util.List;

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
    protected String executeOnListOfRecords(List<Record> recordList) {
        for(Record record : recordList) {
            updateRecord(record);
        }
        storage.write(recordList);
        return "Updated " + recordList.size() + " records";
    }

    protected void updateRecord(Record record) {
        for(int i = 0; i < numberOfAttributesToBeUpdated; i++) {
            record.setAttributeFromTypeAndAnotherAttribute(attributesToBeUpdated[i], attributeValues[i]);
        }
    }
}
