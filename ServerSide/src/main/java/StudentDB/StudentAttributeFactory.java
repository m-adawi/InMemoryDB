package StudentDB;

import DB.Attribute;
import DB.NullAttribute;

public class StudentAttributeFactory {

    private StudentAttributeFactory() {
    }

    public static Attribute getByAttributeName(String attributeName){
        StudentAttributeType attributeType = StudentAttributeType.getTypeFromAttributeName(attributeName);
        return getByType(attributeType);
    }

    public static Attribute getByType(StudentAttributeType attributeType) {
        switch (attributeType){
            case ID:
                return new StudentID();
            case NAME:
                return new StudentName();
            case FACULTY:
                return new Faculty();
            case MAJOR:
                return new Major();
            case GPA:
                return new GPA();
            case CREDIT_HOURS:
                return new CreditHours();
            default:
                return new NullAttribute();
        }
    }
}
