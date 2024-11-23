package fool.ast;

import fool.type.Type;

public interface Expression extends Node {
    Type getType();
    void setType(Type type);
}