package DB;

public class NullAttribute implements Attribute {
    String errorMessage = "Unknown attribute";

    @Override
    public void setValue(String strVal) {
        errorMessage = strVal;
    }

    @Override
    public String getStrValue() {
        return errorMessage;
    }

    @Override
    public int compareTo(Attribute o) {
        return -1;
    }
}
