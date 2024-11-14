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

    public String getIdentifier() {
        return identifier;
    }

    public Expression getExpression() {
        return expression;
    }
}