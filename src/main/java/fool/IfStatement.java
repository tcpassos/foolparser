package fool;

public class IfStatement extends Statement {
    private Expression condition;
    private String thenLabel;

    public IfStatement(Expression condition, String thenLabel) {
        this.condition = condition;
        this.thenLabel = thenLabel;
    }

    @Override
    public String generate() {
        return "ifTrue " + condition.getResult() + " goto " + thenLabel;
    }

    @Override
    public String getResult() {
        return null;
    }
}