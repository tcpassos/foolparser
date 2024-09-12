package fool;

public class Main {
    public static void main(String[] args) {
        String resource = Lexer.class.getClassLoader().getResource("sample.fool").getFile();
        Lexer.main(new String[]{resource});
    }
}