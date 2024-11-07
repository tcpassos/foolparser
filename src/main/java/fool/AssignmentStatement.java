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
        sb.append(expression.generate())
          .append(";")
          .append(System.lineSeparator())
          .append(String.format("%s = %s", identifier, expression.result));
        return sb.toString();
    }
}