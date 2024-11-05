package fool;

public class AssignmentStatement extends Statement {
    private String identifier;
    private String expression;

    public AssignmentStatement(String identifier, String expression) {
        this.identifier = identifier;
        this.expression = expression;
    }

    @Override
    public String generate() {
        return String.format("%s = %s", identifier, expression);
    }
}