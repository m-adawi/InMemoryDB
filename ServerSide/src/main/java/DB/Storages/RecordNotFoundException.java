package DB.Storages;

public class RecordNotFoundException extends Exception{
    public RecordNotFoundException() {
        super("Record not found");
    }
}
