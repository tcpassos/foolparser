package fool;

public class BinaryExpression extends Expression {

    private final String operator;
    private final Expression left;
    private final Expression right;
    private String result;

    public BinaryExpression(String operator, Expression left, Expression right, String result) {
        this.operator = operator;
        this.left = left;
        this.right = right;
        this.result = result;
    }

    @Override
    public String generate() {
        return String.format("%s = %s %s %s", getResult(), left.getResult(), operator, right.getResult());
    }

    @Override
    public String getResult() {
        return result;
    }
}
