package StudentDB;

import DB.*;
import Server.ServerLogger;

import java.io.*;

public class DiskDatabaseStorage implements DatabaseStorage {
    private final File recordsDirectory;

    public DiskDatabaseStorage(String pathToRecordsDirectory) {
        recordsDirectory = new File(pathToRecordsDirectory);
        if(!recordsDirectory.exists())
            createRecordsDirectory(pathToRecordsDirectory);
    }

    private void createRecordsDirectory(String pathToRecordsDirectory) {
            boolean created = recordsDirectory.mkdir();
            if(!created)
                throw new UnknownErrorException("Could not create " + pathToRecordsDirectory);
    }

    @Override
    public void write(Record record) {
        File recordFile = getRecordFileFromItsKey(record.getKey());
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(recordFile))) {
            outputStream.writeObject(record);
        } catch (IOException e){
            ServerLogger.log(e);
        }
    }

    @Override
    public Record read(DatabaseKey recordKey) throws RecordNotFoundException {
        File recordFile = getRecordFileFromItsKey(recordKey);
        if(!recordFile.exists())
            throw new RecordNotFoundException();
        Record record = null;
        try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(recordFile))) {
            record = (Record) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e){
            ServerLogger.log(e);
        }
        return record;
    }

    @Override
    public void delete(DatabaseKey recordKey) throws RecordNotFoundException {
        File recordFile = getRecordFileFromItsKey(recordKey);
        boolean deleted = recordFile.delete();
        if(!deleted)
            throw new RecordNotFoundException();
    }

    @Override
    public boolean containsKey(DatabaseKey recordKey) {
        return getRecordFileFromItsKey(recordKey).exists();
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
    }

    public RecordKeysCollection getKeysCollection() {
        RecordKeysCollection collection = new RecordKeysCollection();
        File[] recordFiles = recordsDirectory.listFiles();
        if(recordFiles == null)
            return collection;
        for(File recordFile : recordFiles){
            IntegerDatabaseKey databaseKey = new IntegerDatabaseKey(Integer.decode(recordFile.getName()));
            collection.addKey(databaseKey);
        }
        return collection;
    }
}