package fool.ast;

import java.util.List;

import fool.type.Type;
import fool.type.VoidType;
import fool.visitor.Visitor;

public class Method implements Node {
    private final Type returnType;
    private final String name;
    private final List<VariableDeclaration> arguments;
    private final List<Statement> statements;

    public Method(Type returnType, String name, List<VariableDeclaration> arguments, List<Statement> statements) {
        this.returnType = returnType;
        this.name = name;
        this.arguments = arguments == null ? List.of() : arguments;
        this.statements = statements;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public Type getReturnType() {
        return returnType;
    }

    public String getName() {
        return name;
    }

    public List<VariableDeclaration> getArguments() {
        return arguments;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public boolean isMain() {
        return name.equals("main") && arguments.size() == 0 && returnType instanceof VoidType;
    }
}
