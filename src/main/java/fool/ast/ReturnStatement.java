package fool.ast;

import fool.visitor.Visitor;

public class ReturnStatement implements Statement {
    private final Expression expression;

    public ReturnStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public Expression getExpression() {
        return expression;
    }
}