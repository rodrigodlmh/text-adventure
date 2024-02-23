package game;

import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

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
	private static boolean torchLit = false;
	// Language interfaces
	private static Supplier<Locale> en = () -> new Locale("en");
	private static Supplier<Locale> es = () -> new Locale("es");
	private static BiConsumer<Locale, String> message = (x, y) ->
	{
		ResourceBundle rb = ResourceBundle.getBundle("Welcome", x);
		
		System.out.println(rb.getString(y));
	};
	// Times for checking time of day user is currently at
	private static LocalTime currentTime = LocalTime.now();
	private static LocalTime morning1 = LocalTime.of(23, 59);
	private static LocalTime morning2 = LocalTime.of(12, 0);
	private static LocalTime afternoon1 = LocalTime.of(11, 59);
	private static LocalTime afternoon2 = LocalTime.of(18, 0);
	// Get user input
	private static Scanner input = new Scanner(System.in);
	// New rooms
	private static Room dungeon = new Room();
	private static Room library = new Room();
	private static Room options = new Room();
	private static Room combat = new Room();
	private static Room currentRoom = dungeon;
	// New inventory
	private static Inventory invClass = new Inventory();
	// New player
	private static Player playClass = new Player();
	
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
					if(currentTime.isAfter(morning1) && currentTime.isBefore(morning2))
					{
						message.accept(en.get(), "Morning");
					}
					else if(currentTime.isAfter(afternoon1) && currentTime.isBefore(afternoon2))
					{
						message.accept(en.get(), "Afternoon");
					}
					else
					{
						message.accept(en.get(), "Evening");
					}
					
					message.accept(en.get(), "Welcome");
					
					end = true;
				}
				else if(language.toLowerCase().trim().equals("spanish"))
				{
					if(currentTime.isAfter(morning1) && currentTime.isBefore(morning2))
					{
						message.accept(es.get(), "Morning");
					}
					else if(currentTime.isAfter(afternoon1) && currentTime.isBefore(afternoon2))
					{
						message.accept(es.get(), "Afternoon");
					}
					else
					{
						message.accept(es.get(), "Evening");
					}
					
					message.accept(es.get(), "Welcome");
					
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
		String newInput = input.replaceAll("\s+", " ").trim().toLowerCase();
		String[] strings = newInput.split(" ");
		
		// Ensure the input only contains a verb and a noun
		if(strings.length == 2)
		{
			// Assign verb and noun to variables
			verb = strings[0];
			noun = strings[1];
			
			// Checks if verb and noun are valid for every command
			if(verb.equals("help") && (noun.equals("english") || noun.equals("spanish")))
			{
				Actions.Help(noun);
			}
			else if(verb.equals("take") && (noun.equals("key") || noun.equals("torch") || noun.equals("shield") || noun.equals("potion")))
			{
				Actions.Take(noun);
			}
			else if(verb.equals("use") && (noun.equals("key") || noun.equals("book") || noun.equals("potion")))
			{
				Actions.Use(noun);
			}
			else if(verb.equals("interact") && (noun.equals("statue") || noun.equals("desk")))
			{
				Actions.Interact(noun);
			}
			else if(verb.equals("fight") && (noun.equals("troll") || noun.equals("ogre") || noun.equals("goblin") || noun.equals("dragon")))
			{
				Actions.Fight(noun);
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
				message.accept(en.get(), "Help");
			}
			else if(lang.equals("spanish"))
			{
				message.accept(es.get(), "Help");
			}
		}
		/**
		 * Add an item to your inventory
		 * @param item
		 */
		private static void Take(String item)
		{
			for(int i = 0; i < currentRoom.getGrid()[0].length; i++)
			{
				for(int j = 0; j < currentRoom.getGrid()[1].length; j++)
				{
					if(currentRoom.getGrid()[i][j] != null && currentRoom.getGrid()[i][j].takeable == true && 
							currentRoom.getGrid()[i][j].used == false && room[i][j] == 1)
					{
						if(item == "torch")
						{
							torchLit = true;
							
							System.out.println("You took the torch and can now see your surroundings");
						}
						else
						{
							currentRoom.getGrid()[i][j].used = true;
							
							//invClass.AddItemToInventory(item);
							
							System.out.println("You took the " + item);
						}
					}
					else
					{
						System.out.println("Nothing to take");
					}
				}
			}
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
			for(int i = 0; i < currentRoom.getGrid()[0].length; i++)
			{
				for(int j = 0; j < currentRoom.getGrid()[1].length; j++)
				{
					if(currentRoom.getGrid()[i][j] != null && currentRoom.getGrid()[i][j].used == false && room[i][j] == 1)
					{
						if(object.equals("statue") && currentRoom.getGrid()[i][j].itemName.equals("statue"))
						{
							currentRoom.getGrid()[i][j].used = true;
							
							System.out.println("You moved the statue, and underneath is a button that got released. You hear something change in the room.");
						}
						if(object.equals("desk") && currentRoom.getGrid()[i][j].itemName.equals("desk"))
						{
							currentRoom.getGrid()[i][j].used = true;
							
							System.out.println("Inside the drawer of the desk, you find a well-used book. It has been added to your inventory.");
							
							//Book book = new Book("book");
							
							//invClass.AddItemToInventory(book);
						}
					}
					else
					{
						System.out.println("Nothing to interact with");
					}
				}
			}
		}
		/**
		 * Fights with an entity and affects relevant stats
		 * @param entity
		 */
		private static void Fight(String entity)
		{
			if(playClass.GetShield() == true)
			{
				playClass.SetHealth(10);
			}
			else 
			{
				playClass.SetHealth(20);
			}
		}
		/**
		 * View something you may lose track of
		 * @param noun
		 */
		private static void View(String noun)
		{
			if(noun.equals("inventory"))
			{
				//invClass.DisplayInventory();
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
						if(currentRoom == library && torchLit == false)
						{
							if(direction.equals("north"))
							{
								if(i == 0)
								{
									System.out.println("You can stare at walls another time");
								}
								else
								{
									if(currentRoom.MovePlayer(i - 1, j).itemName.equals("torch"))
									{
										System.out.println("You see a lit torch");
									}
									else
									{
										System.out.println("It's too dark to see");
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
									if(currentRoom.MovePlayer(i + 1, j).itemName.equals("torch"))
									{
										System.out.println("You see a lit torch");
									}
									else
									{
										System.out.println("It's too dark to see");
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
									if(currentRoom.MovePlayer(i, j + 1).itemName.equals("torch"))
									{
										System.out.println("You see a lit torch");
									}
									else
									{
										System.out.println("It's too dark to see");
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
									if(currentRoom.MovePlayer(i, j - 1).itemName.equals("torch"))
									{
										System.out.println("You see a lit torch");
									}
									else
									{
										System.out.println("It's too dark to see");
									}
									
									i = room[0].length;
									j = room[1].length;
								}
							}
						}
						else
						{
							if(direction.equals("north"))
							{
								if(i == 0)
								{
									System.out.println("You can stare at walls another time");
								}
								else
								{
									if(currentRoom.MovePlayer(i - 1, j) == null)
									{
										System.out.println("You see nothing");
									}
									else
									{
										System.out.println("You see a " + currentRoom.MovePlayer(i - 1, j).itemName);
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
									if(currentRoom.MovePlayer(i + 1, j) == null)
									{
										System.out.println("You see nothing");
									}
									else
									{
										System.out.println("You see a " + currentRoom.MovePlayer(i + 1, j).itemName);
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
									if(currentRoom.MovePlayer(i, j + 1) == null)
									{
										System.out.println("You see nothing");
									}
									else
									{
										System.out.println("You see a " + currentRoom.MovePlayer(i, j + 1).itemName);
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
									if(currentRoom.MovePlayer(i, j - 1) == null)
									{
										System.out.println("You see nothing");
									}
									else
									{
										System.out.println("You see a " + currentRoom.MovePlayer(i, j - 1).itemName);
									}
									
									i = room[0].length;
									j = room[1].length;
								}
							}
						}
					}
				}
			}
		}
		
		static int[][] room = new int[3][3];
	}
}
