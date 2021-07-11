package StudentDB.Commands;

import org.gibello.zql.ParseException;
import org.gibello.zql.ZInsert;
import org.gibello.zql.ZStatement;
import org.gibello.zql.ZqlParser;

import java.io.ByteArrayInputStream;

// Creates CommandGenerator
public class CommandsGenerator {
    private static final CommandsGenerator commandsGenerator = new CommandsGenerator();

    public static CommandsGenerator getCommandGenerator(){
        return commandsGenerator;
    }

    private CommandsGenerator() {
    }

    public Command generateFromSqlQuery(String sqlQuery) throws ParseException {
        ZqlParser parser = new ZqlParser(new ByteArrayInputStream(sqlQuery.getBytes()));
        ZStatement statement = parser.readStatement();
        SpecializedCommandGenerator commandGenerator;
        // TODO complete it
        if(statement instanceof ZInsert)
            commandGenerator = InsertCommandGenerator.getInstance();
        else
            throw new UnsupportedSQLStatementException();
        return commandGenerator.getFromZStatement(statement);
    }
}
