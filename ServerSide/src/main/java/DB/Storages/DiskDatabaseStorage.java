package DB.Storages;

import DB.*;
import Server.ServerLogger;
import DB.Attributes.IntegerDatabaseKey;

import java.io.*;

public class DiskDatabaseStorage implements DatabaseStorage {
    private final File recordsDirectory;
    private final RecordsLocker recordsLocker = new RecordsLocker();
    private final RecordKeysCollection keysCollection= new RecordKeysCollection();

    public DiskDatabaseStorage(String pathToRecordsDirectory) {
        recordsDirectory = new File(pathToRecordsDirectory);
        if(!recordsDirectory.exists())
            createRecordsDirectory(pathToRecordsDirectory);
        initializeKeysCollection();
    }

    private void createRecordsDirectory(String pathToRecordsDirectory) {
        boolean created = recordsDirectory.mkdir();
        if(!created)
            throw new UnknownErrorException("Could not create " + pathToRecordsDirectory);
    }

    private void initializeKeysCollection() {
        File[] recordFiles = recordsDirectory.listFiles();
        if(recordFiles == null)
            return;
        for(File recordFile : recordFiles){
            IntegerDatabaseKey databaseKey = new IntegerDatabaseKey(Integer.parseInt(recordFile.getName(), 16));
            keysCollection.addKey(databaseKey);
        }
    }

    @Override
    public void write(Record record) {
        recordsLocker.lock(record.getKey());
        File recordFile = getRecordFileFromItsKey(record.getKey());
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(recordFile))) {
            outputStream.writeObject(record);
            keysCollection.addKey(record.getKey());
        } catch (IOException e){
            ServerLogger.log(e);
        } finally {
            recordsLocker.unlock(record.getKey());
        }
    }

    @Override
    public Record read(DatabaseKey recordKey) throws RecordNotFoundException {
        if(!keysCollection.containsKey(recordKey))
            throw new RecordNotFoundException();
        recordsLocker.lock(recordKey);
        File recordFile = getRecordFileFromItsKey(recordKey);
        Record record = null;
        try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(recordFile))) {
            record = (Record) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e){
            ServerLogger.log(e);
        } finally {
            recordsLocker.unlock(recordKey);
        }
        return record;
    }

    @Override
    public void delete(DatabaseKey recordKey) throws RecordNotFoundException {
        if(!keysCollection.containsKey(recordKey))
            throw new RecordNotFoundException();
        recordsLocker.lock(recordKey);
        File recordFile = getRecordFileFromItsKey(recordKey);
        try {
            recordFile.delete();
            keysCollection.removeKey(recordKey);
        } finally {
            recordsLocker.unlock(recordKey);
        }
    }

    @Override
    public boolean containsKey(DatabaseKey recordKey) {
        return keysCollection.containsKey(recordKey);
    }

    private File getRecordFileFromItsKey(DatabaseKey databaseKey){
        String recordFileName = getRecordFileNameFromItsKey(databaseKey);
        return new File(recordsDirectory, recordFileName);
    }

    private String getRecordFileNameFromItsKey(DatabaseKey databaseKey){
        // Converts the integer key into a hexadecimal string
        return String.format("%08X", databaseKey.hashCode());
    }

    public void deleteAllRecords() {
        File[] recordFiles = recordsDirectory.listFiles();
        if(recordFiles == null)
            return;
        for(File recordFile : recordFiles){
            boolean deleted = recordFile.delete();
            if(!deleted) {
                RuntimeException e = new UnknownErrorException("Could not delete " + recordFile);
                ServerLogger.log(e);
                throw e;
            }
        }
        keysCollection.removeAllKeys();
    }

    public RecordKeysCollection getKeysCollection() {
        return keysCollection;
    }
}