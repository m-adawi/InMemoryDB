package DB.Commands;

import org.gibello.zql.*;

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
        if(statement instanceof ZInsert)
            commandGenerator = new InsertCommandGenerator(statement);
        else if(statement instanceof ZDelete)
            commandGenerator = new DeleteCommandGenerator(statement);
        else if(statement instanceof ZUpdate)
            commandGenerator = new UpdateCommandGenerator(statement);
        else if(statement instanceof ZQuery)
            commandGenerator = new SelectCommandGenerator(statement);
        else
            throw new UnsupportedSQLStatementException();
        return commandGenerator.generateCommand();
    }
}
