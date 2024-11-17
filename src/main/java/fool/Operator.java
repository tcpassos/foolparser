package fool;

public enum Operator {

    PLUS("+"),
    TIMES("*"),
    EQ("=="),
    LT("<"),
    GT(">"),
    AND("and"),
    OR("or"),
    NOT("not");
    
    private final String symbol;
    
    Operator(String symbol) {
        this.symbol = symbol;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public static Operator fromString(String symbol) {
        for (Operator op : Operator.values()) {
        if (op.symbol.equals(symbol)) {
            return op;
        }
        }
        return null;
    }

    public boolean isUnary() {
        return this == NOT;
    }

}
