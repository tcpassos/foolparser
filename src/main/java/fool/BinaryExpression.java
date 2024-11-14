package fool;

public class BinaryExpression extends Expression {

    private final String operator;
    private final Expression left;
    private final Expression right;
    private final String result;

    public BinaryExpression(String operator, Expression left, Expression right, String result) {
        this.operator = operator;
        this.left = left;
        this.right = right;
        this.result = result;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public String getOperator() {
        return operator;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    public String getResult() {
        return result;
    }
}
