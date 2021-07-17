package DB.Commands;

import DB.Conditions.Condition;
import DB.Conditions.ConditionFactory;
import DB.Database;
import DB.DatabaseKey;
import org.gibello.zql.ZDelete;
import org.gibello.zql.ZStatement;

public class DeleteCommandGenerator extends SpecializedConditionalCommandGenerator {
    private final ZDelete query;

    public DeleteCommandGenerator(ZStatement statement) {
        query = (ZDelete) statement;
    }

    @Override
    public Command generateCommand() {
        checkTableName(query.getTable());
        if(isOnSingleRecord(query.getWhere()))
            return generateDeleteSingleRecordCommand();
        else
            return generateDeleteOnCondition();
    }

    private Command generateDeleteSingleRecordCommand() {
        DatabaseKey recordKey = getSingleRecordKey(query.getWhere());
        return new DeleteCommand(recordKey);
    }

    private Command generateDeleteOnCondition(){
        Condition condition = getCondition(query.getWhere());
        return new DeleteCommand(condition);
    }
}
