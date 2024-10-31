package fool;

import java.io.FileReader;

import java_cup.runtime.DefaultSymbolFactory;
import java_cup.runtime.SymbolFactory;

public class Main {
    public static void main(String[] args) {
        try {
            String resource = Lexer.class.getClassLoader().getResource("sample.fool").getFile();
            Lexer lexer = new Lexer(new FileReader(resource));
            SymbolFactory symbolFactory = new DefaultSymbolFactory();
            Parser parser = new Parser(lexer, symbolFactory);

            parser.parse();
            System.out.println("Parsing concluído com sucesso!");
            parser.getTacInstructions().forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}