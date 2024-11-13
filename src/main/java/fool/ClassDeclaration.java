package fool;

import java.util.List;

public class ClassDeclaration implements Node {

    private final String name;
    private final List<Variable> fields;
    private final List<Method> methods;

    public ClassDeclaration(String name, List<Variable> fields, List<Method> methods) {
        this.name = name;
        this.fields = fields;
        this.methods = methods;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public String getName() {
        return name;
    }

    public List<Variable> getFields() {
        return fields;
    }

    public List<Method> getMethods() {
        return methods;
    }
}
