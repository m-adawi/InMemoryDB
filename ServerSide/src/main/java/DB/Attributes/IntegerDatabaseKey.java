package DB.Attributes;

import DB.DatabaseKey;
import DB.InvalidDatabaseOperationException;

public class IntegerDatabaseKey implements DatabaseKey {
    private int value;

    public IntegerDatabaseKey(int value) {
        this.value = value;
    }

    public IntegerDatabaseKey(String strVal) {
        try {
            value = Integer.parseInt(strVal);
        } catch (IllegalArgumentException e) {
            throw new InvalidDatabaseOperationException(strVal + " is not an integer");
        }
    }

    public IntegerDatabaseKey(IntegerAttribute integerAttribute){
        value = integerAttribute.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntegerDatabaseKey)) return false;
        IntegerDatabaseKey that = (IntegerDatabaseKey) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
