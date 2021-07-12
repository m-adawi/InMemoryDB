package DB.Storages;

import DB.Attributes.IntegerDatabaseKey;
import DB.DatabaseKey;
import DB.Record;
import DB.RecordKeysCollection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryStorage implements DatabaseStorage {
    private final Map<IntegerDatabaseKey, Record> map = new ConcurrentHashMap<>();
    private final CacheDatabaseKeyFactory cacheDatabaseKeyFactory;

    public MemoryStorage(int cacheSize) {
        cacheDatabaseKeyFactory = new CacheDatabaseKeyFactory(cacheSize);
    }

    @Override
    public void write(Record record) {
        IntegerDatabaseKey key = cacheDatabaseKeyFactory.mapToCacheDatabaseKey(record.getKey());
        map.put(key, record);
    }

    @Override
    public Record read(DatabaseKey recordKey) throws RecordNotFoundException {
        IntegerDatabaseKey key = cacheDatabaseKeyFactory.mapToCacheDatabaseKey(recordKey);
        Record record = map.get(key);
        if(record != null && record.getKey().equals(recordKey))
            return record;
        else
            throw new RecordNotFoundException();
    }

    @Override
    public void delete(DatabaseKey recordKey) throws RecordNotFoundException {
        IntegerDatabaseKey key = cacheDatabaseKeyFactory.mapToCacheDatabaseKey(recordKey);
        Record record = map.get(key);
        if(record == null)
            throw new RecordNotFoundException();
        if(record.getKey().equals(recordKey))
            map.remove(key);
    }

    @Override
    public boolean containsKey(DatabaseKey recordKey) {
        IntegerDatabaseKey key = cacheDatabaseKeyFactory.mapToCacheDatabaseKey(recordKey);
        Record record = map.get(key);
        return record != null && record.getKey().equals(recordKey);
    }

    @Override
    public void deleteAllRecords() {
        map.clear();
    }

    @Override
    public RecordKeysCollection getKeysCollection() {
        RecordKeysCollection collection = new RecordKeysCollection();
        for(DatabaseKey key : map.keySet()) {
            collection.addKey(key);
        }
        return collection;
    }
}
