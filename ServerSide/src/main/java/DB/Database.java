package DB;

import DB.Commands.SelectCommand;
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
        ServerConfigurations conf = ServerConfigurations.getConfigurations();
        storage = new CachedDiskDatabaseStorage(conf.getCacheSize(), conf.getRecordsDirectory());
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

    public void updateRecordByKey(DatabaseKey recordKey, Record record) {
        if(!storage.containsKey(recordKey))
            throwRecordDoesNotExistException();
        // If the record key is to be updated, delete old record and write the new one
        if(!record.getKey().equals(recordKey)) {
            // If the new key already exists, raise exception
            if(storage.containsKey(record.getKey()))
                throwRecordAlreadyExistsException();
            deleteRecordByKey(recordKey);
        }
        storage.write(record);
    }

    private void throwRecordDoesNotExistException() throws InvalidDatabaseOperationException {
        throw new InvalidDatabaseOperationException("Record does not exist");
    }

    public RecordKeysCollection getKeysCollection() {
        return storage.getKeysCollection();
    }

    public void deleteAllRecords() {
        storage.deleteAllRecords();
    }

    public String execute(Command command) {
        command.setStorage(storage);
        return command.execute();
    }

    public String[][] executeQuery(SelectCommand command) {
        command.setStorage(storage);
        return command.executeAndGetTable();
    }
}
