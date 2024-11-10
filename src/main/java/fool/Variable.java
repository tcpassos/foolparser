package fool;

public class Variable implements CodeFragment {
    private final Type type;
    private final String name;

    public Variable(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getResult() {
        return name;
    }

    @Override
    public String generate() {
        return "var " + name + " " + type;
    }
}
