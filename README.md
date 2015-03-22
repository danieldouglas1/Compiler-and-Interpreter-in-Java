# Compiler-and-Interpreter-in-Java
Simple compiler and interpreter written in Java.

Compiler that compiles a string from the small programming language L to simple stack-based intermediate code (called S) for an abstract machine. A string from the language L consists of a list of statements, where each statement is either an assignment statement or a print statement.


The context-free grammar G for L is:

Statements -> Statement ; Statements | end Statement -> id = Expr | print id

Expr- > Term | Term + Expr | Term – Expr

Term -> Factor | Factor * Term

Factor -> int | id | ( Expr )


The non-terminals are: Statements (starting symbol), Statement, Expr, Term, and Factor. Terminals (tokens) are: ; end id = print + - * int ( )


S, our stack-based intermediate code, consists of the following commands:

PUSH // pushes the operand op onto the stack

ADD // pops the two top elements from the stack, adds their values // and pushes the result back onto the stack

SUB // pops the two top elements from the stack, subtracts the first // value retrieved from the second value, and pushes the result back onto the stack

MULT // pops the two top elements from the stack, multiplies their // values and pushes the result back onto the stack

ASSIGN // pops the two top elements from the stack, assigns the first // element (a value) to the second element (a variable)

PRINT // prints the value currently on top of the stack
