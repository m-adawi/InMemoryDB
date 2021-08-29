package DB.Storages;

import DB.Database;
import DB.DatabaseKey;
import DB.Record;
import DB.RecordKeysCollection;

import java.util.List;

public abstract class DatabaseStorage {
    public abstract void write(Record record);
    public abstract Record read(DatabaseKey recordKey) throws RecordNotFoundException;
    public abstract void delete(DatabaseKey recordKey) throws RecordNotFoundException;
    public abstract boolean containsKey(DatabaseKey recordKey);
    public abstract void deleteAllRecords();
    public abstract RecordKeysCollection getKeysCollection();

    public void write(List<Record> records) {
        for(Record record : records) {
            write(record);
        }
    }

    public void delete(List<DatabaseKey> recordKeys) {
        for(DatabaseKey recordKey : recordKeys) {
            delete(recordKey);
        }
    }
}