package DB.Commands;

import DB.Attributes.StudentAttributeType;
import DB.Conditions.Condition;
import DB.DatabaseKey;
import DB.Record;

import java.util.List;

public class SelectCommand extends ConditionalCommand {
    private StudentAttributeType[] attributesToBeSelected;
    StringBuilder tableBuilder = new StringBuilder();

    public SelectCommand(DatabaseKey recordKey, String[] selectedColumns) {
        super(recordKey);
        setAttributesToBeSelected(selectedColumns);
    }

    public SelectCommand(Condition condition, String[] selectedColumns) {
        super(condition);
        setAttributesToBeSelected(selectedColumns);
    }

    private void setAttributesToBeSelected(String[] selectedColumns) {
        // if selectedColumns is null this means all the columns are selected
        if(selectedColumns == null) {
            attributesToBeSelected = StudentAttributeType.values();
            return;
        }
        attributesToBeSelected = new StudentAttributeType[selectedColumns.length];
        for(int i = 0; i < attributesToBeSelected.length; i++) {
            attributesToBeSelected[i] = StudentAttributeType.getTypeFromAttributeName(selectedColumns[i]);
        }
    }

    @Override
    protected void executeOnRecord(DatabaseKey recordKey) {
        Record record = database.selectRecordByKey(recordKey);
        tableBuilder.append('|');
        for (StudentAttributeType attribute : attributesToBeSelected) {
            tableBuilder.append(record.getAttributeFromItsType(attribute).getStrValue());
            tableBuilder.append('|');
        }
        tableBuilder.append('\n');
    }


    @Override
    protected String executeOnListOfRecords(List<Record> recordList) {
        for(Record record : recordList) {
            executeOnRecord(record.getKey());
        }
        return tableBuilder.toString();
    }
}
