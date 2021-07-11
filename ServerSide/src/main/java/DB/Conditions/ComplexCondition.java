package DB.Conditions;

public abstract class ComplexCondition implements Condition {
    protected Condition[] operands;

    public ComplexCondition(Condition... operands) {
        this.operands = operands;
    }
}
