package fool;

import java.util.List;

public class Method implements Node {
    private final Type returnType;
    private final String name;
    private final List<Variable> arguments;
    private final List<Statement> statements;

    public Method(Type returnType, String name, List<Variable> arguments, List<Statement> statements) {
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

    public List<Variable> getArguments() {
        return arguments;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public String getReturnHolder() {
        if (statements.isEmpty()) {
            return null;
        }
        if (statements.get(statements.size() - 1) instanceof ReturnStatement) {
            return ((ReturnStatement) statements.get(statements.size() - 1)).getResult();
        }
        return null;
    }
}
