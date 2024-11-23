package fool.type;

public class VoidType extends Type {
    @Override
    public boolean isCompatible(Type other) {
        return other instanceof VoidType;
    }

    @Override
    public String toString() {
        return "void";
    }
}