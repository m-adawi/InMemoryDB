package DB.Storages;

import DB.*;
import Server.ServerLogger;
import DB.Attributes.IntegerDatabaseKey;

import java.io.*;

public class DiskDatabaseStorage implements DatabaseStorage {
    private final File recordsDirectory;
    private final RecordKeysCollection keysCollection = new RecordKeysCollection();

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
        File[] firstSubDirs = recordsDirectory.listFiles();
        if(firstSubDirs == null)
            return;
        for(File secondSubDir : firstSubDirs){
            File[] recordFiles = secondSubDir.listFiles();
            for(File recordFile : recordFiles) {
                IntegerDatabaseKey databaseKey = new IntegerDatabaseKey(Integer.parseInt(recordFile.getName(), 16));
                keysCollection.addKey(databaseKey);
            }
        }
    }

    //TODO: add a write method with multiple records to lock them all first and modify delete and update commands
    @Override
    public void write(Record record) {
        File recordFile = getRecordFileFromItsKey(record.getKey());
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(recordFile))) {
            outputStream.writeObject(record);
            keysCollection.addKey(record.getKey());
        } catch (IOException e){
            ServerLogger.log(e);
        }
    }

    @Override
    public Record read(DatabaseKey recordKey) throws RecordNotFoundException {
        if(!keysCollection.containsKey(recordKey))
            throw new RecordNotFoundException();
        File recordFile = getRecordFileFromItsKey(recordKey);
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
        if(!keysCollection.containsKey(recordKey))
            throw new RecordNotFoundException();
        File recordFile = getRecordFileFromItsKey(recordKey);
        recordFile.delete();
        keysCollection.removeKey(recordKey);
    }

    @Override
    public boolean containsKey(DatabaseKey recordKey) {
        return keysCollection.containsKey(recordKey);
    }

    private File getRecordFileFromItsKey(DatabaseKey databaseKey){
        String recordFileName = getRecordFileNameFromItsKey(databaseKey);
        File firstSubDir = new File(recordsDirectory, getNthByteInHexadecimal(databaseKey.hashCode(), 0));
        if(!firstSubDir.exists())
            firstSubDir.mkdir();
        File secondSubDir = new File(firstSubDir, getNthByteInHexadecimal(databaseKey.hashCode(), 1));
        if(!secondSubDir.exists())
            secondSubDir.mkdir();
        return new File(secondSubDir, recordFileName);
    }

    private String getRecordFileNameFromItsKey(DatabaseKey databaseKey){
        // Converts the integer key into a hexadecimal string
        return String.format("%08X", databaseKey.hashCode());
    }

    private String getNthByteInHexadecimal(int key, int n) {
        return String.format("%02X", (key >> (n*8))&0xff);
    }

    public void deleteAllRecords() {
        try {
            for (DatabaseKey recordKey : keysCollection) {
                delete(recordKey);
            }
        }catch (RecordNotFoundException e) {
            ServerLogger.log(e);
        }
        keysCollection.removeAllKeys();
    }

    public RecordKeysCollection getKeysCollection() {
        return keysCollection;
    }
}