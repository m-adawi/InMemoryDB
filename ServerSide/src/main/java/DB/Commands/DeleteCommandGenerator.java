package DB.Commands;

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
            return generateDeleteSingleRecordCommand(query);
        return null;
    }

    private Command generateDeleteSingleRecordCommand(ZDelete query) {
        return new DeleteSingleRecordCommand(getSingleRecordKey(query.getWhere()));
    }
}
