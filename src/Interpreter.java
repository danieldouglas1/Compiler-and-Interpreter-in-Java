/**
 * Created by hordur on 19/02/15.
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class Interpreter {
    private static Stack<String> myStack = new Stack<String>();
    private static Map<String, Integer> myMap = new HashMap<String, Integer>();
    private static String[] biteCode = new String[100];
    private static int bcLength = 0;

    public static void main(String[] args) {
        biteCode = readInput();
        decode();
    }

    private static String[] readInput() {
        String[] input = new String[100];
        int index = 0;
        Scanner stdIn = new Scanner(System.in);

        while (stdIn.hasNextLine()) {
            input[index] = stdIn.nextLine();
            index++;
        }
        bcLength = index;
        return input;
    }

    /*
    * Uses Stack and Map to take the "Bytecode" complied code
    * from standard input and interpret it into runtime
    * actions.
     */
    private static void decode() {
        String valueInput = "";
        int value1, value2;
        String pop1, pop2;
        for (int i = 0; i < bcLength; i++) {
            if (biteCode[i].contains("PUSH")) {
                for (int j = 5; j < biteCode[i].length(); j++) {
                    valueInput += biteCode[i].charAt(j);
                }
                myStack.push(valueInput);
                valueInput = "";
            } else if (biteCode[i].contains("ADD")) {
                pop1 = myStack.pop();
                pop2 = myStack.pop();
                if (pop1.length() > 0 && pop2.length() > 0) {
                    value1 = parseValue(pop1);
                    value2 = parseValue(pop2);
                    myStack.push(Integer.toString(value1 + value2));
                } else error(biteCode[i]);
            } else if (biteCode[i].contains("SUB")) {
                pop1 = myStack.pop();
                pop2 = myStack.pop();
                if (pop1.length() > 0 && pop2.length() > 0) {
                    value1 = parseValue(pop1);
                    value2 = parseValue(pop2);
                    myStack.push(Integer.toString(value2 - value1));
                } else error(biteCode[i]);
            } else if (biteCode[i].contains("MULT")) {
                pop1 = myStack.pop();
                pop2 = myStack.pop();
                if (pop1.length() > 0 && pop2.length() > 0) {
                    value1 = parseValue(pop1);
                    value2 = parseValue(pop2);
                    myStack.push(Integer.toString(value1 * value2));
                } else error(biteCode[i]);
            } else if (biteCode[i].contains("ASSIGN")) {
                pop1 = myStack.pop();
                pop2 = myStack.pop();
                if (pop1.length() > 0 && pop2.length() > 0) {
                    value1 = parseValue(pop1);
                    myMap.put(pop2,value1);
                } else error(biteCode[i]);
            } else if (biteCode[i].contains("PRINT")) {
                pop1 = myStack.pop();
                if (pop1.length() > 0) {
                    value1 = parseValue(pop1);
                    System.out.println(value1);
                } else error(biteCode[i]);
            } else error(biteCode[i]);
        }
    }

    /*
    * Helper function for the decode function.
     */
    private static void error(String nameOfOperator) {
        System.out.println("Error for operator: "+nameOfOperator);
        System.exit(0);
    }

    private static int parseValue(String pop) {
        if(Character.isDigit(pop.charAt(0))) {
            return Integer.parseInt(pop);
        } else if (pop.charAt(0) == '-') {
            return Integer.parseInt(pop);
        } else {
            return myMap.get(pop);
        }
    }
}