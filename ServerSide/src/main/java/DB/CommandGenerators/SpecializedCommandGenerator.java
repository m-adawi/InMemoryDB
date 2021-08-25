package DB.CommandGenerators;

import DB.Commands.Command;
import DB.InvalidDatabaseOperationException;

public abstract class SpecializedCommandGenerator {
    public abstract Command generateCommand();
    protected void checkTableName(String tableName) throws InvalidDatabaseOperationException {
        if(!tableName.toLowerCase().equals("students"))
            throw new InvalidDatabaseOperationException("Invalid table name :" + tableName
                + ". Did you mean: students?");
    }
}
