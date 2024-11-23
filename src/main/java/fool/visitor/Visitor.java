package fool.visitor;

import fool.ast.AssignmentStatement;
import fool.ast.BinaryExpression;
import fool.ast.ClassDeclaration;
import fool.ast.ConstantExpression;
import fool.ast.IfElseStatement;
import fool.ast.IfStatement;
import fool.ast.Method;
import fool.ast.MethodCall;
import fool.ast.ReturnStatement;
import fool.ast.UnaryExpression;
import fool.ast.VariableDeclaration;
import fool.ast.VariableExpression;
import fool.ast.WhileStatement;

public interface Visitor {
    void visit(ClassDeclaration node);
    void visit(VariableDeclaration node);
    void visit(Method node);
    void visit(AssignmentStatement node);
    void visit(IfStatement node);
    void visit(IfElseStatement node);
    void visit(WhileStatement node);
    void visit(ReturnStatement node);
    void visit(MethodCall node);
    void visit(ConstantExpression node);
    void visit(VariableExpression node);
    void visit(UnaryExpression node);
    void visit(BinaryExpression node);
}