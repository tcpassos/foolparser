package fool.ast;

import java.util.List;

import fool.type.Type;
import fool.visitor.Visitor;

public class MethodCall implements Expression, Statement {
    private final String methodName;
    private final List<Expression> arguments;
    private Type type;

    public MethodCall(String methodName, List<Expression> arguments) {
        this.methodName = methodName;
        this.arguments = arguments == null ? List.of() : arguments;
        this.type = null;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public String getMethodName() {
        return methodName;
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }
}