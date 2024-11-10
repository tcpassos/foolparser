package fool;

public class WhileStatement extends Statement {
    private Expression condition;
    private String bodyLabel;

    public WhileStatement(Expression condition, String bodyLabel) {
        this.condition = condition;
        this.bodyLabel = bodyLabel;
    }

    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder();
        sb.append("while ").append(condition.getResult()).append(" goto ").append(bodyLabel);
        return sb.toString();
    }

    @Override
    public String getResult() {
        return null;
    }
}