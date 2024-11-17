package fool;

public abstract class SymbolInfo {
    private String name;
    private Type type;

    public SymbolInfo(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }
}