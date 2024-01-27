package game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

	// Logging variable
	final static Logger log = LogManager.getLogger(Main.class.getName());
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try 
		{
			// Attempting to divide
            int result = 10/0;
            System.out.println("Result: " + result);
        } 
		catch (ArithmeticException e) 
		{
            log.debug(e);
        } 
		catch (Exception e) 
		{
            log.debug(e);
        } 
		finally 
		{
            System.out.println("This block is always executed.");
        }
	}

}
