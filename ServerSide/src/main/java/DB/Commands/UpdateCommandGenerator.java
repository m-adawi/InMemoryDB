package DB.Commands;

import DB.Conditions.Condition;
import DB.DatabaseKey;
import org.gibello.zql.ZStatement;
import org.gibello.zql.ZUpdate;

public class UpdateCommandGenerator extends SpecializedConditionalCommandGenerator {
    private final ZUpdate query;
    private String[] columns;
    private String[] values;

    public UpdateCommandGenerator(ZStatement query) {
        this.query = (ZUpdate) query;
    }

    @Override
    public Command generateCommand() {
        checkTableName(query.getTable());
        prepareColumnsAndValues();
        return generateUpdateOnConditionCommand();
    }

    private void prepareColumnsAndValues() {
        int numberOfAttributesToBeUpdated = query.getColumnUpdateCount();
        columns = new String[numberOfAttributesToBeUpdated];
        values = new String[numberOfAttributesToBeUpdated];
        for(int i = 0; i < numberOfAttributesToBeUpdated; i++) {
            columns[i] = query.getColumnUpdateName(i+1); // Index starts from 1
            values[i] = query.getColumnUpdate(i+1).toString();
        }
    }

    private Command generateUpdateOnConditionCommand() {
        Condition condition = getCondition(query.getWhere());
        return new UpdateCommand(condition, columns, values);
    }
}
