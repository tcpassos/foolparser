package fool;

public class ConstantExpression extends Expression {
    private final String result;

    public ConstantExpression(String result) {
        this.result = result;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public String getResult() {
        return result;
    }
}
