package DB;

import DB.Attributes.*;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class Record implements Serializable {
    private final Map<StudentAttributeType, Attribute> map = new EnumMap<>(StudentAttributeType.class);

    public Record(StudentID studentID) {
        map.put(StudentAttributeType.ID, studentID);
    }

    public void setAttributeFromNameAndStrValue(String attributeName, String strVal) {
        StudentAttributeType attributeType = StudentAttributeType.getTypeFromAttributeName(attributeName);
        setAttributeFromTypeAndStrValue(attributeType, strVal);
    }

    public void setAttributeFromTypeAndStrValue(StudentAttributeType attributeType, String strVal){
        Attribute attribute = StudentAttributeFactory.getByType(attributeType);
        attribute.setValue(strVal);
        map.put(attributeType, attribute);
    }

    public void setAttributeFromTypeAndAnotherAttribute(StudentAttributeType attributeType, Attribute anotherAttribute) {
        Attribute attributeToBeSet = StudentAttributeFactory.getByType(attributeType);
        attributeToBeSet.setValue(anotherAttribute);
        map.put(attributeType, attributeToBeSet);
    }

    public Attribute getAttributeFromItsName(String attributeName) {
        StudentAttributeType attributeType = StudentAttributeType.getTypeFromAttributeName(attributeName);
        return getAttributeFromItsType(attributeType);
    }

    public Attribute getAttributeFromItsType(StudentAttributeType attributeType) {
        Attribute attribute = map.get(attributeType);
        // If the attribute is not set for the record,
        // return one with the default value according to the type
        if (attribute == null)
            return StudentAttributeFactory.getByType(attributeType);
        return attribute;
    }

    public DatabaseKey getKey() {
        return new IntegerDatabaseKey((StudentID) map.get(StudentAttributeType.ID));
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Record)) return false;
        Record that = (Record) o;
        for(StudentAttributeType type : StudentAttributeType.values()) {
            Attribute attributeOfThis = map.get(type);
            Attribute attributeOfThat = that.map.get(type);
            if (!Objects.equals(attributeOfThis, attributeOfThat))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }
}
