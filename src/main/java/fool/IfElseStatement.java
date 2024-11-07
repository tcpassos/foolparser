package fool;

public class IfElseStatement extends Statement {
    private Expression condition;
    private String thenLabel;
    private String elseLabel;

    public IfElseStatement(Expression condition, String thenLabel, String elseLabel) {
        this.condition = condition;
        this.thenLabel = thenLabel;
        this.elseLabel = elseLabel;
    }

    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder();
        sb.append("ifTrue ").append(condition.result).append(" goto ").append(thenLabel).append(System.lineSeparator());
        sb.append("ifFalse ").append(condition.result).append(" goto ").append(elseLabel);
        return sb.toString();
    }
}