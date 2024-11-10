package fool;

import java.util.List;

public class MethodCall extends Expression {
    private String methodName;
    private List<Expression> arguments;

    public MethodCall(String methodName, List<Expression> arguments, String result) {
        super(result);
        this.methodName = methodName;
        this.arguments = arguments == null ? List.of() : arguments;
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

        sb.append(getResult()).append(" = call ").append(methodName);

        return sb.toString();
    }
}