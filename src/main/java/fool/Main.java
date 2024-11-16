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
            Parser parser = new Parser(lexer, symbolFactory);
            ClassDeclaration ast = (ClassDeclaration) parser.parse().value;

            CodeGenerationVisitor codeGenerator = new CodeGenerationVisitor();
            ast.accept(codeGenerator);
            System.out.println(codeGenerator.getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}