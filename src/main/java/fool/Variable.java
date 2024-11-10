package fool;

public class Variable implements CodeFragment {
    private String type;
    private String name;

    public Variable(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
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
