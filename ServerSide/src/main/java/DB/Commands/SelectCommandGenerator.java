package DB.Commands;

import DB.Conditions.Condition;
import DB.Database;
import DB.DatabaseKey;
import org.gibello.zql.ZFromItem;
import org.gibello.zql.ZQuery;
import org.gibello.zql.ZSelectItem;
import org.gibello.zql.ZStatement;

import java.util.Vector;

public class SelectCommandGenerator extends SpecializedConditionalCommandGenerator {
    private final ZQuery query;
    private String[] columns;

    public SelectCommandGenerator(ZStatement query) {
        this.query = (ZQuery) query;
    }

    @Override
    public Command generateCommand() {
        if(query.getFrom().size() > 1)
            throw new UnsupportedSQLStatementException();
        checkTableName(((ZFromItem)query.getFrom().elementAt(0)).getTable());
        prepareColumns();
        if(isOnSingleRecord(query.getWhere()))
            return generateSelectSingleRecordCommand();
        else
            return generateSelectOnConditionCommand();
    }

    private void prepareColumns() {
        // if all records are selected, keep columns as null to indicate the selection of all records
        if(areAllRecordsSelected())
            return;
        columns = new String[query.getSelect().size()];
        for(int i = 0; i < columns.length; i++) {
            columns[i] = ((ZSelectItem) query.getSelect().elementAt(i)).getColumn();
        }
    }

    private boolean areAllRecordsSelected() {
        Vector selectedColumns = query.getSelect();
        return selectedColumns.size() == 1 && selectedColumns.elementAt(0).toString().equals("*");
    }

    private Command generateSelectSingleRecordCommand() {
        DatabaseKey recordKey = getSingleRecordKey(query.getWhere());
        return new SelectCommand(recordKey, columns);
    }

    private Command generateSelectOnConditionCommand() {
        Condition condition = getCondition(query.getWhere());
        return new SelectCommand(condition, columns);
    }
}
