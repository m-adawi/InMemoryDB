package DB.Conditions;

import DB.Commands.UnsupportedSQLStatementException;
import org.gibello.zql.ZExp;
import org.gibello.zql.ZExpression;

public class ConditionFactory {
    private static final ConditionFactory factory = new ConditionFactory();

    public ConditionFactory getInstance() {
        return factory;
    }

    private ConditionFactory() {
    }

    public Condition getByZExp(ZExp exp) {
        if(!(exp instanceof ZExpression))
            throw new UnsupportedSQLStatementException();
        return getByZExpression((ZExpression) exp);
    }

    private Condition getByZExpression(ZExpression exp) {
        String operator = exp.getOperator();
        String operand1 = exp.getOperand(0).toString();
        String operand2 = exp.getOperand(1).toString();

        switch (operator){
            case "=":
                return new EqualAttributeCondition(operand1, operand2);
            case "!=":
                return new InvertedCondition(new EqualAttributeCondition(operand1, operand2));
            case "<":
                return new LessThanAttributeCondition(operand1, operand2);
            case ">":
                return new GreaterThanAttributeCondition(operand1, operand2);
            case ">=":
                Condition equal = new EqualAttributeCondition(operand1, operand2);
                Condition greater = new GreaterThanAttributeCondition(operand1, operand2);
                return new OrCondition(equal, greater);
            case "<=":
                equal = new EqualAttributeCondition(operand1, operand2);
                Condition less = new LessThanAttributeCondition(operand1, operand2);
                return new OrCondition(equal, less);
            default:
                return generateComplexCondition(exp);
        }
    }

    private Condition generateComplexCondition(ZExpression exp) {
        Condition[] conditions = new Condition[exp.nbOperands()];
        for(int i = 0; i < conditions.length; i++) {
            conditions[i] = ConditionFactory.factory.getByZExp(exp.getOperand(i));
        }
        switch (exp.getOperator()) {
            case "AND":
                return new AndCondition(conditions);
            case "OR":
                return new OrCondition(conditions);
            default:
                throw new UnsupportedSQLStatementException();
        }
    }
}
