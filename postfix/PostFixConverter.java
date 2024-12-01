package postfix;

import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

public class PostFixConverter {

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
        StringTokenizer infix = new StringTokenizer(infixExpression, "+-*/()^ ", true);
        // "a + b" --> "a + b".split(" + ") --> ["a", " ", "+", " ", "b"]
        Stack<String> opStack = new Stack<>();
        StringBuilder postFixExp = new StringBuilder(); // Contains PostFix expression
        final String BLANK = " ";

        while (infix.hasMoreElements()) {
            String token = infix.nextToken().trim(); // " + ".trim() --> "+"
            if (token.isEmpty()) continue;

            System.out.println("Token: " + token); // Debug print

            if (token.matches("[\\+-/\\*()\\^]")) { // If token is an operator or a parenthesis
                switch (token.charAt(0)) { 
                    case '(':
                        opStack.push(token);
                        break;
                    case ')':
                        while (!opStack.isEmpty() && !"(".equals(opStack.peek())) {
                            postFixExp.append(BLANK).append(opStack.pop()); 
                        }
                        if (opStack.isEmpty() || !"(".equals(opStack.pop())) { 
                            throw new InvalidExpressionException();
                        }
                        break;
                    case '+':
                    case '-':
                    case '*':
                    case '/':
                    case '^':
                        while (!opStack.isEmpty() && precedence(opStack.peek()) >= precedence(token)) {
                            postFixExp.append(BLANK).append(opStack.pop());
                        }
                        opStack.push(token);
                        break;
                }
            } else { // If token is a number
                postFixExp.append(BLANK).append(token);
            }

            System.out.println("Postfix so far: " + postFixExp.toString()); // Debug print
            System.out.println("Operator stack: " + opStack); // Debug print
        }

        while (!opStack.isEmpty()) { // Pop what is left in the operator stack
            String topToken = opStack.pop();
            if (!"(".equals(topToken)) { 
                postFixExp.append(BLANK).append(topToken);
            } else { // Found an unmatched ( parenthesis
                throw new InvalidExpressionException();
            }
        }

        return postFixExp.toString().trim();
    }

    private static int precedence(String op) {
        switch (op) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "^":
                return 3;
            case "(":
                return 0;
            default:
                throw new IllegalArgumentException("Invalid operator: " + op);
        }
    }

    public static double evaluatePostFix(String postFixExpression) throws InvalidExpressionException {
        StringTokenizer postFix = new StringTokenizer(postFixExpression);
        Stack<Double> stack = new Stack<>();

        while (postFix.hasMoreElements()) {
            String token = postFix.nextToken();

            System.out.println("Evaluating token: " + token); // Debug print

            if (token.matches("[\\+-/\\*\\^]")) { // If token is an operator
                if (stack.size() < 2) throw new InvalidExpressionException();
                double b = stack.pop();
                double a = stack.pop();
                switch (token.charAt(0)) {
                    case '+':
                        stack.push(a + b);
                        break;
                    case '-':
                        stack.push(a - b);
                        break;
                    case '*':
                        stack.push(a * b);
                        break;
                    case '/':
                        stack.push(a / b);
                        break;
                    case '^':
                        stack.push(Math.pow(a, b));
                        break;
                }
            } else { // If token is a number
                stack.push(Double.parseDouble(token));
            }

            System.out.println("Stack after evaluating: " + stack); // Debug print
        }

        if (stack.size() != 1) throw new InvalidExpressionException();
        return stack.pop();
    }

}