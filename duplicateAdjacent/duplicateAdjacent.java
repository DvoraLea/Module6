package duplicateAdjacent;
import java.util.Scanner;
import java.util.Stack;


public class duplicateAdjacent {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter a string: ");
        String word = input.nextLine(); // Read input string
        
        Stack<Character> s = new Stack<>(); // Stack for the characters

        // Loop through each character in the string
        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i); // Get the current character
            
            // Check if the stack is not empty and the top character matches the current character
            if (!s.isEmpty() && s.peek() == letter) {
                s.pop(); // Remove the top character if it's a duplicate
            } else {
                s.push(letter); // Add the character to the stack
            }
        }

      
        StringBuilder result = new StringBuilder();
        while (!s.isEmpty()) {
            result.insert(0, s.pop()); // Insert characters in reverse order
        }

        // Print the final result
        System.out.println("Output: " + result.toString());
    }
}
	
	
	

