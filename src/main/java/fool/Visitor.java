package fool;

public interface Visitor {
    void visit(ClassDeclaration node);
    void visit(Method node);
    void visit(AssignmentStatement node);
    void visit(IfStatement node);
    void visit(IfElseStatement node);
    void visit(WhileStatement node);
    void visit(ReturnStatement node);
    void visit(MethodCall node);
    void visit(ConstantExpression node);
    void visit(UnaryExpression node);
    void visit(BinaryExpression node);
}