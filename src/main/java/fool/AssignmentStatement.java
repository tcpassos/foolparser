package fool;

public class AssignmentStatement extends Statement {
    private String identifier;
    private Expression expression;

    public AssignmentStatement(String identifier, Expression expression) {
        this.identifier = identifier;
        this.expression = expression;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder();
        String exp = expression.generate();
        if (!exp.isEmpty()) {
            sb.append(exp).append(System.lineSeparator());
        }
        sb.append(String.format("%s = %s", identifier, expression.getResult()));
        return sb.toString();
    }

    @Override
    public String getResult() {
        return identifier;
    }
}