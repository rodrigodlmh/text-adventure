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
		/**
		 * Move in specified direction
		 * @param direction
		 */
		private static void Move(String direction)
		{
			for(int i = 0; i < room[0].length; i++)
			{
				for(int j = 0; j < room[1].length; j++)
				{
					if(room[i][j] == 1)
					{
						if(direction.equals("north"))
						{
							if(i == 0)
							{
								System.out.println("Walking into a wall isn't advisable");
							}
							else
							{
								room[i][j] = 0;
								room[i - 1][j] = 1;
								
								i = room[0].length;
								j = room[1].length;
							}
						}
						else if(direction.equals("south"))
						{
							if(i == 2)
							{
								System.out.println("Walking into a wall isn't advisable");
							}
							else
							{
								room[i][j] = 0;
								room[i + 1][j] = 1;
								
								i = room[0].length;
								j = room[1].length;
							}
						}
						else if(direction.equals("east"))
						{
							if(j == 2)
							{
								System.out.println("Walking into a wall isn't advisable");
							}
							else
							{
								room[i][j] = 0;
								room[i][j + 1] = 1;
								
								i = room[0].length;
								j = room[1].length;
							}
						}
						else if(direction.equals("west"))
						{
							if(j == 0)
							{
								System.out.println("Walking into a wall isn't advisable");
							}
							else
							{
								room[i][j] = 0;
								room[i][j - 1] = 1;
								
								i = room[0].length;
								j = room[1].length;
							}
						}
					}
				}
			}
		}
		/**
		 * Look in specified direction
		 * @param direction
		 */
		private static void Look(String direction)
		{
			for(int i = 0; i < room[0].length; i++)
			{
				for(int j = 0; j < room[1].length; j++)
				{
					if(room[i][j] == 1)
					{
						if(direction.equals("north"))
						{
							if(i == 0)
							{
								System.out.println("You can stare at walls another time");
							}
							else
							{
								if(items[i - 1][j] == 0)
								{
									System.out.println("You see nothing");
								}
								else
								{
									System.out.println("You see a " + items[i - 1][j]);
								}
								
								i = room[0].length;
								j = room[1].length;
							}
						}
						else if(direction.equals("south"))
						{
							if(i == 2)
							{
								System.out.println("You can stare at walls another time");
							}
							else
							{
								if(items[i + 1][j] == 0)
								{
									System.out.println("You see nothing");
								}
								else
								{
									System.out.println("You see a " + items[i + 1][j]);
								}
								
								i = room[0].length;
								j = room[1].length;
							}
						}
						else if(direction.equals("east"))
						{
							if(j == 2)
							{
								System.out.println("You can stare at walls another time");
							}
							else
							{
								if(items[i][j + 1] == 0)
								{
									System.out.println("You see nothing");
								}
								else
								{
									System.out.println("You see a " + items[i][j + 1]);
								}
								
								i = room[0].length;
								j = room[1].length;
							}
						}
						else if(direction.equals("west"))
						{
							if(j == 0)
							{
								System.out.println("You can stare at walls another time");
							}
							else
							{
								if(items[i][j - 1] == 0)
								{
									System.out.println("You see nothing");
								}
								else
								{
									System.out.println("You see a " + items[i][j - 1]);
								}
								
								i = room[0].length;
								j = room[1].length;
							}
						}
					}
				}
			}
		}
		
		static int[][] room = new int[3][3];
		static int[][] items = new int[3][3];
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
			Actions.room[1][1] = 1;
			
			Actions.items[0][0] = 0;
			Actions.items[0][1] = 2;
			Actions.items[0][2] = 3;
			Actions.items[1][0] = 4;
			Actions.items[1][1] = 5;
			Actions.items[1][2] = 0;
			Actions.items[2][0] = 0;
			Actions.items[2][1] = 8;
			Actions.items[2][2] = 9;
			
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
			// Assign verb and noun to variables
			verb = strings[0].toLowerCase();
			noun = strings[1].toLowerCase();
			
			// Checks if verb and noun are valid for every command
			if(verb.equals("take"))
			{
				Actions.Take(noun);
			}
			else if(verb.equals("use"))
			{
				Actions.Use(noun);
			}
			else if(verb.equals("interact"))
			{
				Actions.Interact(noun);
			}
			else if(verb.equals("move") && (noun.equals("north") || noun.equals("south") || 
					noun.equals("east") || noun.equals("west")))
			{
				Actions.Move(noun);
			}
			else if(verb.equals("look") && (noun.equals("north") || noun.equals("south") || 
					noun.equals("east") || noun.equals("west")))
			{
				Actions.Look(noun);
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
