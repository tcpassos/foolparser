package fool;

public class ConstantExpression extends Expression {
    private final String result;

    public ConstantExpression(String result) {
        this.result = result;
    }

    @Override
    public String generate() {
        return "";
    }

    @Override
    public String getResult() {
        return result;
    }

}
