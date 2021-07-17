package DB.Attributes;

import DB.InvalidDatabaseOperationException;

import java.io.Serializable;

public interface Attribute extends Serializable, Comparable<Attribute> {
    void setValue(String strVal) throws InvalidDatabaseOperationException;
    void setValue(Attribute anotherAttribute);
    String getStrValue();
}
