package StudentDB;

import DB.DatabaseKey;
import DB.IntegerDatabaseKey;

public class CacheDatabaseKeyFactory {
    private int cacheSize;

    public CacheDatabaseKeyFactory(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    public IntegerDatabaseKey mapToCacheDatabaseKey(DatabaseKey databaseKey) {
        int integerKey = databaseKey.hashCode() % cacheSize;
        return new IntegerDatabaseKey(integerKey);
    }
}
