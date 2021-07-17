package DB.Attributes;

import DB.DatabaseKey;

public class StudentID extends IntegerAttribute {

    public StudentID() {
    }

    public StudentID(int value) {
        super(value);
    }

    public StudentID(String strVal) {
        super(strVal);
    }

    public StudentID(DatabaseKey databaseKey) {
        value = databaseKey.hashCode();
    }

    @Override
    public String getStrValue() {
        // return ID with leading zeros (assuming 8 digits)
        return String.format("%08d", value);
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof StudentID)) return false;
        StudentID that = (StudentID) obj;
        return value.equals(that.value);
    }
}
