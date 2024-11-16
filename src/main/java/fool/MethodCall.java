package fool;

import java.util.List;

public class MethodCall implements Expression, Statement {
    private final String methodName;
    private final List<Expression> arguments;

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public MethodCall(String methodName, List<Expression> arguments) {
        this.methodName = methodName;
        this.arguments = arguments == null ? List.of() : arguments;
    }

    public String getMethodName() {
        return methodName;
    }

    public List<Expression> getArguments() {
        return arguments;
    }
}