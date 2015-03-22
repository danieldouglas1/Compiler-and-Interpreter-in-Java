/**
 * Created by hordur on 19/02/15.
 */
public class Token {
    String lexeme;
    public enum TokenCode {
        ID, ASSIGN, SEMICOL, INT, PLUS, MINUS, MULT, LPAREN, RPAREN, PRINT, END, ERROR
    }
    TokenCode tCode;

    Token(String m_lexeme, TokenCode m_tCode) {
        lexeme = m_lexeme;
        tCode = m_tCode;
    }
}
