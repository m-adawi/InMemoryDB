package DB;

import java.io.Serializable;

public interface Attribute extends Serializable, Comparable<Attribute>, Cloneable {
    void setValue(String strVal) throws InvalidDatabaseOperationException;
    String getStrValue();
}
