/**
 * Created by hordur on 19/02/15.
 */
public class Parser {
    private Lexer myLexer;
    private Token nextT;

    Parser(Lexer lexer) {
        myLexer = lexer;
    }

    /* Wrapper for nextToken
    *   - Throws error if Lexer returns ERROR token
    *   - Calls system exit if Lexer returns END token
    */
    private Token GetToken() {
        Token output = myLexer.nextToken();
        if (output.tCode == Token.TokenCode.ERROR) {
            System.out.println("Syntax error!");
            System.exit(0);
        } else if (output.tCode == Token.TokenCode.END) {
            System.exit(0);
        }
        return output;
    }

    /* The main method for the Parser
    * Calling Statements invokes a recursive function call
    * which ends when either an ERROR or END token is returned.
    * If an invalid sequence of tokens is detected the parse
    * returns a syntax error.
     */
    public void parse() {
        nextT = GetToken();
        Statements();
    }

    /*
    * Top down recursive parse for the language, the context-free
    * grammar G for L is:
    *
    * Statements -> Statement ; Statements | end
    * Statement -> id = Expr | print id
    * Expr- > Term | Term + Expr | Term – Expr
    * Term -> Factor | Factor * Term
    * Factor -> int | id | ( Expr )
    *
    * Writes corresponding compiled code to System out.
     */

    private void Statements() {
        if(nextT.tCode == Token.TokenCode.END)
            System.exit(0);
        else Statement();
    }

    private void Statement() {
        if(nextT.tCode == Token.TokenCode.ID) {
            writePush(nextT);
            nextT = GetToken();
            if (nextT.tCode == Token.TokenCode.ASSIGN) {
                nextT = GetToken();
                Expr();
                if (nextT.tCode == Token.TokenCode.SEMICOL) {
                    writeAssign();
                    nextT = GetToken();
                    Statements();
                } else error();
            } else error();
        } else if (nextT.tCode == Token.TokenCode.PRINT) {
            nextT = GetToken();
            if (nextT.tCode == Token.TokenCode.ID) {
                writePush(nextT);
                nextT = GetToken();
                if (nextT.tCode == Token.TokenCode.SEMICOL)
                    writePrint();
                    nextT = GetToken();
                Statements();
            } else error();
        } else if (nextT.tCode == Token.TokenCode.END) {
            System.exit(0);
        } else error();
    }

    private void Expr() {
        Term();
        if (nextT.tCode == Token.TokenCode.PLUS) {
            nextT = GetToken();
            Expr();
            writeAdd();
        } else if (nextT.tCode == Token.TokenCode.MINUS) {
            nextT = GetToken();
            Expr();
            writeSub();
        } else if (nextT.tCode == Token.TokenCode.SEMICOL) {
        } else if (nextT.tCode == Token.TokenCode.RPAREN) {
        } else if (nextT.tCode == Token.TokenCode.MULT) {
        } else error();
    }

    private void Term() {
        Factor();
        if (nextT.tCode == Token.TokenCode.MULT) {
            nextT = GetToken();
            Term();
            writeMult();
        } else if (nextT.tCode == Token.TokenCode.SEMICOL) {
        } else if (nextT.tCode == Token.TokenCode.PLUS) {
        } else if (nextT.tCode == Token.TokenCode.MINUS) {
        } else if (nextT.tCode == Token.TokenCode.RPAREN) {
        } else error();
    }

    private void Factor() {
        if(nextT.tCode == Token.TokenCode.INT) {
            writePush(nextT);
            nextT = GetToken();
        } else if (nextT.tCode == Token.TokenCode.ID) {
            writePush(nextT);
            nextT = GetToken();
        } else if (nextT.tCode == Token.TokenCode.LPAREN) {
            nextT = GetToken();
            Expr();
            if (nextT.tCode == Token.TokenCode.RPAREN)
                nextT = GetToken();
            else error();
        } else error();
    }

    /*
    * Helper function for calling an error in the parser.
     */
    private void error() {
        System.out.println("Syntax error!");
        System.exit(0);
    }

    /*
    * Series of helper functions for calling the println
    * function.
     */
    private void writePush(Token token) {
        System.out.println("PUSH "+token.lexeme);
    }

    private void writeAdd(){
        System.out.println("ADD");
    }

    private void writeSub(){
        System.out.println("SUB");
    }

    private void writeMult(){
        System.out.println("MULT");
    }

    private void writeAssign(){
        System.out.println("ASSIGN");
    }

    private void writePrint(){
        System.out.println("PRINT");
    }
}