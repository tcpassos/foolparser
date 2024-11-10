package fool;

import java.util.List;

public class Method implements CodeFragment {
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

    public String getResult() {
        if (statements.isEmpty()) {
            return null;
        }
        if (statements.get(statements.size() - 1) instanceof ReturnStatement) {
            return ((ReturnStatement) statements.get(statements.size() - 1)).getResult();
        }
        return null;
    }

    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder();
        
        // Label
        sb.append(name).append(":\n");

        // Arguments
        for (Variable arg : arguments) {
            sb.append("param ").append(arg.getName()).append("\n");
        }

        // Statements
        for (Statement stmt : statements) {
            sb.append(stmt.generate()).append("\n");
        }

        // Return
        String result = getResult();
        if (result != null) {
            sb.append("return ").append(result).append("\n");
        }

        return sb.toString();
    }
}
