package fool;

import java.io.FileReader;

import java_cup.runtime.DefaultSymbolFactory;
import java_cup.runtime.SymbolFactory;

public class Main {
    public static void main(String[] args) {
        try {
            String resource = Lexer.class.getClassLoader().getResource("sample.fool").getFile();
            Lexer lexer = new Lexer(new FileReader(resource));
            @SuppressWarnings("deprecation")
            SymbolFactory symbolFactory = new DefaultSymbolFactory();
            
            // Syntax analysis and AST construction
            Parser parser = new Parser(lexer, symbolFactory);
            ClassDeclaration ast = (ClassDeclaration) parser.parse().value;

            // Semantic analysis
            SemanticAnalyzerVisitor semanticAnalyzer = new SemanticAnalyzerVisitor();
            ast.accept(semanticAnalyzer);
            if (semanticAnalyzer.hasErrors()) {
                semanticAnalyzer.printErrors();
                return;
            }

            // Code generation
            CodeGenerationVisitor codeGenerator = new CodeGenerationVisitor();
            ast.accept(codeGenerator);
            System.out.println(codeGenerator.getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}