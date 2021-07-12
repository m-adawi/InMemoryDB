package StudentDatabaseTester;

import DB.Record;
import DB.Commands.Command;
import DB.Commands.CommandsGenerator;
import DB.Commands.InsertRecordCommand;
import DB.Attributes.StudentID;
import DB.StudentRecord;
import org.gibello.zql.ParseException;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StudentCommandGeneratorTest {
    CommandsGenerator commandsGenerator = CommandsGenerator.getCommandGenerator();
    @Test
    public void testInsert() throws ParseException {
        String sqlQuery = "insert into students (ID, Name, Faculty) values (12, malek, Engineering);";
        Command command = commandsGenerator.generateFromSqlQuery(sqlQuery);
        assertTrue(command instanceof InsertRecordCommand);
        Record record = new StudentRecord(new StudentID(12));
        record.setAttributeFromNameAndStrValue("name", "malek");
        record.setAttributeFromNameAndStrValue("faculty", "engineering");
        assertTrue(record.equals(((InsertRecordCommand) command).getRecord()));
        record.setAttributeFromNameAndStrValue("id", "3");
        assertFalse(record.equals(((InsertRecordCommand) command).getRecord()));
    }
}
