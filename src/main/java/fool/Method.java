package fool;

import java.util.List;

public class Method implements CodeFragment {
    private String returnType;
    private String name;
    private List<String> arguments;
    private List<Statement> statements;

    public Method(String returnType, String name, List<String> arguments, List<Statement> statements) {
        this.returnType = returnType;
        this.name = name;
        this.arguments = arguments == null ? List.of() : arguments;
        this.statements = statements;
    }

    public String getReturnType() {
        return returnType;
    }

    public String getName() {
        return name;
    }

    public List<String> getArguments() {
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
        for (String arg : arguments) {
            sb.append("param ").append(arg).append("\n");
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
