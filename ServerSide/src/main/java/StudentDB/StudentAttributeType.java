package StudentDB;

import DB.InvalidDatabaseOperationException;

public enum StudentAttributeType {
    ID, NAME, FACULTY, MAJOR, CREDIT_HOURS, GPA;

    public static StudentAttributeType getTypeFromAttributeName(String attributeName) {
        StudentAttributeType type;

        try {
            // Ignore case
            type = StudentAttributeType.valueOf(attributeName.toUpperCase());
        }
        catch (IllegalArgumentException e){
            throw new InvalidDatabaseOperationException("Invalid attribute name: " + attributeName);
        }

        return type;
    }
}
