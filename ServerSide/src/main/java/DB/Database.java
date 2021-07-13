package DB;

import Server.ServerConfigurations;
import DB.Commands.Command;
import DB.Storages.CachedDiskDatabaseStorage;
import DB.Storages.DatabaseStorage;
import DB.Storages.RecordNotFoundException;

public class Database {
    private final static Database database = new Database();
    private final DatabaseStorage storage;

    public static Database getDatabase() {
        return database;
    }

    private Database(){
        storage = new CachedDiskDatabaseStorage(ServerConfigurations.getConfigurations().getCacheSize(), "Records");
    }

    public void insertRecord(Record record) {
        if(storage.containsKey(record.getKey()))
            throwRecordAlreadyExistsException();
        storage.write(record);
    }

    private void throwRecordAlreadyExistsException() throws InvalidDatabaseOperationException {
        throw new InvalidDatabaseOperationException("Record already exists");
    }

    public Record selectRecordByKey(DatabaseKey recordKey) {
        Record record = null;
        try {
            record = storage.read(recordKey);
        } catch (RecordNotFoundException e){
            throwRecordDoesNotExistException();
        }
        return record;
    }

    public void deleteRecordByKey(DatabaseKey recordKey) {
        try {
            storage.delete(recordKey);
        } catch (RecordNotFoundException e){
            throwRecordDoesNotExistException();
        }
    }

    public void updateRecord(Record record) {
        if(!storage.containsKey(record.getKey()))
            throwRecordDoesNotExistException();
        storage.write(record);
    }

    private void throwRecordDoesNotExistException() throws InvalidDatabaseOperationException {
        throw new InvalidDatabaseOperationException("Record does not exist");
    }

    public RecordKeysCollection getKeysCollection() {
        return storage.getKeysCollection();
    }

    //TODO delete it
    public void deleteAllRecords() {
        storage.deleteAllRecords();
    }

    //TODO modify execute for conditions
    public String execute(Command command) {
        return command.execute();
    }
}
