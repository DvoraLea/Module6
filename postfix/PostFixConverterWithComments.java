package postfix;

import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

public class PostFixConverterWithComments {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an algebraic expression:");
        String infixExpression = scanner.nextLine();

        try {
            String postFixExpression = PostFix(infixExpression);
            System.out.println("Postfix Expression: " + postFixExpression);
            double result = evaluatePostFix(postFixExpression);
            System.out.println("Result: " + result);
        } catch (InvalidExpressionException e) {
            System.out.println("Invalid Expression: " + e.getMessage());
        }
    }

    public static String PostFix(String infixExpression) throws InvalidExpressionException {
    	//true flag, the delimiters are included as tokens
    	StringTokenizer infix = new StringTokenizer(infixExpression, "+-*/() ", true);
        Stack<String> opStack = new Stack<>();
        StringBuilder postFixExp = new StringBuilder(); // Contains PostFix expression
        final String BLANK = " ";

        // Process each token in the infix expression
        while (infix.hasMoreElements()) {
            String token = infix.nextToken().trim();
            if (token.isEmpty()) continue;

            System.out.println("Token: " + token); // Debug print

            // If token is an operator or a parenthesis
            if (token.matches("[\\+-/\\*()]")) {
                switch (token.charAt(0)) {
                    case '(':  // Left parenthesis - push it onto the operator stack
                        opStack.push(token);
                        break;
                    case ')':  // Right parenthesis - pop operators until '(' is found
                        // Pop operators until '(' is found
                        while (!opStack.isEmpty() && !"(".equals(opStack.peek())) {
                            postFixExp.append(BLANK).append(opStack.pop());
                        }
                        // Check for an unmatched parenthesis
                        //remove the '(' parenthesis in the pop
                        if (opStack.isEmpty() || !"(".equals(opStack.pop())) {
                            throw new InvalidExpressionException();
                        }
                        break;
                    case '+':
                    case '-':
                    case '*':
                    case '/':  // Operator - pop higher or equal precedence operators
                        // Pop operators with higher or equal precedence and add them to postfix expression
                        while (!opStack.isEmpty() && precedence(opStack.peek()) >= precedence(token)) {
                            postFixExp.append(BLANK).append(opStack.pop());
                        }
                        // Push the current operator onto the stack
                        opStack.push(token);
                        break;
                }
            } else { // If token is a number, add it directly to the postfix expression
                postFixExp.append(BLANK).append(token);
            }

            System.out.println("Postfix so far: " + postFixExp.toString()); // Debug print
            System.out.println("Operator stack: " + opStack); // Debug print
        }

        // Pop any remaining operators from the stack
        while (!opStack.isEmpty()) { // Pop what is left in the operator stack
            String topToken = opStack.pop();
            if (!"(".equals(topToken)) {
                postFixExp.append(BLANK).append(topToken);
            } else { // Found an unmatched ( parenthesis
                throw new InvalidExpressionException();
            }
        }

        return postFixExp.toString().trim();  // Return the completed postfix expression
    }

    // Function to return precedence of operators
    private static int precedence(String op) {
        switch (op) {
            case "+":
            case "-":
                return 1;  // Lower precedence for + and -
            case "*":
            case "/":
                return 2;  // Higher precedence for * and /
            case "(":
                return 0;  // Parentheses have the lowest precedence
            default:
                throw new IllegalArgumentException("Invalid operator: " + op);
        }
    }

    // Function to evaluate the postfix expression
    public static double evaluatePostFix(String postFixExpression) throws InvalidExpressionException {
        
    	StringTokenizer postFix = new StringTokenizer(postFixExpression);
        Stack<Double> stack = new Stack<>();

        // Process each token in the postfix expression
        while (postFix.hasMoreElements()) {
            String token = postFix.nextToken();

            System.out.println("Evaluating token: " + token); // Debug print

            // If token is an operator, pop two operands and perform the operation
            if (token.matches("[\\+-/\\*]")) {
                if (stack.size() < 2) throw new InvalidExpressionException();  // If not enough operands, throw exception
                double b = stack.pop();
                double a = stack.pop();
                switch (token.charAt(0)) {
                    case '+':
                        stack.push(a + b);  // Addition
                        break;
                    case '-':
                        stack.push(a - b);  // Subtraction
                        break;
                    case '*':
                        stack.push(a * b);  // Multiplication
                        break;
                    case '/':
                        stack.push(a / b);  // Division
                        break;
                }
            } else { // If token is a number, push it onto the stack
                stack.push(Double.parseDouble(token));
            }

            System.out.println("Stack after evaluating: " + stack); // Debug print
        }

        // After processing all tokens, there should be exactly one value
        //        left in the stack - the answer
        if (stack.size() != 1) throw new InvalidExpressionException();
        return stack.pop();  // Return the final result
    }

}
