package DB.Attributes;

import DB.InvalidDatabaseOperationException;

import java.util.Objects;

public abstract class StringAttribute implements Attribute {
    protected String value;

    public StringAttribute() {
        value = "Not assigned";
    }

    public StringAttribute(String value) {
        setValue(value);
    }

    @Override
    public  void setValue(String strVal) {
        if(strVal.length() > maximumLength())
            throw new InvalidDatabaseOperationException("Above the maximum " + maximumLength() + " characters allowed length");
        // Extract string from between quotes
        if(strVal.startsWith("\"") && strVal.endsWith("\"") || strVal.startsWith("'") && strVal.endsWith("'"))
            value = strVal.substring(1, strVal.length()-1);
        else
            value = strVal;
    }

    @Override
    public void setValue(Attribute anotherAttribute) {
        setValue(anotherAttribute.getStrValue());
    }

    @Override
    public String getStrValue() {
        return value;
    }


    @Override
    public int compareTo(Attribute attribute) {
        if(!(attribute instanceof StringAttribute))
            return -1;
        // compare ignoring case
        return value.toUpperCase().compareTo(((StringAttribute) attribute).value.toUpperCase());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringAttribute)) return false;
        StringAttribute that = (StringAttribute) o;
        return compareTo((Attribute) o) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    protected abstract int maximumLength();
}
