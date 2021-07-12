package StudentDatabaseTester;

import DB.Storages.CachedDiskDatabaseStorage;
import org.junit.BeforeClass;

public class CachedDiskStorageTest extends DatabaseStorageTest {
    @BeforeClass
    public static void setStorage() {
        int cacheSize = 100;
        String pathToRecordsDirectory = "Students Record";
        storage = new CachedDiskDatabaseStorage(cacheSize, pathToRecordsDirectory);
    }
}
