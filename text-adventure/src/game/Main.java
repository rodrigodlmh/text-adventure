package game;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main 
{
	// 1.1 + 1.7 -- Private, nested class for any character methods
	private static class Actions
	{
		// 1.5 -- Able to access the methods without initializing Actions
		/**
		 * Add an item to your inventory
		 * @param item
		 */
		private static void Take(String item)
		{
			System.out.println("You took the " + item);
		}
		/**
		 * Use an item on something in the room
		 * @param item
		 */
		private static void Use(String item)
		{
			System.out.println("You used the " + item);
		}
		/**
		 * Interact with an object in the room
		 * @param object
		 */
		private static void Interact(String object)
		{
			System.out.println("You interacted with the " + object);
		}
	}
	
	// 1.6 -- Unchangeable logging variable
	final static Logger log = LogManager.getLogger(Main.class.getName());
	// Other fields
	private static String verb;
	private static String noun;
	private static boolean end = false;
	// Get user input
	private static Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) 
	{
		try 
		{
			// Run game until it's beaten
			while(end == false)
			{
				// Process every command
				System.out.print("Command: ");
				String command = input.nextLine();
				// Validates blank input
				if(command.trim().equals(""))
				{
					// Prompt new input
					System.out.println("Input cannot be blank");
				}
				else
				{
					Parser(command);
				}
			}
			
			// Will display when game is beaten (loop is broken)
			System.out.println("You win!");
        }
		catch (Exception e) 
		{
			// Log exceptions
            log.debug(e);
        } 
		finally 
		{
            System.out.println("This block is always executed");
        }
	}

	/**
	 * Splits the entered command into its verb and noun
	 * @param input
	 */
	private static void Parser(String input)
	{
		// Split the input into an array
		String newInput = input.replaceAll("\s+", " ").trim();
		String[] strings = newInput.split(" ");
		
		// Ensure the input only contains a verb and a noun
		if(strings.length == 2)
		{
			// Checks if verb and noun are valid for every command
			if(strings[0].toLowerCase().equals("take"))
			{
				Actions.Take(strings[1]);
			}
			else if(strings[0].toLowerCase().equals("use"))
			{
				Actions.Use(strings[1]);
			}
			else if(strings[0].toLowerCase().equals("interact"))
			{
				Actions.Interact(strings[1]);
			}
			else
			{
				// Prompt new input
				System.out.println("Are you sure whatever you're trying to do is worth it?");
			}
		}
		else
		{
			// Prompt new input
			System.out.println("Input must contain exactly two words");
		}
	}
}
