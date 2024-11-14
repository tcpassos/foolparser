package fool;

public class IfStatement extends Statement {
    private final Expression condition;
    private final Statement then;

    public IfStatement(Expression condition, Statement then) {
        this.condition = condition;
        this.then = then;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public Expression getCondition() {
        return condition;
    }

    public Statement getThen() {
        return then;
    }
}