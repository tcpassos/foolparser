package fool;

public class UnaryExpression extends Expression {
    private final String operator;
    private final Expression expression;
    private String result;

    public UnaryExpression(String operator, Expression expression, String result) {
        this.operator = operator;
        this.expression = expression;
    }

    @Override
    public String generate() {
        return String.format("%s = %s %s", result, operator, expression.getResult());
    }

    @Override
    public String getResult() {
        return result;
    }

}
