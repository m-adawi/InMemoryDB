package DB.Storages;

import DB.*;

public class CachedDiskDatabaseStorage implements DatabaseStorage {
    private final DatabaseStorage memoryStorage;
    private final DatabaseStorage diskStorage;
    private final RecordsLocker recordsLocker = new RecordsLocker();

    public CachedDiskDatabaseStorage(int cacheSize, String pathToRecordsDirectory) {
        memoryStorage = new MemoryStorage(cacheSize);
        diskStorage = new DiskDatabaseStorage(pathToRecordsDirectory);
    }

    @Override
    public void write(Record record) {
        recordsLocker.lock(record.getKey());
        try {
            writeToStorages(record);
        } finally {
            recordsLocker.unlock(record.getKey());
        }
    }

    private void writeToStorages(Record record) {
        memoryStorage.write(record);
        diskStorage.write(record);
    }

    @Override
    public Record read(DatabaseKey recordKey) throws RecordNotFoundException {
        recordsLocker.lock(recordKey);
        try {
            return readFromStorages(recordKey);
        } finally {
            recordsLocker.unlock(recordKey);
        }
    }

    private Record readFromStorages(DatabaseKey recordKey) throws RecordNotFoundException {
        Record record;
        try {
            record = memoryStorage.read(recordKey);
        } catch (RecordNotFoundException e){ // Cache miss
            record = diskStorage.read(recordKey);
            // Write most recently accessed records to cache
            memoryStorage.write(record);
        }
        return record;
    }

    @Override
    public void delete(DatabaseKey recordKey) throws RecordNotFoundException{
        recordsLocker.lock(recordKey);
        try {
            deleteFromStorages(recordKey);
        }
        finally {
            recordsLocker.unlock(recordKey);
        }
    }

    private void deleteFromStorages(DatabaseKey recordKey) throws RecordNotFoundException {
        memoryStorage.delete(recordKey);
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
