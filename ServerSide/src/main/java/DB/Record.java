package DB;

import DB.Attributes.Attribute;

import java.io.Serializable;

public interface Record extends Serializable, Cloneable {
    void setAttributeFromNameAndStrValue(String attributeName, String strVal) throws InvalidDatabaseOperationException;
    Attribute getAttributeFromItsName(String attributeName) throws InvalidDatabaseOperationException;
    DatabaseKey getKey();
}
