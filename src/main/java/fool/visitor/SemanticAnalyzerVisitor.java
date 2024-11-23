package fool.visitor;

import java.util.ArrayList;
import java.util.List;

import fool.SemanticException;
import fool.ast.AssignmentStatement;
import fool.ast.BinaryExpression;
import fool.ast.ClassDeclaration;
import fool.ast.ConstantExpression;
import fool.ast.Expression;
import fool.ast.IfElseStatement;
import fool.ast.IfStatement;
import fool.ast.Method;
import fool.ast.MethodCall;
import fool.ast.Operator;
import fool.ast.ReturnStatement;
import fool.ast.Statement;
import fool.ast.UnaryExpression;
import fool.ast.VariableDeclaration;
import fool.ast.VariableExpression;
import fool.ast.WhileStatement;
import fool.symbol.MethodSymbolInfo;
import fool.symbol.ParameterSymbolInfo;
import fool.symbol.SymbolInfo;
import fool.symbol.SymbolTable;
import fool.symbol.VariableSymbolInfo;
import fool.type.BoolType;
import fool.type.ErrorType;
import fool.type.IntType;
import fool.type.Type;

public class SemanticAnalyzerVisitor implements Visitor {
    private SymbolTable globalScope;
    private SymbolTable currentScope;
    private List<String> errors;
    private Type currentMethodReturnType;

    public SemanticAnalyzerVisitor() {
        this.globalScope = new SymbolTable();
        this.currentScope = globalScope;
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

    private void enterScope(SymbolTable newScope) {
        currentScope = newScope;
    }

    private void exitScope() {
        currentScope = currentScope.getParent();
    }

    public String getSerializedSymbolTable() {
        return globalScope.serialize();
    }    

    @Override
    public void visit(ClassDeclaration node) {
        node.getFields().forEach(f -> f.accept(this));
        node.getMethods().forEach(m -> m.accept(this));
    }

    @Override
    public void visit(VariableDeclaration node) {
        // Declara a variável
        VariableSymbolInfo varSymbol = new VariableSymbolInfo(node.getName(), node.getType());
        try {
            currentScope.declare(varSymbol);
        } catch (SemanticException e) {
            reportError("Variable '" + node.getName() + "': " + e.getMessage());
        }
    }

    @Override
    public void visit(Method node) {
        // Declara o método
        List<ParameterSymbolInfo> params = node.getArguments()
                                               .stream()
                                               .map(p -> new ParameterSymbolInfo(p.getName(), p.getType()))
                                               .toList();
        MethodSymbolInfo methodSymbol = new MethodSymbolInfo(node.getName(), node.getReturnType(), params);
        try {
            currentScope.declare(methodSymbol);
        } catch (SemanticException e) {
            reportError("Method " + node.getName() + ": " + e.getMessage());
        }

        // Cria um escopo filho para o método
        enterScope(currentScope.createChildScope());
        currentMethodReturnType = node.getReturnType();

        // Declara os parâmetros
        for (VariableDeclaration param : node.getArguments()) {
            VariableSymbolInfo paramSymbol = new VariableSymbolInfo(param.getName(), param.getType());
            try {
                currentScope.declare(paramSymbol);
            } catch (SemanticException e) {
                reportError("Parameter '" + param.getName() + "': " + e.getMessage());
            }
        }

        for (Statement stmt : node.getStatements()) {
            stmt.accept(this);
        }

        exitScope();
        currentMethodReturnType = null;
    }

    @Override
    public void visit(AssignmentStatement node) {
        // Verifica se a variável foi declarada
        SymbolInfo varSymbol = currentScope.lookup(node.getIdentifier());
        if (varSymbol == null) {
            reportError("Variable '" + node.getIdentifier() + "' not declared.");
            return;
        }
        if (!(varSymbol instanceof VariableSymbolInfo)) {
            reportError("Symbol '" + node.getIdentifier() + "' is not a variable.");
            return;
        }

        // Visita a expressão
        node.getExpression().accept(this);
        Type exprType = node.getExpression().getType();

        // Verifica compatibilidade de tipos
        if (!varSymbol.getType().isCompatible(exprType)) {
            reportError("Type mismatch in assignment to variable '" + node.getIdentifier() + "'. Expected " + varSymbol.getType() + ", found " + exprType + ".");
        }
    }

    @Override
    public void visit(IfStatement node) {
        // Visita a condição
        node.getCondition().accept(this);
        Type conditionType = node.getCondition().getType();

        // A condição deve ser booleana
        if (!(conditionType instanceof BoolType)) {
            reportError("Condition in 'if' statement must be of type bool.");
        }

        // Visita o ramo 'then'
        node.getThen().accept(this);
    }

    @Override
    public void visit(IfElseStatement node) {
        // Visita a condição
        node.getCondition().accept(this);
        Type conditionType = node.getCondition().getType();

        // A condição deve ser booleana
        if (!(conditionType instanceof BoolType)) {
            reportError("Condition in 'if-else' statement must be of type bool.");
        }

        // Visita os ramos 'then' e 'else'
        node.getThen().accept(this);
        node.getOtherwise().accept(this);
    }

    @Override
    public void visit(WhileStatement node) {
        // Visita a condição
        node.getCondition().accept(this);
        Type conditionType = node.getCondition().getType();

        // A condição deve ser booleana
        if (!(conditionType instanceof BoolType)) {
            reportError("Condition in 'while' statement must be of type bool.");
        }

        // Visita o corpo
        node.getBody().accept(this);
    }

    @Override
    public void visit(ReturnStatement node) {
        if (currentMethodReturnType == null) {
            reportError("Return statement outside of a method.");
            return;
        }

        // Visita a expressão
        node.getExpression().accept(this);
        Type exprType = node.getExpression().getType();

        // Verifica compatibilidade de tipos
        if (!currentMethodReturnType.isCompatible(exprType)) {
            reportError("Type mismatch in return statement. Expected " + currentMethodReturnType + ", found " + exprType + ".");
        }
    }

    @Override
    public void visit(MethodCall node) {
        // Verifica se o método foi declarado
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

        // Verifica o número de argumentos
        if (parameters.size() != arguments.size()) {
            reportError("Method '" + node.getMethodName() + "' expects " + parameters.size() + " arguments, but got " + arguments.size() + ".");
        } else {
            // Verifica os tipos dos argumentos
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
        // Verifica se a variável foi declarada
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
        // Visita a expressão
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
        // Visita as expressões esquerda e direita
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
