// C:\JFLEX\bin\jflex .\src\main\java\fool\lexer.flex
// java -jar .\lib\java-cup-11b.jar -parser Parser -symbols sym .\src\main\java\fool\parser.cup
package fool;

import java.io.FileReader;
import java_cup.runtime.DefaultSymbolFactory;
import java_cup.runtime.SymbolFactory;

public class Main {
    public static void main(String[] args) {
        try {
            // Carregar o arquivo de entrada .fool
            String resource = Lexer.class.getClassLoader().getResource("sample.fool").getFile();

            // Criar uma instância do Lexer
            Lexer lexer = new Lexer(new FileReader(resource));

            // Criar uma instância do SymbolFactory
            SymbolFactory symbolFactory = new DefaultSymbolFactory();

            // Criar uma instância do Parser, passando o Lexer e o SymbolFactory
            Parser parser = new Parser(lexer, symbolFactory);

            // Executar o parser
            parser.parse();

            System.out.println("Parsing concluído com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}