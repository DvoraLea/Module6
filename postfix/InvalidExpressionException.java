package postfix;

public class InvalidExpressionException extends RuntimeException {
	public InvalidExpressionException() {
		System.out.println("Run time exception thrown");
	}
}
