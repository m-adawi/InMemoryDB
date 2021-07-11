package StudentDB;

import DB.StringAttribute;

public class StudentName extends StringAttribute {
    private static int MAX_STRING_LENGTH = 100;

    public StudentName() {
    }

    public StudentName(String value) {
        super(value);
    }

    @Override
    protected int maximumLength() {
        return MAX_STRING_LENGTH;
    }
}
