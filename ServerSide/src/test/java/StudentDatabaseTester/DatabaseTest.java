package StudentDatabaseTester;

import DB.Attributes.IntegerDatabaseKey;
import DB.InvalidDatabaseOperationException;
import DB.Database;
import DB.Attributes.StudentID;
import DB.Record;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DatabaseTest {
    private static final Database database = Database.getDatabase();

    @Before
    public void deletePreviousRecords(){
        database.deleteAllRecords();
    }

    @Test
    public void testInsertionAndSelection(){
        Record record = new Record(new StudentID(1));
        database.insertRecord(record);
        Record record2 = database.selectRecordByKey(record.getKey());
        assertEquals(record, record2);
    }

    @Test(expected = InvalidDatabaseOperationException.class)
    public void testSelectingNonExistentRecordRaisesException(){
        database.selectRecordByKey(new IntegerDatabaseKey(2));
    }

    @Test(expected = InvalidDatabaseOperationException.class)
    public void testDeletingNonExistentRecordRaisesException(){
        database.deleteRecordByKey(new IntegerDatabaseKey(2));
    }
}
