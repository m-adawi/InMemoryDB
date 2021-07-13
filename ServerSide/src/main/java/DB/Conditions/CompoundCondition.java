package DB.Conditions;

public abstract class CompoundCondition implements Condition {
    protected Condition[] operands;

    public CompoundCondition(Condition... operands) {
        this.operands = operands;
    }
}
