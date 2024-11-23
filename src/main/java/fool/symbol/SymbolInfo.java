package fool.symbol;

import fool.type.Type;

public abstract class SymbolInfo implements SymbolTableEntry {
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

    @Override
    public String toString() {
        return String.format("Symbol(name='%s', type=%s)", name, type);
    }
    
}