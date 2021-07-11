package DB;

public class RecordNotFoundException extends Exception{
    public RecordNotFoundException() {
        super("Record not found");
    }
}
