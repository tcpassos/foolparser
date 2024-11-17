package fool;

public class VariableSymbolInfo extends SymbolInfo {
    public VariableSymbolInfo(String name, Type type) {
        super(name, type);
    }

    @Override
    public String toString() {
        return String.format("Variable(name='%s', type=%s)", getName(), getType());
    }
    
}