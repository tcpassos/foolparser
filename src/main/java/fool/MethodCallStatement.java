package fool;

import java.util.List;

public class MethodCallStatement extends Statement {
    private String methodName;
    private List<Expression> arguments;
    private String result;

    public MethodCallStatement(String methodName, List<Expression> arguments, String result) {
        this.methodName = methodName;
        this.arguments = arguments == null ? List.of() : arguments;
        this.result = result;
    }

    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder();

        for (Expression arg : arguments) {
            sb.append("param ").append(arg.result).append("\n");
        }

        sb.append(result).append(" = call ").append(methodName);

        return sb.toString();
    }
}