/**
 * Created by hordur on 19/02/15.
 */
public class Compiler {
    public static void main(String[] args) {
        Lexer myLexer = new Lexer();
        Parser myParser = new Parser(myLexer);
        myParser.parse();
    }
}
