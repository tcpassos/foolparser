package fool;

public class ReturnStatement extends Statement {
    private Expression expression;
    private String result;

    public ReturnStatement(Expression expression) {
        this.expression = expression;
        this.result = expression.getResult();
    }

    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder();
        String exp = expression.generate();
        if (!exp.isEmpty()) {
            sb.append(exp);
        }
        return sb.toString();
    }

    @Override
    public String getResult() {
        return result;
    }
}