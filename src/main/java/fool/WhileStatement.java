package fool;

public class WhileStatement implements Statement {
    private final Expression condition;
    private Statement body;

    public WhileStatement(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public Expression getCondition() {
        return condition;
    }

    public Statement getBody() {
        return body;
    }
}