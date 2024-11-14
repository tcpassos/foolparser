package fool;

public class UnaryExpression extends Expression {
    private final String operator;
    private final Expression expression;
    private final String result;

    public UnaryExpression(String operator, Expression expression, String result) {
        this.operator = operator;
        this.expression = expression;
        this.result = result;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public String getOperator() {
        return operator;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public String getResult() {
        return result;
    }

}
