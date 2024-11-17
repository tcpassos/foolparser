package fool;

public interface Expression extends Node {
    Type getType();
    void setType(Type type);
}