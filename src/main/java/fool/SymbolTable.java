package fool;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private Map<String, SymbolInfo> symbols;
    private SymbolTable parent;

    public SymbolTable() {
        this(null);
    }

    public SymbolTable(SymbolTable parent) {
        this.symbols = new HashMap<>();
        this.parent = parent;
    }

    public void declare(SymbolInfo symbol) throws SemanticException {
        if (symbols.containsKey(symbol.getName())) {
            throw new SemanticException("Symbol '" + symbol.getName() + "' already declared in this scope.");
        }
        symbols.put(symbol.getName(), symbol);
    }

    public SymbolInfo lookup(String name) {
        SymbolInfo symbol = symbols.get(name);
        if (symbol != null) {
            return symbol;
        } else if (parent != null) {
            return parent.lookup(name);
        }
        return null;
    }

    public SymbolTable getParent() {
        return parent;
    }
}
