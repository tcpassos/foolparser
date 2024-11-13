package fool;

public interface Visitor {
    void visit(ClassDeclaration node);
    void visit(Method node);
}