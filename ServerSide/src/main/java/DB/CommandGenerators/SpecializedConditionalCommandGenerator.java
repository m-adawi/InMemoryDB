package DB.CommandGenerators;

import DB.CommandGenerators.SpecializedCommandGenerator;
import DB.Conditions.Condition;
import DB.Conditions.ConditionFactory;
import org.gibello.zql.ZExp;

public abstract class SpecializedConditionalCommandGenerator extends SpecializedCommandGenerator {
    protected Condition getCondition(ZExp exp) {
        return ConditionFactory.getInstance().getByZExp(exp);
    }
}
