package DB.Commands;

import DB.Conditions.Condition;
import DB.Conditions.ConditionFactory;
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
        return new DeleteCommand(getSingleRecordKey(query.getWhere()));
    }

    private Command generateDeleteOnCondition(){
        Condition condition = ConditionFactory.getInstance().getByZExp(query.getWhere());
        return new DeleteCommand(condition);
    }
}
