package fool;

public enum Type {
    VOID("void"),
    INT("int"),
    BOOL("bool");

    private final String name;

    Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
