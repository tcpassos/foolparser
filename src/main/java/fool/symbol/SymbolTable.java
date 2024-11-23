package fool.symbol;

import java.util.ArrayList;
import java.util.List;

import fool.SemanticException;

public class SymbolTable implements SymbolTableEntry {
    private SymbolTable parent;
    private List<SymbolTableEntry> entries;

    public SymbolTable() {
        this(null);
    }

    public SymbolTable(SymbolTable parent) {
        this.parent = parent;
        this.entries = new ArrayList<>();
    }

    public void declare(SymbolInfo symbol) throws SemanticException {
        // Verifica duplicatas
        for (SymbolTableEntry entry : entries) {
            if (entry instanceof SymbolInfo) {
                SymbolInfo existingSymbol = (SymbolInfo) entry;
                if (existingSymbol.getName().equals(symbol.getName())) {
                    throw new SemanticException("Symbol '" + symbol.getName() + "' already declared in this scope.");
                }
            }
        }
        entries.add(symbol);
    }

    public SymbolInfo lookup(String name) {
        // Procura neste escopo
        for (SymbolTableEntry entry : entries) {
            if (entry instanceof SymbolInfo) {
                SymbolInfo symbol = (SymbolInfo) entry;
                if (symbol.getName().equals(name)) {
                    return symbol;
                }
            }
        }
        // Procura nos escopos ancestrais
        if (parent != null) {
            return parent.lookup(name);
        }
        return null;
    }

    public SymbolTable getParent() {
        return parent;
    }

    public SymbolTable createChildScope() {
        SymbolTable childScope = new SymbolTable(this);
        entries.add(childScope);
        return childScope;
    }

    public String serialize() {
        StringBuilder sb = new StringBuilder();
        serializeHelper(sb, 0);
        return sb.toString();
    }

    private void serializeHelper(StringBuilder sb, int indentLevel) {
        if (entries.isEmpty()) {
            return;
        }
        String indent = "  ".repeat(indentLevel);

        for (SymbolTableEntry entry : entries) {
            if (entry instanceof SymbolInfo) {
                SymbolInfo symbol = (SymbolInfo) entry;
                sb.append(indent).append("  ").append(symbol).append("\n");
            } else if (entry instanceof SymbolTable) {
                SymbolTable childScope = (SymbolTable) entry;
                childScope.serializeHelper(sb, indentLevel + 1);
            }
        }
    }
}
