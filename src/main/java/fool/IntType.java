package fool;

public class IntType extends Type {
    @Override
    public boolean isCompatible(Type other) {
        return other instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }
}