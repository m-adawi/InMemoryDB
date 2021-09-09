package DB.Commands;

import DB.Attributes.StudentAttributeType;
import DB.Conditions.Condition;
import DB.DatabaseKey;
import DB.Record;

import java.util.List;

public class SelectCommand extends ConditionalCommand {
    private StudentAttributeType[] attributesToBeSelected;
    StringBuilder tableBuilder = new StringBuilder();

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

    public String[][] executeAndGetTable() {
        List<Record> recordList = getAllRecordsSatisfiedByCondition();
        String[][] table = new String[recordList.size()+1][attributesToBeSelected.length];
        for(int j = 0; j < attributesToBeSelected.length; j++) {
            table[0][j] = attributesToBeSelected[j].name();
        }
        for(int i = 1; i < table.length; i++) {
            Record record = recordList.get(i-1);
            for(int j = 0; j < table[i].length; j++) {
                table[i][j] = record.getAttributeFromItsType(attributesToBeSelected[j]).getStrValue();
            }
        }
        return table;
    }

    @Override
    protected String executeOnListOfRecords(List<Record> recordList) {
        appendColumnNames();
        for(Record record : recordList) {
            appendRecordRow(record);
        }
        return tableBuilder.toString();
    }

    private void appendColumnNames() {
        tableBuilder.append('|');
        for(StudentAttributeType type : attributesToBeSelected) {
            tableBuilder.append(type.name());
            tableBuilder.append('|');
        }
        tableBuilder.append('\n');
    }

    protected void appendRecordRow(Record record) {
        tableBuilder.append('|');
        for (StudentAttributeType attribute : attributesToBeSelected) {
            tableBuilder.append(record.getAttributeFromItsType(attribute).getStrValue());
            tableBuilder.append('|');
        }
        tableBuilder.append('\n');
    }


}
