package fool;

import java.util.Map;

public class CodeGenerationVisitor implements Visitor {

    private CodeContext context;
    private StringBuilder code;
    private Map<String, Node> labels;

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

    /**
     * Visit a method node.
     */
    @Override
    public void visit(Method node) {
        StringBuilder sb = new StringBuilder();
        
        // Label
        String methodLabel = context.newLabel(node.getName());
        sb.append(methodLabel).append(":\n");

        // Arguments
        for (Variable arg : node.getArguments()) {
            sb.append("param ").append(arg.getName()).append("\n");
        }

        // Statements
        for (Statement stmt : node.getStatements()) {
            sb.append(stmt.generate()).append("\n");
        }

        // Return
        String result = node.getReturnHolder();
        if (result != null) {
            sb.append("return ").append(result).append("\n");
        }

        appendCode(sb.toString());
    }

    /**
     * Visit an assignment statement node.
     */
    @Override
    public void visit(AssignmentStatement node) {
        Expression expressionToAssign = node.getExpression();
        expressionToAssign.accept(this);
        String toAppend = String.format("%s = %s", node.getIdentifier(), expressionToAssign.getResult());
        appendCode(toAppend);
    }

    @Override
    public void visit(IfStatement node) {
        Expression condition = node.getCondition();
        condition.accept(this);
        
        Statement then = node.getThen();
        String thenLabel = context.newLabel("then");
        appendLabel(thenLabel, then);

        String toAppend = String.format("ifTrue %s goto %s", condition.getResult(), thenLabel);
        appendCode(toAppend);
    }

    @Override
    public void visit(IfElseStatement node) {
        Expression condition = node.getCondition();
        condition.accept(this);
        
        Statement then = node.getThen();
        String thenLabel = context.newLabel("then");
        appendLabel(thenLabel, then);

        Statement otherwise = node.getOtherwise();
        String elseLabel = context.newLabel("else");
        appendLabel(elseLabel, otherwise);

        String toAppend = String.format("ifTrue %s goto %s", condition.getResult(), thenLabel);
        appendCode(toAppend);
        toAppend = String.format("ifFalse %s goto %s", condition.getResult(), elseLabel);
        appendCode(toAppend);
    }

    @Override
    public void visit(WhileStatement node) {
        Expression condition = node.getCondition();
        condition.accept(this);

        Statement body = node.getBody();
        String bodyLabel = context.newLabel("body");
        appendLabel(bodyLabel, body);

        String toAppend = String.format("while %s goto %s", condition.getResult(), bodyLabel);
        appendCode(toAppend);
    }

    @Override
    public void visit(ReturnStatement node) {
        
    }

    /**
     * Visit a method call statement node.
     */
    @Override
    public void visit(ConstantExpression node) { /* Not needed */ }

    /**
     * Visit a method call statement node.
     */
    @Override
    public void visit(UnaryExpression node) {
        Expression expressionToAssign = node.getExpression();
        expressionToAssign.accept(this);
        String toAppend = String.format("%s = %s %s", node.getResult(), node.getOperator(), expressionToAssign.getResult());
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
        String toAppend = String.format("%s = %s %s %s", node.getResult(), left.getResult(), node.getOperator(), right.getResult());
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
     * Append a label to the code buffer and associate it with a node.
     *
     * @param label The label name.
     * @param node The node to associate with the label.
     */
    private void appendLabel(String label, Node node) {
        if (labels.containsKey(label)) {
            throw new IllegalArgumentException("Label already exists: " + label);
        }
        labels.put(label, node);
    }

    /**
     * Process all labels in the labels generating code for each one.
     */
    private void processLabels() {
        code.append("// LABELS:\n");
        for (Map.Entry<String, Node> entry : labels.entrySet()) {
            appendCode(entry.getKey() + ":");
            entry.getValue().accept(this);
        }
        labels.clear();
    }
}
