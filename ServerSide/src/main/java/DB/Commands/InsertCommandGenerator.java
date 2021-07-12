package DB.Commands;

import DB.InvalidDatabaseOperationException;
import DB.Record;
import DB.Attributes.StudentAttributeType;
import DB.Attributes.StudentID;
import DB.StudentRecord;
import org.gibello.zql.ZInsert;
import org.gibello.zql.ZStatement;

import java.util.Vector;

public class InsertCommandGenerator extends SpecializedCommandGenerator {
    private final ZInsert query;

    public InsertCommandGenerator(ZStatement statement){
        query = (ZInsert) statement;
    }

    @Override
    public Command generateCommand() {
        checkTableName(query.getTable());
        Record record;
        if(query.getColumns() == null)
            record = generateRecordForAllAttributes(query.getValues());
        else
            record = generateRecordForSetOfAttributes(query.getColumns(), query.getValues());
        return new InsertRecordCommand(record);
    }

    private Record generateRecordForAllAttributes(Vector values){
        StudentRecord record = new StudentRecord(new StudentID(values.elementAt(0).toString()));
        StudentAttributeType[] attributeTypes = StudentAttributeType.values();
        if(values.size() != attributeTypes.length)
            throwColumnsAndValuesMismatchException(attributeTypes.length, values.size());
        for(int i = 1; i < values.size(); i++){
            record.setAttributeFromTypeAndStrValue(attributeTypes[i], values.elementAt(i).toString());
        }
        return record;
    }

    private Record generateRecordForSetOfAttributes(Vector columns, Vector values) {
        if(columns.size() != values.size())
            throwColumnsAndValuesMismatchException(columns.size(), values.size());
        // look for ID attribute
        int indexOfID = -1;
        for(int i = 0; i < columns.size(); i++){
            if(columns.elementAt(i).toString().equalsIgnoreCase(StudentAttributeType.ID.name()))
                indexOfID = i;
        }
        if(indexOfID == -1){
            throw new InvalidDatabaseOperationException("You must provide " + StudentAttributeType.ID);
        }
        Record record = new StudentRecord(new StudentID(values.elementAt(indexOfID).toString()));
        for(int i = 0; i < columns.size(); i++){
            if(i == indexOfID)
                continue;
            record.setAttributeFromNameAndStrValue(columns.elementAt(i).toString(), values.elementAt(i).toString());
        }
        return record;
    }

    private void throwColumnsAndValuesMismatchException(int columnsNumber, int valuesNumber){
        throw new InvalidDatabaseOperationException("Columns and values mismatch: " + columnsNumber + " columns and "
                +valuesNumber + " values were provided");
    }
}

