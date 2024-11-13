package fool;

public class CodeGenerationVisitor implements Visitor {

    private CodeContext context;
    IntermediateCode code;

    public CodeGenerationVisitor() {
        this.context = new CodeContext();
        this.code = new IntermediateCode();
    }

    public IntermediateCode getCode() {
        return code;
    }

    /**
     * Visit a class declaration node.
     */
    @Override
    public void visit(ClassDeclaration node) {
        for (Method method : node.getMethods()) {
            method.accept(this);
        }
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

        code.append(sb.toString());
    }

}
