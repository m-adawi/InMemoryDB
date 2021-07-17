package DB.Attributes;

public class Major extends StringAttribute {
    protected static int MAX_STRING_LENGTH = 100;

    public Major() {
    }

    public Major(String value) {
        super(value);
    }

    @Override
    protected int maximumLength() {
        return MAX_STRING_LENGTH;
    }
}

