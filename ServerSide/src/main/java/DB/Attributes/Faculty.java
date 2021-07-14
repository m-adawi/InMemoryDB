package DB.Attributes;

public class Faculty extends StringAttribute {
    private static int MAX_STRING_LENGTH = 100;

    public Faculty() {
    }

    public Faculty(String value) {
        super(value);
    }

    public Faculty(StringAttribute anotherStringAttribute) {
        super(anotherStringAttribute);
    }

    @Override
    protected int maximumLength() {
        return MAX_STRING_LENGTH;
    }
}
