package fool;

public class UnaryExpression implements Expression {
    private final String operator;
    private final Expression expression;

    public UnaryExpression(String operator, Expression expression) {
        this.operator = operator;
        this.expression = expression;
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
}
