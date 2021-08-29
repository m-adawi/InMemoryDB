package DB.Storages;

public class RecordNotFoundException extends RuntimeException{
    public RecordNotFoundException() {
        super("Record not found");
    }
}
