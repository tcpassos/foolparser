package fool.ast;

import fool.type.Type;
import fool.visitor.Visitor;

public class UnaryExpression implements Expression {
    private final Operator operator;
    private final Expression expression;

    public UnaryExpression(Operator operator, Expression expression) {
        this.operator = operator;
        this.expression = expression;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public Operator getOperator() {
        return operator;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public Type getType() {
        return expression.getType();
    }

    @Override
    public void setType(Type type) {
        expression.setType(type);
    }
}
