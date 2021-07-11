package StudentDatabaseTester;

import DB.Attribute;
import DB.InvalidDatabaseOperationException;
import DB.NullAttribute;
import StudentDB.StudentAttributeFactory;
import StudentDB.StudentAttributeType;
import org.junit.Test;

import static org.junit.Assert.assertFalse;


public class AttributeFactoryTest {
    @Test
    public void testNoNullObjectsReturned(){
        for (StudentAttributeType type : StudentAttributeType.values()){
            Attribute attribute = StudentAttributeFactory.getByType(type);
            assertFalse(attribute instanceof NullAttribute);
        }
    }

    @Test(expected = InvalidDatabaseOperationException.class)
    public void testInvalidAttributeNamesRaiseInvalidDatabaseOperationException(){
        String attributeName = "RandomName";
        Attribute attribute = StudentAttributeFactory.getByAttributeName(attributeName);
    }

    @Test
    public void testThatAllValidNamesCreateValidAttributes(){
        for (StudentAttributeType type : StudentAttributeType.values()){
            String attributeName = type.name();
            Attribute attribute = StudentAttributeFactory.getByAttributeName(attributeName);
            assertFalse(attribute instanceof NullAttribute);
        }
    }
}
