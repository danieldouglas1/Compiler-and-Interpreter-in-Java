/**
 * Created by hordur on 19/02/15.
 */
import java.util.Scanner;

public class Lexer {
    String codeInput = "";
    int codeInputLength = 0;
    int currI = 0; // Current index in string

    Lexer() {
        codeInput = readInput();
        codeInputLength = codeInput.length();
    }

    private String readInput() {
        String input = "";
        Scanner stdIn = new Scanner(System.in);

        while (stdIn.hasNextLine()) {
            input += stdIn.nextLine();
        }
        return input.replaceAll("", "\\s");
    }

    /*
    * Function called by the Parser class, returns the
    * next token, returns error token if it discovers
    * a Syntax Error.
     */
    public Token nextToken() {
        String[] currLex = new String[2];
        String[] nextLex = new String[2];
        char nxL = codeInput.charAt(0);
        if (currI < codeInputLength) {
            currLex = currLexeme();
        }
        if (currI < codeInputLength) {
            nextLex = nextLexeme();
            nxL = nextLex[0].charAt(0);
        }
        String nxT = nextLex[1];

        if(currLex[1].equals("digit")) {
            return new Token(currLex[0], Token.TokenCode.INT);

        } else if (currLex[1].equals("print")) {
            if(nxT.equals("id")) {
                return new Token(currLex[0], Token.TokenCode.PRINT);
            } else {return new Token(currLex[0], Token.TokenCode.ERROR);}

        } else if (currLex[1].equals("end")) {
            return new Token(currLex[0], Token.TokenCode.END);

        } else if (currLex[1].equals("id")) {
            if (!(nxT.equals("digit") || nxT.equals("print") || nxT.equals("end") || nxT.equals("id") || nxL == '(')) {
                return new Token(currLex[0], Token.TokenCode.ID);
            } else {return new Token(currLex[0], Token.TokenCode.ERROR);}

        } else if (currLex[1].equals("symbol")) {
            char symbol = currLex[0].charAt(0);
            if (symbol == ';') {
                if(nxT.equals("print") || nxT.equals("end") || nxT.equals("id")) {
                    return new Token(currLex[0], Token.TokenCode.SEMICOL);
                } else {return new Token(currLex[0], Token.TokenCode.ERROR);}

            } else if (symbol == '=') {
                if(nxT.equals("digit") || nxT.equals("id") || nxL == '(') {
                    return new Token(currLex[0], Token.TokenCode.ASSIGN);
                } else {
                    return new Token(currLex[0], Token.TokenCode.ERROR);}

            } else if (symbol == '+') {
                if(nxT.equals("digit") || nxT.equals("id") || nxL == '(') {
                    return new Token(currLex[0], Token.TokenCode.PLUS);
                } else {return new Token(currLex[0], Token.TokenCode.ERROR);}

            } else if (symbol == '-') {
                if(nxT.equals("digit") || nxT.equals("id")  || nxL == '(') {
                    return new Token(currLex[0], Token.TokenCode.MINUS);
                } else {return new Token(currLex[0], Token.TokenCode.ERROR);}

            } else if (symbol == '*') {
                if(nxT.equals("digit") || nxT.equals("id") || nxL == '(') {
                    return new Token(currLex[0], Token.TokenCode.MULT);
                } else {return new Token(currLex[0], Token.TokenCode.ERROR);}

            } else if (symbol == '(') {
                if(nxT.equals("digit") || nxT.equals("id") || nxL == '(' ) {
                    return new Token(currLex[0], Token.TokenCode.LPAREN);
                } else {return new Token(currLex[0], Token.TokenCode.ERROR);}

            } else if (symbol == ')') {
                if(nxL == ';' || nxL == '+' || nxL == '-' || nxL == '*' || nxL == ')') {
                    return new Token(currLex[0], Token.TokenCode.RPAREN);
                } else {return new Token(currLex[0], Token.TokenCode.ERROR);}
            }
        }
        return new Token(currLex[0], Token.TokenCode.ERROR);
    }

    private String parseDigit() {
        String digits = "";
        char thisChar = codeInput.charAt(currI);
        while (Character.isDigit(thisChar)) {
            digits += thisChar;
            currI++;
            thisChar = codeInput.charAt(currI);
        }
        return digits;
    }

    private String parseLetter() {
        String letters = "";
        char thisChar = codeInput.charAt(currI);
        while (Character.isLetter(thisChar)) {
            letters += thisChar;
            currI++;
            if (letters.equals("print") || letters.equals("end")) {
                return letters;
            }
            thisChar = codeInput.charAt(currI);
        }
        return letters;
    }

    private String[] currLexeme() {
        String temp = "";
        String[] lexeme = new String[2];
        char thisChar = codeInput.charAt(currI);
        if(Character.isDigit(thisChar)) {
            temp = parseDigit();
            lexeme[0] = temp;
            lexeme[1] = "digit";
            return lexeme;
        } else if (Character.isLetter(thisChar)) {
            temp = parseLetter();
            lexeme[0] = temp;
            if (temp.equals("print")) {
                lexeme[1] = "print";
            } else if (temp.equals("end")) {
                lexeme[1] = "end";
            } else {
                lexeme[1] = "id";
            }
            return lexeme;

        } else {
            temp += thisChar;
            lexeme[0] = temp;
            lexeme[1] = "symbol";
            currI++;
            return lexeme;
        }
    }

    private String[] nextLexeme() {
        String temp = "";
        int restoreIndex = currI;
        String[] lexeme = new String[2];
        char thisChar = codeInput.charAt(currI);
        if(Character.isDigit(thisChar)) {
            temp = parseDigit();
            lexeme[0] = temp;
            lexeme[1] = "digit";
            currI = restoreIndex;
            return lexeme;
        } else if (Character.isLetter(thisChar)) {
            temp = parseLetter();
            lexeme[0] = temp;
            if (temp.equals("print")) {
                lexeme[1] = "print";
            } else if (temp.equals("end")) {
                lexeme[1] = "end";
            } else {
                lexeme[1] = "id";
            }
            currI = restoreIndex;
            return lexeme;

        } else {
            temp += thisChar;
            lexeme[0] = temp;
            lexeme[1] = "symbol";
            currI++;
            currI = restoreIndex;
            return lexeme;
        }
    }
}