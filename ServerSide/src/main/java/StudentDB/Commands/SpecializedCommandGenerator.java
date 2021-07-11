package StudentDB.Commands;

import DB.InvalidDatabaseOperationException;
import org.gibello.zql.ZStatement;

public abstract class SpecializedCommandGenerator {
    public abstract Command getFromZStatement(ZStatement query);
    protected void checkTableName(String tableName) throws InvalidDatabaseOperationException {
        if(!tableName.toLowerCase().equals("students"))
            throw new InvalidDatabaseOperationException("Invalid table name :" + tableName
                + ". Did you mean: students?");
    }
}
