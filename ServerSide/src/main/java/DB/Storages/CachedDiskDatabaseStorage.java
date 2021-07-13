package DB.Storages;

import DB.DatabaseKey;
import DB.InvalidDatabaseOperationException;
import DB.Record;
import DB.RecordKeysCollection;

public class CachedDiskDatabaseStorage implements DatabaseStorage {
    private final DatabaseStorage memoryStorage;
    private final DatabaseStorage diskStorage;

    public CachedDiskDatabaseStorage(int cacheSize, String pathToRecordsDirectory) {
        memoryStorage = new MemoryStorage(cacheSize);
        diskStorage = new DiskDatabaseStorage(pathToRecordsDirectory);
    }

    @Override
    public void write(Record record) throws InvalidDatabaseOperationException {
        writeToCache(record);
        writeToDisk(record);
    }

    private void writeToCache(Record record){
        memoryStorage.write(record);
    }

    private void writeToDisk(Record record){
        diskStorage.write(record);
    }

    @Override
    public Record read(DatabaseKey recordKey) throws RecordNotFoundException {
        Record record;
        try {
            record = memoryStorage.read(recordKey);
        } catch (RecordNotFoundException e){ // Cache miss
            record = diskStorage.read(recordKey);
            // Write most recent accessed records to cache
            memoryStorage.write(record);
        }
        return record;
    }

    @Override
    public void delete(DatabaseKey recordKey) throws RecordNotFoundException{
        try {
            memoryStorage.delete(recordKey);
        } catch (RecordNotFoundException e){
            // ignore since it's cache
        }
        diskStorage.delete(recordKey);
    }

    @Override
    public boolean containsKey(DatabaseKey recordKey) {
        return diskStorage.containsKey(recordKey);
    }

    @Override
    public void deleteAllRecords() {
        memoryStorage.deleteAllRecords();
        diskStorage.deleteAllRecords();
    }

    public RecordKeysCollection getKeysCollection() {
        return diskStorage.getKeysCollection();
    }
}
