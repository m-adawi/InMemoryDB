package DB.Storages;

import DB.DatabaseKey;
import DB.Record;
import DB.RecordKeysCollection;

public interface DatabaseStorage {
    void write(Record record);
    Record read(DatabaseKey recordKey) throws RecordNotFoundException;
    void delete(DatabaseKey recordKey) throws RecordNotFoundException;
    boolean containsKey(DatabaseKey recordKey);
    void deleteAllRecords();
    RecordKeysCollection getKeysCollection();
}