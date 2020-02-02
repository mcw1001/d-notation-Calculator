/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package d20_calculator;

import java.util.Random;
import java.util.Stack;
/**
 * The evaluator will parse and evaluate basic arithmetic expressions, with added functionality for
 * D&D dice rolling
 * 
 * Adapted from GeeksforGeeks' Expression Evaluation 
 * https://www.geeksforgeeks.org/expression-evaluation/
 *      by user Ciphe
 * @author Michael
 *
 */
//@SuppressWarnings("unused")

public class Evaluator {
    /**
     * rollDie implemented without string parsing, passed ints instead
     * @param mult - number of times die is rolled
     * @param die - the type(number) of die to be rolled (4 - 20)
     * @return a random number, or total of random numbers
     */
    private static int rollDieInt(int mult, int die) {
        Random rand = new Random();
        if(mult <=1){
            //the first (and only) element is the die to roll once
            return rand.nextInt(die)+1;
        }else {
            //2 elements: the number of times to roll the die, and the die type (e.g. 2d20)
            int total = 0;
            for(int i=0;i<mult;i++) {
                total += rand.nextInt(die)+1;
            }
            return total;
        }
    }
    /**
     * Simple evaluation function. Does not account for floating point or big decimal numbers.
     * Can handle simple operations (generally those necessary for a D&D calculator)
     * @param expression
     * @return
     */
    public static int evaluate(String expression) {
        char[] chars = expression.toCharArray();

        Stack <Integer> numbers = new Stack<>();
        Stack <Character> operators = new Stack<>();
        Boolean isD = false;

        for(int i=0; i<chars.length;i++) {
            if(chars[i] == ' ') {
                continue; //skip the rest of the loop if the char is whitespace
            }
            //if character is a digit, convert to number (unless there's a 'd' operator)
            if(Character.isDigit(chars[i])) {
                //StringBuffer sbuf = new StringBuffer();
                StringBuilder sbuf = new StringBuilder();
                StringBuilder sbuf2 = new StringBuilder(); //secondary buffer in case of d20 operator)

                //if there's more than one consecutive digit
                while(i < chars.length && Character.isDigit(chars[i])) {
                    sbuf.append(chars[i++]);
                    if(i < chars.length && chars[i]=='d') { //detect a d20 die operator (such as 4d6 or 2d20, etc)
                        //sbuf.append(chars[i++]);
                        isD = true;
                        i++; //move past the d to process the next number
                        break;
                    }
                }
                //if a d20 operator d is detected in the word, do a secondary loop to get the other numbers
                while (isD && i < chars.length && Character.isDigit(chars[i])) {
                    sbuf2.append(chars[i++]);
                    if(i < chars.length && chars[i]=='d') { //in the case of any additional d's, something's wrong
                        i++;//simply skip over it (will likely return a -1 and fail, but w/e)
                    }
                }

                i--; //an important decrement to move back one after exiting the loop (believed to be what was causing problems in the original version)

                if(isD) {
                    isD = false; //reset isD for next processing
                    int mult = Integer.parseInt(sbuf.toString());
                    int type = Integer.parseInt(sbuf2.toString());
                    numbers.push(rollDieInt(mult,type));
                }else {
                    numbers.push(Integer.parseInt(sbuf.toString()));
                }
            }
            //push the opening brace to operators stack
            else if (chars[i] == '(') {
                operators.push(chars[i]);
            }
            //closing brace encountered, solve everything between this and closes opening brace
            else if (chars[i] == ')') {
                int check;
                while(operators.peek()!='(') {
                    check = operate(operators.pop(), numbers.pop(),numbers.pop());
                    //System.out.print(check);
                    numbers.push(check);
                }
                //once the loop is finished, remove the remaining '(' operator
                operators.pop();
            }

            //if the current character is an operator
            else if (chars[i] == '+' || chars[i] == '-' || chars[i] == '*' || chars[i] == '/') {
                //while this operator has less precedence than the operator at the top
                //of the operator stack, use the top of the stack to operate on two values from numbers
                while(!operators.empty() && hasPrecedence(operators.peek(),chars[i])) {
                    numbers.push(operate(operators.pop(), numbers.pop(),numbers.pop()));
                }
                //finally, push current operator onto operator stack
                operators.push(chars[i]);
            }
        }
        //for loop ends, expression completely parsed
        //operate on all remaining values
        while(!operators.empty()) {
            numbers.push(operate(operators.pop(), numbers.pop(),numbers.pop()));
        }
        //all that should remain in numbers is the final result, 
        return numbers.pop();
    }
    /*
     * Returns true if op1 is higher (or same) precedence than op2, false otherwise
     */
    public static Boolean hasPrecedence(char op1, char op2) {
        if (op1 == '(' || op1 == ')') {
            return false; 
        }else return !((op2 == '*' || op2 == '/') && (op1 == '+' || op1 == '-'));
    }

    //perform an arithmetic operation on integers a and b (given reverse order, due to being popped off of a stack)
    //   (e.g. if you pushed a and then b onto the stack, b has to be given first)
    //the operator decides the operation
    public static int operate(char operator, int b, int a) {
        switch(operator) {
        case '+':
            return a+b;
        case '-':
            return a-b;
        case '*':
            return a*b;
        case '/':
            if(b==0) {
                    throw new IllegalArgumentException("Divisor b cannot be 0");
            }else {
                    return a/b;
            }
        }
        return 0;
    }

}