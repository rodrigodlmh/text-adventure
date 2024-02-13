package game;

import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main 
{
	// 1.6 -- Unchangeable logging variable
	final static Logger log = LogManager.getLogger(Main.class.getName());
	// Other fields
	private static String verb;
	private static String noun;
	private static boolean end = false;
	private static Locale en = new Locale("en");
	private static Locale es = new Locale("es");
	private static LocalTime currentTime = LocalTime.now();
	// Times for checking time of day user is currently at
	private static LocalTime morning1 = LocalTime.of(23, 59);
	private static LocalTime morning2 = LocalTime.of(12, 0);
	private static LocalTime afternoon1 = LocalTime.of(11, 59);
	private static LocalTime afternoon2 = LocalTime.of(18, 0);
	// Get user input
	private static Scanner input = new Scanner(System.in);
	// New room
	private static Room roomClass = new Room();
	// New inventory
	private static Inventory invClass = new Inventory();
	
	public static void main(String[] args) 
	{
		try 
		{
			Actions.room[1][1] = 1;
			
			System.out.println("Would you like to view the welcome message in English or Spanish?");
			while(end == false)
			{
				String language = input.nextLine();
				if(language.toLowerCase().trim().equals("english"))
				{
					ResourceBundle rb = ResourceBundle.getBundle("Welcome", en);
					
					if(currentTime.isAfter(morning1) && currentTime.isBefore(morning2))
					{
						System.out.print(rb.getString("Morning"));
					}
					else if(currentTime.isAfter(afternoon1) && currentTime.isBefore(afternoon2))
					{
						System.out.print(rb.getString("Afternoon"));
					}
					else
					{
						System.out.print(rb.getString("Evening"));
					}
					
					System.out.println(rb.getString("Welcome"));
					
					end = true;
				}
				else if(language.toLowerCase().trim().equals("spanish"))
				{
					ResourceBundle rb = ResourceBundle.getBundle("Welcome", es);
					
					if(currentTime.isAfter(morning1) && currentTime.isBefore(morning2))
					{
						System.out.print(rb.getString("Morning"));
					}
					else if(currentTime.isAfter(afternoon1) && currentTime.isBefore(afternoon2))
					{
						System.out.print(rb.getString("Afternoon"));
					}
					else
					{
						System.out.print(rb.getString("Evening"));
					}
					
					System.out.println(rb.getString("Welcome"));
					
					end = true;
				}
				else
				{
					System.out.println("Not a supported language");
				}
			}
				
			end = false;
			
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
            input.close();
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
			if(verb.equals("help") && (noun.equals("english") || noun.equals("spanish")))
			{
				Actions.Help(noun);
			}
			else if(verb.equals("take"))
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
			else if(verb.equals("view") && (noun.equals("inventory") || noun.equals("position")))
			{
				Actions.View(noun);
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
		else if(strings[0].toLowerCase().equals("help"))
		{
			Actions.Help("english");
		}
		else
		{
			// Prompt new input
			System.out.println("Input must contain exactly two words");
		}
	}
	
	// 1.1 + 1.7 -- Private, nested class for any character methods
	private static class Actions
	{
		// 1.5 -- Able to access the methods without initializing Actions
		private static void Help(String lang)
		{
			if(lang.equals("english"))
			{
				ResourceBundle rb = ResourceBundle.getBundle("Welcome", en);
				System.out.println(rb.getString("Help"));
			}
			else if(lang.equals("spanish"))
			{
				ResourceBundle rb = ResourceBundle.getBundle("Welcome", es);
				System.out.println(rb.getString("Help"));
			}
		}
		/**
		 * Add an item to your inventory
		 * @param item
		 */
		private static void Take(String item)
		{
			invClass.AddItemToInventory(item);
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
		 * View something you may lose track of
		 * @param noun
		 */
		private static void View(String noun)
		{
			if(noun.equals("inventory"))
			{
				invClass.DisplayInventory();
			}
			else
			{
				for(int i = 0; i < room[0].length; i++)
				{
					for(int j = 0; j < room[1].length; j++)
					{
						if(room[i][j] == 1)
						{
							if(i == 0 && j == 0)
							{
								System.out.println("Northwest");
							}
							else if(i == 0 && j == 1)
							{
								System.out.println("North");
							}
							else if(i == 0 && j == 2)
							{
								System.out.println("Northeast");
							}
							else if(i == 1 && j == 0)
							{
								System.out.println("West");
							}
							else if(i == 1 && j == 1)
							{
								System.out.println("Center");
							}
							else if(i == 1 && j == 2)
							{
								System.out.println("East");
							}
							else if(i == 2 && j == 0)
							{
								System.out.println("Southwest");
							}
							else if(i == 2 && j == 1)
							{
								System.out.println("South");
							}
							else
							{
								System.out.println("Southeast");
							}
							
							i = room[0].length;
							j = room[1].length;
						}
					}
				}
			}
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
		static int[][] items = roomClass.getGrid();
	}
}
