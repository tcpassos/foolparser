package fool;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import fool.ast.ClassDeclaration;
import fool.visitor.CodeGenerationVisitor;
import fool.visitor.SemanticAnalyzerVisitor;
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

            // Write symbol table to file
            String symbolTableStr = semanticAnalyzer.getSerializedSymbolTable();
            try (FileWriter writer = new FileWriter("symbol_table.txt")) {
                writer.write(symbolTableStr);
            } catch (IOException e) {
                System.err.println("Error writing symbol table to file: " + e.getMessage());
            }

            // Code generation
            CodeGenerationVisitor codeGenerator = new CodeGenerationVisitor();
            ast.accept(codeGenerator);

            // Write code to file
            try (FileWriter writer = new FileWriter("output.txt")) {
                writer.write(codeGenerator.getCode());
            } catch (IOException e) {
                System.err.println("Error writing code to file: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}