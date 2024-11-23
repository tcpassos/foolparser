package fool.ast;

import fool.type.BoolType;
import fool.type.IntType;
import fool.type.Type;
import fool.visitor.Visitor;

public class ConstantExpression implements Expression {
    private final Object value;
    private Type type;

    public ConstantExpression(Integer value) {
        this.value = value;
        this.type = new IntType();
    }

    public ConstantExpression(Boolean value) {
        this.value = value;
        this.type = new BoolType();
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public Object getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }
}