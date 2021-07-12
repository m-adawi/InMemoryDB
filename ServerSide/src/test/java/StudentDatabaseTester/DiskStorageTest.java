package StudentDatabaseTester;

import DB.Storages.DiskDatabaseStorage;
import org.junit.BeforeClass;

public class DiskStorageTest extends DatabaseStorageTest {
    @BeforeClass
    public static void setStorage() {
        storage = new DiskDatabaseStorage("Student Records Test");
    }
}
