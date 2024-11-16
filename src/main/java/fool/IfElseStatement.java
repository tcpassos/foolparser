package fool;

public class IfElseStatement implements Statement {
    private final Expression condition;
    private final Statement then;
    private final Statement otherwise;

    public IfElseStatement(Expression condition, Statement then, Statement otherwise) {
        this.condition = condition;
        this.then = then;
        this.otherwise = otherwise;
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

    public Statement getOtherwise() {
        return otherwise;
    }
}