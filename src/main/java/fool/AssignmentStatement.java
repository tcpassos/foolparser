package fool;

public class AssignmentStatement extends Statement {
    private String identifier;
    private Expression expression;

    public AssignmentStatement(String identifier, Expression expression) {
        this.identifier = identifier;
        this.expression = expression;
    }

    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder();
        String exp = expression.generate();
        if (!exp.isEmpty()) {
            sb.append(exp).append(System.lineSeparator());
        }
        sb.append(String.format("%s = %s", identifier, expression.result));
        return sb.toString();
    }
}