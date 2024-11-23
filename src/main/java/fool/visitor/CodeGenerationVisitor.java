package fool.visitor;

import java.util.Map;

import fool.ast.AssignmentStatement;
import fool.ast.BinaryExpression;
import fool.ast.ClassDeclaration;
import fool.ast.ConstantExpression;
import fool.ast.Expression;
import fool.ast.IfElseStatement;
import fool.ast.IfStatement;
import fool.ast.Method;
import fool.ast.MethodCall;
import fool.ast.Node;
import fool.ast.ReturnStatement;
import fool.ast.Statement;
import fool.ast.UnaryExpression;
import fool.ast.VariableDeclaration;
import fool.ast.VariableExpression;
import fool.ast.WhileStatement;

public class CodeGenerationVisitor implements Visitor {

    private CodeContext context;
    private StringBuilder code;

    public CodeGenerationVisitor() {
        this.context = new CodeContext();
        this.code = new StringBuilder();
    }

    public String getCode() {
        return code.toString();
    }

    /**
     * Visit a class declaration node.
     */
    @Override
    public void visit(ClassDeclaration node) {
        for (Method method : node.getMethods()) {
            method.accept(this);
        }
        processLabels();
    }

    @Override
    public void visit(VariableDeclaration node) { /* Not needed */ }

    /**
     * Visit a method node.
     */
    @Override
    public void visit(Method node) {
        // Label
        appendCode("\n" + node.getName() + ":");

        // Arguments
        for (VariableDeclaration arg : node.getArguments()) {
            appendCode("param " + arg.getName());
        }

        // Statements
        for (Statement stmt : node.getStatements()) {
            stmt.accept(this);
        }
    }

    /**
     * Visit an assignment statement node.
     */
    @Override
    public void visit(AssignmentStatement node) {
        Expression expressionToAssign = node.getExpression();
        expressionToAssign.accept(this);
        String toAppend = String.format("%s = %s", node.getIdentifier(), context.getNodeTemp(expressionToAssign));
        appendCode(toAppend);
    }

    /**
     * Visit a if statement node.
     */
    @Override
    public void visit(IfStatement node) {
        Expression condition = node.getCondition();
        condition.accept(this);
        
        Statement then = node.getThen();
        String thenLabel = context.getSubtreeLabel(then, "then");

        String toAppend = String.format("ifTrue %s goto %s", context.getNodeTemp(condition), thenLabel);
        appendCode(toAppend);
    }

    /**
     * Visit a if else statement node.
     */
    @Override
    public void visit(IfElseStatement node) {
        Expression condition = node.getCondition();
        condition.accept(this);
        
        Statement then = node.getThen();
        String thenLabel = context.getSubtreeLabel(then, "then");

        Statement otherwise = node.getOtherwise();
        String elseLabel = context.getSubtreeLabel(otherwise, "else");

        String toAppend = String.format("ifTrue %s goto %s", context.getNodeTemp(condition), thenLabel);
        appendCode(toAppend);
        toAppend = String.format("ifFalse %s goto %s", context.getNodeTemp(condition), elseLabel);
        appendCode(toAppend);
    }

    /**
     * Visit a while statement node.
     */
    @Override
    public void visit(WhileStatement node) {
        Expression condition = node.getCondition();
        condition.accept(this);

        Statement body = node.getBody();
        String bodyLabel = context.getSubtreeLabel(body, "body");

        String toAppend = String.format("while %s goto %s", context.getNodeTemp(condition), bodyLabel);
        appendCode(toAppend);
    }

    /**
     * Visit a return statement node.
     */
    @Override
    public void visit(ReturnStatement node) {
        Expression exp = node.getExpression();
        exp.accept(this);
        appendCode(String.format("return %s", context.getNodeTemp(exp)));
    }

    /**
     * Visit a method call expression node.
     */
    @Override
    public void visit(MethodCall node) {
        for (Expression arg : node.getArguments()) {
            arg.accept(this);
        }
        appendCode(context.getNodeTemp(node) + " = call " + node.getMethodName());
    }

    /**
     * Visit a method call statement node.
     */
    @Override
    public void visit(ConstantExpression node) { /* Not needed */ }

    @Override
    public void visit(VariableExpression node) { /* Not needed */ }

    /**
     * Visit a method call statement node.
     */
    @Override
    public void visit(UnaryExpression node) {
        Expression expressionToAssign = node.getExpression();
        expressionToAssign.accept(this);
        String toAppend = String.format("%s = %s %s", context.getNodeTemp(node), node.getOperator(), context.getNodeTemp(expressionToAssign));
        appendCode(toAppend);
    }

    /**
     * Visit a method call statement node.
     */
    @Override
    public void visit(BinaryExpression node) {
        Expression left = node.getLeft();
        Expression right = node.getRight();
        left.accept(this);
        right.accept(this);
        String toAppend = String.format("%s = %s %s %s", context.getNodeTemp(node), context.getNodeTemp(left), node.getOperator(), context.getNodeTemp(right));
        appendCode(toAppend);
    }

    /**
     * Append code to the code buffer.
     *
     * @param codeToAppend The code to append.
     */
    private void appendCode(String codeToAppend) {
        code.append(codeToAppend).append("\n");
    }

    /**
     * Process all labels in the labels generating code for each one.
     */
    private void processLabels() {
        for (Map.Entry<String, Node> entry : context.getSubtrees().entrySet()) {
            appendCode("\n" + entry.getKey() + ":");
            entry.getValue().accept(this);
        }
    }
}
