package game;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
            int result = 10/0;
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            System.err.println("Error: Division by zero is not allowed.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred.");
            e.printStackTrace();
        } finally {
            System.out.println("This block is always executed.");
        }
	}

}
