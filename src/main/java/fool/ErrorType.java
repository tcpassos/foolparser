package fool;

public class ErrorType extends Type {
    @Override
    public boolean isCompatible(Type other) {
        return false;
    }

    @Override
    public String toString() {
        return "error";
    }
}