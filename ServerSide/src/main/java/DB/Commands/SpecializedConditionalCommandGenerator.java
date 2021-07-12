package DB.Commands;

import DB.DatabaseKey;
import DB.Attributes.IntegerDatabaseKey;
import DB.Attributes.StudentAttributeType;
import DB.Attributes.StudentID;
import org.gibello.zql.ZExp;
import org.gibello.zql.ZExpression;

import java.util.Vector;

public abstract class SpecializedConditionalCommandGenerator extends SpecializedCommandGenerator {
    protected boolean isOnSingleRecord(ZExp exp) {
        if(!(exp instanceof ZExpression))
            return false;
        ZExpression expression = (ZExpression) exp;
        if(!expression.getOperator().equals("="))
            return false;
        Vector operands = expression.getOperands();
        return isTheFirstOperandTheRecordPrimaryKey(operands);
    }

    private boolean isTheFirstOperandTheRecordPrimaryKey(Vector operands) {
        return operands.elementAt(0).toString().equalsIgnoreCase(StudentAttributeType.ID.name());
    }

    protected DatabaseKey getSingleRecordKey(ZExp exp) {
        String idStrVal = ((ZExpression) exp).getOperands().elementAt(1).toString();
        StudentID id = new StudentID(idStrVal);
        return new IntegerDatabaseKey(id.getValue());
    }
}
