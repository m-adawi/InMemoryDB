package DB.Attributes;

import DB.DatabaseKey;

public class IntegerDatabaseKey implements DatabaseKey {
    private int value;

    public IntegerDatabaseKey(int value) {
        this.value = value;
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
