package DB;

import Server.ServerConfigurations;
import DB.Commands.Command;
import DB.Storages.CachedDiskDatabaseStorage;
import DB.Storages.DatabaseStorage;
import DB.Storages.RecordNotFoundException;

public class Database {
    private final static Database database = new Database();
    private final DatabaseStorage storage;
    private final RecordKeysCollection recordKeysCollection;
    private final RecordsLocker recordsLocker;

    public static Database getDatabase() {
        return database;
    }

    private Database(){
        storage = new CachedDiskDatabaseStorage(ServerConfigurations.getConfigurations().getCacheSize(), "Records");
        recordKeysCollection = storage.getKeysCollection();
        recordsLocker  = new RecordsLocker();
    }

    public void insertRecord(Record record) {
        if(recordKeysCollection.containsKey(record.getKey()))
            throwRecordAlreadyExistsException();
        recordsLocker.lock(record.getKey());
        try {
            addKeyToCollection(record.getKey());
            storage.write(record);
        } finally {
            recordsLocker.unlock(record.getKey());
        }
    }

    private void throwRecordAlreadyExistsException() throws InvalidDatabaseOperationException {
        throw new InvalidDatabaseOperationException("Record already exists");
    }

    private void addKeyToCollection(DatabaseKey recordKey) {
            recordKeysCollection.addKey(recordKey);
    }

    public Record selectRecordByKey(DatabaseKey recordKey) {
        if(!recordKeysCollection.containsKey(recordKey))
            throwRecordDoesNotExistException();
        recordsLocker.lock(recordKey);
        Record record = null;
        try {
            record = storage.read(recordKey);
        } catch (RecordNotFoundException e){
            throwRecordDoesNotExistException();
        }
        finally {
            recordsLocker.unlock(recordKey);
        }
        return record;
    }

    public void deleteRecordByKey(DatabaseKey recordKey) {
        if(!recordKeysCollection.containsKey(recordKey))
            throwRecordDoesNotExistException();
        recordsLocker.lock(recordKey);
        try {
            removeFromKeyCollection(recordKey);
            storage.delete(recordKey);
        } catch (RecordNotFoundException e){
            throwRecordDoesNotExistException();
        } finally {
            recordsLocker.unlock(recordKey);
        }
    }

    private void removeFromKeyCollection(DatabaseKey recordKey) {
            recordKeysCollection.removeKey(recordKey);
    }

    public void updateRecord(Record record) {
        if(!recordKeysCollection.containsKey(record.getKey()))
            throwRecordDoesNotExistException();
        recordsLocker.lock(record.getKey());
        try {
            storage.write(record);
        } finally {
            recordsLocker.unlock(record.getKey());
        }
    }

    private void throwRecordDoesNotExistException() throws InvalidDatabaseOperationException {
        throw new InvalidDatabaseOperationException("Record does not exist");
    }

    //TODO delete it
    public void deleteAllRecords() {
        storage.deleteAllRecords();
        recordKeysCollection.removeAllKeys();
    }

    //TODO modify execute for conditions
    public String execute(Command command) {
        command.execute();
        return "Done";
    }
}
