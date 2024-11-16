package fool;

public class ConstantExpression implements Expression {
    private final String value;

    public ConstantExpression(String value) {
        this.value = value;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public String getValue() {
        return value;
    }
}
