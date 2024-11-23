package fool.ast;

import fool.type.Type;
import fool.visitor.Visitor;

public class VariableDeclaration implements Statement {
    private final Type type;
    private final String name;

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public VariableDeclaration(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
