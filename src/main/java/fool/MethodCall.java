package fool;

import java.util.List;

public class MethodCall extends Expression {
    private final String methodName;
    private final List<Expression> arguments;
    private final String result;

    public MethodCall(String methodName, List<Expression> arguments, String result) {
        this.methodName = methodName;
        this.arguments = arguments == null ? List.of() : arguments;
        this.result = result;
    }

    public String getMethodName() {
        return methodName;
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder();

        for (Expression arg : arguments) {
            sb.append("param ").append(arg.getResult()).append("\n");
        }

        sb.append(result).append(" = call ").append(methodName);

        return sb.toString();
    }

    @Override
    public String getResult() {
        return result;
    }
}