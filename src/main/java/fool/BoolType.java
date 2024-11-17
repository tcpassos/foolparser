package fool;

public class BoolType extends Type {
    @Override
    public boolean isCompatible(Type other) {
        return other instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }
}