package fool;

import java.util.ArrayList;
import java.util.List;

public class SemanticAnalyzerVisitor implements Visitor {
    private SymbolTable currentScope;
    private List<String> errors;
    private Type currentMethodReturnType;

    public SemanticAnalyzerVisitor() {
        this.currentScope = new SymbolTable();
        this.errors = new ArrayList<>();
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public void printErrors() {
        errors.forEach(System.err::println);
    }

    private void reportError(String message) {
        errors.add(message);
    }

    private void enterScope() {
        currentScope = new SymbolTable(currentScope);
    }

    private void exitScope() {
        currentScope = currentScope.getParent();
    }

    // Visitor methods

    @Override
    public void visit(ClassDeclaration node) {
        // Enter class scope
        enterScope();

        // Declare fields
        node.getFields().forEach(f -> f.accept(this));

        // Declare methods
        for (Method method : node.getMethods()) {
            List<ParameterSymbolInfo> params = new ArrayList<>();
            for (VariableDeclaration param : method.getArguments()) {
                params.add(new ParameterSymbolInfo(param.getName(), param.getType()));
            }
            MethodSymbolInfo methodSymbol = new MethodSymbolInfo(method.getName(), method.getReturnType(), params);
            try {
                currentScope.declare(methodSymbol);
            } catch (SemanticException e) {
                reportError("Method " + method.getName() + ": " + e.getMessage());
            }
        }

        // Visit methods
        for (Method method : node.getMethods()) {
            method.accept(this);
        }

        // Exit class scope
        exitScope();
    }

    @Override
    public void visit(VariableDeclaration node) {
        // Declare variable
        VariableSymbolInfo varSymbol = new VariableSymbolInfo(node.getName(), node.getType());
        try {
            currentScope.declare(varSymbol);
        } catch (SemanticException e) {
            reportError("Variable '" + node.getName() + "': " + e.getMessage());
        }
    }

    @Override
    public void visit(Method node) {
        // Enter method scope
        enterScope();
        currentMethodReturnType = node.getReturnType();

        // Declare parameters
        for (VariableDeclaration param : node.getArguments()) {
            VariableSymbolInfo paramSymbol = new VariableSymbolInfo(param.getName(), param.getType());
            try {
                currentScope.declare(paramSymbol);
            } catch (SemanticException e) {
                reportError("Parameter '" + param.getName() + "': " + e.getMessage());
            }
        }

        // Visit statements
        for (Statement stmt : node.getStatements()) {
            stmt.accept(this);
        }

        // Exit method scope
        currentMethodReturnType = null;
        exitScope();
    }

    @Override
    public void visit(AssignmentStatement node) {
        // Check if variable is declared
        SymbolInfo varSymbol = currentScope.lookup(node.getIdentifier());
        if (varSymbol == null) {
            reportError("Variable '" + node.getIdentifier() + "' not declared.");
            return;
        }
        if (!(varSymbol instanceof VariableSymbolInfo)) {
            reportError("Symbol '" + node.getIdentifier() + "' is not a variable.");
            return;
        }

        // Visit expression
        node.getExpression().accept(this);
        Type exprType = node.getExpression().getType();

        // Check type compatibility
        if (!varSymbol.getType().isCompatible(exprType)) {
            reportError("Type mismatch in assignment to variable '" + node.getIdentifier() + "'. Expected " + varSymbol.getType() + ", found " + exprType + ".");
        }
    }

    @Override
    public void visit(IfStatement node) {
        // Visit condition
        node.getCondition().accept(this);
        Type conditionType = node.getCondition().getType();

        // Condition must be boolean
        if (!(conditionType instanceof BoolType)) {
            reportError("Condition in 'if' statement must be of type bool.");
        }

        // Visit then branch
        enterScope();
        node.getThen().accept(this);
        exitScope();
    }

    @Override
    public void visit(IfElseStatement node) {
        // Visit condition
        node.getCondition().accept(this);
        Type conditionType = node.getCondition().getType();

        // Condition must be boolean
        if (!(conditionType instanceof BoolType)) {
            reportError("Condition in 'if-else' statement must be of type bool.");
        }

        // Visit then branch
        enterScope();
        node.getThen().accept(this);
        exitScope();

        // Visit else branch
        enterScope();
        node.getOtherwise().accept(this);
        exitScope();
    }

    @Override
    public void visit(WhileStatement node) {
        // Visit condition
        node.getCondition().accept(this);
        Type conditionType = node.getCondition().getType();

        // Condition must be boolean
        if (!(conditionType instanceof BoolType)) {
            reportError("Condition in 'while' statement must be of type bool.");
        }

        // Visit body
        enterScope();
        node.getBody().accept(this);
        exitScope();
    }

    @Override
    public void visit(ReturnStatement node) {
        if (currentMethodReturnType == null) {
            reportError("Return statement outside of a method.");
            return;
        }

        // Visit expression
        node.getExpression().accept(this);
        Type exprType = node.getExpression().getType();

        // Check type compatibility
        if (!currentMethodReturnType.isCompatible(exprType)) {
            reportError("Type mismatch in return statement. Expected " + currentMethodReturnType + ", found " + exprType + ".");
        }
    }

    @Override
    public void visit(MethodCall node) {
        // Check if method is declared
        SymbolInfo methodSymbol = currentScope.lookup(node.getMethodName());
        if (methodSymbol == null) {
            reportError("Method '" + node.getMethodName() + "' not declared.");
            node.setType(new ErrorType());
            return;
        }
        if (!(methodSymbol instanceof MethodSymbolInfo)) {
            reportError("Symbol '" + node.getMethodName() + "' is not a method.");
            node.setType(new ErrorType());
            return;
        }

        MethodSymbolInfo method = (MethodSymbolInfo) methodSymbol;
        List<ParameterSymbolInfo> parameters = method.getParameters();
        List<Expression> arguments = node.getArguments();

        // Check number of arguments
        if (parameters.size() != arguments.size()) {
            reportError("Method '" + node.getMethodName() + "' expects " + parameters.size() + " arguments, but got " + arguments.size() + ".");
        } else {
            // Check types of arguments
            for (int i = 0; i < parameters.size(); i++) {
                arguments.get(i).accept(this);
                Type argType = arguments.get(i).getType();
                Type paramType = parameters.get(i).getType();
                if (!paramType.isCompatible(argType)) {
                    reportError("Argument " + (i + 1) + " of method '" + node.getMethodName() + "' expects type " + paramType + ", but got " + argType + ".");
                }
            }
        }

        node.setType(method.getType());
    }

    @Override
    public void visit(ConstantExpression node) {
        Object value = node.getValue();
        if (value instanceof Integer) {
            node.setType(new IntType());
        } else if (value instanceof Boolean) {
            node.setType(new BoolType());
        } else {
            reportError("Unknown constant type: " + value);
            node.setType(new ErrorType());
        }
    }  

    @Override
    public void visit(VariableExpression node) {
        // Check if variable is declared
        SymbolInfo varSymbol = currentScope.lookup(node.getName());
        if (varSymbol == null) {
            reportError("Variable '" + node.getName() + "' not declared.");
            node.setType(new ErrorType());
            return;
        }
        if (!(varSymbol instanceof VariableSymbolInfo)) {
            reportError("Symbol '" + node.getName() + "' is not a variable.");
            node.setType(new ErrorType());
            return;
        }
        node.setType(varSymbol.getType());
    }

    @Override
    public void visit(UnaryExpression node) {
        // Visit expression
        node.getExpression().accept(this);
        Type exprType = node.getExpression().getType();

        Operator op = node.getOperator();

        switch (op) {
            case NOT:
                if (!(exprType instanceof BoolType)) {
                    reportError("Operator 'not' requires a boolean operand.");
                }
                node.setType(new BoolType());
                break;

            default:
                reportError("Unknown unary operator '" + op + "'.");
                node.setType(new ErrorType());
                break;
        }
    }

    @Override
    public void visit(BinaryExpression node) {
        // Visit left and right expressions
        node.getLeft().accept(this);
        node.getRight().accept(this);

        Type leftType = node.getLeft().getType();
        Type rightType = node.getRight().getType();

        Operator op = node.getOperator();

        switch (op) {
            case PLUS:
            case TIMES:
                if (!(leftType instanceof IntType) || !(rightType instanceof IntType)) {
                    reportError("Operator '" + op + "' requires integer operands.");
                }
                node.setType(new IntType());
                break;

            case AND:
            case OR:
                if (!(leftType instanceof BoolType) || !(rightType instanceof BoolType)) {
                    reportError("Operator '" + op + "' requires boolean operands.");
                }
                node.setType(new BoolType());
                break;

            case EQ:
            case LT:
            case GT:
                if (!leftType.isCompatible(rightType)) {
                    reportError("Operator '" + op + "' requires operands of the same type.");
                }
                node.setType(new BoolType());
                break;

            default:
                reportError("Unknown operator '" + op + "'.");
                node.setType(new ErrorType());
                break;
        }
    }
}
