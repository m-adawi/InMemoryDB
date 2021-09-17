package DB.Attributes;

import DB.InvalidDatabaseOperationException;

import java.util.Objects;

public abstract class IntegerAttribute implements Attribute {
    protected Integer value;

    public IntegerAttribute() {
        value = 0;
    }

    public IntegerAttribute(int value) {
        setValue(value);
    }

    public IntegerAttribute(String strVal) {
        setValue(strVal);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public void setValue(Attribute anotherAttribute) {
        setValue(((IntegerAttribute) anotherAttribute).getValue());
    }

    @Override
    public void setValue(String strVal) {
        try {
            value = Integer.parseInt(strVal);
        }
        catch (IllegalArgumentException e){
            throw new InvalidDatabaseOperationException(strVal + " is not an integer");
        }
    }

    @Override
    public String getStrValue() {
        return String.valueOf(value);
    }

    @Override
    public int compareTo(Attribute attribute) {
        if(!(attribute instanceof IntegerAttribute))
            return -1;
        return value.compareTo(((IntegerAttribute) attribute).value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntegerAttribute)) return false;
        IntegerAttribute that = (IntegerAttribute) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
