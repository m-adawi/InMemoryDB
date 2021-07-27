package DB.Commands;

import DB.Conditions.Condition;
import DB.Conditions.ConditionFactory;
import DB.DatabaseKey;
import DB.Attributes.IntegerDatabaseKey;
import DB.Attributes.StudentAttributeType;
import DB.Attributes.StudentID;
import org.gibello.zql.ZExp;
import org.gibello.zql.ZExpression;

import java.util.Vector;

public abstract class SpecializedConditionalCommandGenerator extends SpecializedCommandGenerator {
    protected Condition getCondition(ZExp exp) {
        return ConditionFactory.getInstance().getByZExp(exp);
    }
}
