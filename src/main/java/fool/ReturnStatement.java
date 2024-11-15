package fool;

public class ReturnStatement extends Statement {
    private final Expression expression;

    public ReturnStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public Expression getExpression() {
        return expression;
    }
}