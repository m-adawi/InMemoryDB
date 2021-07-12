package DB.Attributes;

import DB.InvalidDatabaseOperationException;

import java.util.Objects;

public abstract class DoubleAttribute implements Attribute, Cloneable {
    protected Double value;

    public DoubleAttribute() {
        value = 0.0;
    }

    public DoubleAttribute(double value) {
        setValue(value);
    }

    public DoubleAttribute(String strVal) {
        setValue(strVal);
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public void setValue(String strVal) {
        try {
            value = Double.parseDouble(strVal);
        }
        catch (IllegalArgumentException e){
            throw new InvalidDatabaseOperationException(value + " is not a double");
        }
    }

    @Override
    public String getStrValue() {
        return value.toString();
    }

    @Override
    public int compareTo(Attribute attribute) {
        if(!(attribute instanceof DoubleAttribute))
            return -1;
        return value.compareTo(((DoubleAttribute) attribute).value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoubleAttribute)) return false;
        DoubleAttribute that = (DoubleAttribute) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
