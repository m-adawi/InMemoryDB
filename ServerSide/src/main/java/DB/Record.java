package DB;

import DB.Attributes.Attribute;

import java.io.Serializable;

public interface Record extends Serializable {
    void setAttributeFromNameAndStrValue(String attributeName, String strVal);
    Attribute getAttributeFromItsName(String attributeName);
    DatabaseKey getKey();
}
