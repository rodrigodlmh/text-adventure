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
	private static boolean stairOpen = false;
	private static boolean ogreDead = false;
	private static boolean goblinDead = false;
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
	private static Room dungeon = new Room("dungeon", new Key(), new Chest(), new Statue(), new Monster("troll", "Blocking the stairway"));
	private static Room library = new Room("library", new Torch(), new Desk(), new Bookshelf(), new Shield());
	private static Room options = new Room("options", new Key(), new Door("door1"), new Door("door2"), new Door("door3"));
	private static Room combat = new Room("combat", new HealingPotion(), new Monster("ogre", "Lesser monster"), 
			new Monster("goblin", "Lesser monster"), new Monster("dragon", "Guarding door"));
	private static Room currentRoom = dungeon;
	// New inventory
	//private static Inventory invClass = new Inventory();
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
			
			System.out.println("You find yourself in the dungeon");
			
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
					noun.equals("east") || noun.equals("west") || noun.equals("up") || noun.equals("down") || noun.equals("left") || 
					noun.equals("right")))
			{
				Actions.Move(noun);
			}
			else if(verb.equals("look") && (noun.equals("north") || noun.equals("south") || 
					noun.equals("east") || noun.equals("west") || noun.equals("up") || noun.equals("down") || noun.equals("left") || 
					noun.equals("right")))
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
					if(currentRoom.getGrid()[i][j] != null && room[i][j] == 1)
					{
						if(currentRoom.getGrid()[i][j].itemName.equals("torch") && item.equals("torch"))
						{
							playClass.GetInventory().AddItem(currentRoom.getGrid()[i][j]);
							
							torchLit = true;
							
							System.out.println("You took the torch and can now see your surroundings");
							
							currentRoom.getGrid()[i][j] = null;
							
							i = room[0].length;
							j = room[1].length;
							
							break;
						}
						else if(currentRoom.getGrid()[i][j].itemName.equals("shield") && item.equals("shield"))
						{
							playClass.GetInventory().AddItem(currentRoom.getGrid()[i][j]);
							
							playClass.SetShield(true);
							
							System.out.println("You took the shield");
							
							currentRoom.getGrid()[i][j] = null;
							
							i = room[0].length;
							j = room[1].length;
							
							break;
						}
						else if(currentRoom.getGrid()[i][j].itemName.equals(item))
						{
							playClass.GetInventory().AddItem(currentRoom.getGrid()[i][j]);
							
							System.out.println("You took the " + item);
							
							currentRoom.getGrid()[i][j] = null;
							
							i = room[0].length;
							j = room[1].length;
							
							break;
						}
					}
					else if(i == room[0].length - 1 && j == room[1].length - 1 && currentRoom.getGrid()[i][j] == null)
					{
						System.out.println("Nothing to take");
						
						i = room[0].length;
						j = room[1].length;
						
						break;
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
			for(int i = 0; i < currentRoom.getGrid()[0].length; i++)
			{
				for(int j = 0; j < currentRoom.getGrid()[1].length; j++)
				{
					if(currentRoom.getGrid()[i][j] != null && room[i][j] == 1)
					{
						if(currentRoom.getGrid()[i][j].itemName.equals("chest") && item.equals("key") && 
								playClass.GetInventory().GetItem("key") != null)
						{
							Sword sword = new Sword();
							
							playClass.SetDamage(sword.baseDamageIncrease);
							
							playClass.GetInventory().AddItem(sword);
							
							playClass.GetInventory().DeleteItem(item);
							
							System.out.println("Inside the chest, you find a sword. You can now do battle.");
							
							currentRoom.getGrid()[i][j] = null;
							
							i = room[0].length;
							j = room[1].length;
							
							break;
						}
						if(currentRoom.getGrid()[i][j].itemName.equals("bookshelf") && item.equals("book") && 
								playClass.GetInventory().GetItem("book") != null)
						{
							System.out.println("You insert the book into an empty slot on the shelf, and it swings open to reveal the next room.\n"
									+ "You find yourself with multiple choices");
							
							playClass.GetInventory().DeleteItem(item);
							playClass.GetInventory().DeleteItem("torch");
							
							currentRoom = options;
							
							Actions.room[i][j] = 0;
							Actions.room[1][1] = 1;
							
							i = room[0].length;
							j = room[1].length;
							
							break;
						}
						if(currentRoom.getGrid()[i][j].itemName.equals("door2") && item.equals("key") && 
								playClass.GetInventory().GetItem("key") != null)
						{
							System.out.println("You try the key on the second door, and it unlocks, opening the next room.\n"
									+ "You find yourself in danger");
							
							playClass.GetInventory().DeleteItem(item);
							
							currentRoom = combat;
							
							Actions.room[i][j] = 0;
							Actions.room[1][1] = 1;
							
							i = room[0].length;
							j = room[1].length;
							
							break;
						}
						else if(currentRoom.getGrid()[i][j].itemName.equals("door1") || currentRoom.getGrid()[i][j].itemName.equals("door3"))
						{
							System.out.println("You try the key on this door, but it doesn't fit");
						}
						if(item.equals("potion") && playClass.GetInventory().GetItem("potion") != null)
						{
							System.out.println("You heal yourself and can now survive any more battles");
							
							playClass.GetInventory().DeleteItem(item);
							
							playClass.SetHealth(-100);
							
							i = room[0].length;
							j = room[1].length;
							
							break;
						}
					}
					else if(i == room[0].length - 1 && j == room[1].length - 1 && currentRoom.getGrid()[i][j] == null)
					{
						System.out.println("Cannot use");
						
						i = room[0].length;
						j = room[1].length;
						
						break;
					}
				}
			}
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
					if(currentRoom.getGrid()[i][j] != null && room[i][j] == 1)
					{
						if(object.equals("statue") && currentRoom.getGrid()[i][j].itemName.equals("statue"))
						{
							System.out.println("You moved the statue, and underneath is a button that got released. You hear something change in "
									+ "the room.");
							
							stairOpen = true;
							
							currentRoom.getGrid()[i][j] = null;
							
							i = room[0].length;
							j = room[1].length;
							
							break;
						}
						if(object.equals("desk") && currentRoom.getGrid()[i][j].itemName.equals("desk"))
						{
							System.out.println("Inside the drawer of the desk, you find a well-used book. It has been added to your inventory.");
							
							playClass.GetInventory().AddItem(new Book());
							
							currentRoom.getGrid()[i][j] = null;
							
							i = room[0].length;
							j = room[1].length;
							
							break;
						}
					}
					else if(i == room[0].length - 1 && j == room[1].length - 1 && currentRoom.getGrid()[i][j] == null)
					{
						System.out.println("Nothing to interact with");
						
						i = room[0].length;
						j = room[1].length;
						
						break;
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
			for(int i = 0; i < currentRoom.getGrid()[0].length; i++)
			{
				for(int j = 0; j < currentRoom.getGrid()[1].length; j++)
				{
					if(currentRoom.getGrid()[i][j] != null && room[i][j] == 1)
					{
						if(playClass.GetHealth() > 20 && playClass.GetShield() == false)
						{
							if(entity.equals("troll") && currentRoom.getGrid()[i][j].itemName.equals("troll") && stairOpen == true && 
									playClass.GetInventory().GetItem("sword") != null)
							{
								System.out.println("You strike down the troll, clearing the stairs to the next room, but you lose some health.\n"
										+ "You find yourself in darkness");
								
								currentRoom = library;
								
								Actions.room[i][j] = 0;
								Actions.room[1][1] = 1;
								
								playClass.SetHealth(20);
								
								i = room[0].length;
								j = room[1].length;
								
								break;
							}
							if(entity.equals("ogre") && currentRoom.getGrid()[i][j].itemName.equals("ogre"))
							{
								System.out.println("The ogre is dead. You lose some health");
								
								ogreDead = true;
								
								currentRoom.getGrid()[i][j] = null;
								
								playClass.SetHealth(20);
								
								i = room[0].length;
								j = room[1].length;
								
								break;
							}
							if(entity.equals("goblin") && currentRoom.getGrid()[i][j].itemName.equals("goblin"))
							{
								System.out.println("The goblin is dead. You lose some health");
								
								goblinDead = true;
								
								currentRoom.getGrid()[i][j] = null;
								
								playClass.SetHealth(20);
								
								i = room[0].length;
								j = room[1].length;
								
								break;
							}
							if(entity.equals("dragon") && currentRoom.getGrid()[i][j].itemName.equals("dragon"))
							{
								if(ogreDead == true && goblinDead == true)
								{
									System.out.println("At long last, you defeat the final challenge, clearing your way to freedom");
									
									end = true;
									
									i = room[0].length;
									j = room[1].length;
									
									break;
								}
								else
								{
									System.out.println("You must slay the other monsters in the room before battling the dragon so that "
											+ "they aren't a hinderance in your epic battle");
								}
							}
						}
						else if(playClass.GetHealth() > 10 && playClass.GetShield() == true)
						{
							if(entity.equals("troll") && currentRoom.getGrid()[i][j].itemName.equals("troll") && stairOpen == true && 
									playClass.GetInventory().GetItem("sword") != null)
							{
								System.out.println("You strike down the troll, clearing the stairs to the next room, but you lose some health.\n"
										+ "You find yourself in darkness");
								
								currentRoom = library;
								
								Actions.room[i][j] = 0;
								Actions.room[1][1] = 1;
								
								playClass.SetHealth(10);
								
								i = room[0].length;
								j = room[1].length;
								
								break;
							}
							if(entity.equals("ogre") && currentRoom.getGrid()[i][j].itemName.equals("ogre"))
							{
								System.out.println("The ogre is dead. You lose some health");
								
								ogreDead = true;
								
								currentRoom.getGrid()[i][j] = null;
								
								playClass.SetHealth(10);
								
								i = room[0].length;
								j = room[1].length;
								
								break;
							}
							if(entity.equals("goblin") && currentRoom.getGrid()[i][j].itemName.equals("goblin"))
							{
								System.out.println("The goblin is dead. You lose some health");
								
								goblinDead = true;
								
								currentRoom.getGrid()[i][j] = null;
								
								playClass.SetHealth(10);
								
								i = room[0].length;
								j = room[1].length;
								
								break;
							}
							if(entity.equals("dragon") && currentRoom.getGrid()[i][j].itemName.equals("dragon"))
							{
								if(ogreDead == true && goblinDead == true)
								{
									System.out.println("At long last, you defeat the final challenge, clearing your way to freedom");
									
									end = true;
									
									i = room[0].length;
									j = room[1].length;
									
									break;
								}
								else
								{
									System.out.println("You must slay the other monsters in the room before battling the dragon so that "
											+ "they aren't a hinderance in your epic battle");
								}
							}
						}
						else
						{
							System.out.println("You need to heal first");
						}
					}
					else if(i == room[0].length - 1 && j == room[1].length - 1 && currentRoom.getGrid()[i][j] == null)
					{
						System.out.println("Cannot fight");
						
						i = room[0].length;
						j = room[1].length;
						
						break;
					}
				}
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
				playClass.DisplayInventory();
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
							
							break;
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
						if(direction.equals("north") || direction.equals("up"))
						{
							if(i == 0)
							{
								System.out.println("Walking into a wall isn't advisable");
							}
							else
							{
								room[i][j] = 0;
								room[i - 1][j] = 1;
							}
						}
						else if(direction.equals("south") || direction.equals("down"))
						{
							if(i == 2)
							{
								System.out.println("Walking into a wall isn't advisable");
							}
							else
							{
								room[i][j] = 0;
								room[i + 1][j] = 1;
							}
						}
						else if(direction.equals("east") || direction.equals("right"))
						{
							if(j == 2)
							{
								System.out.println("Walking into a wall isn't advisable");
							}
							else
							{
								room[i][j] = 0;
								room[i][j + 1] = 1;
							}
						}
						else if(direction.equals("west") || direction.equals("left"))
						{
							if(j == 0)
							{
								System.out.println("Walking into a wall isn't advisable");
							}
							else
							{
								room[i][j] = 0;
								room[i][j - 1] = 1;
							}
						}
						
						i = room[0].length;
						j = room[1].length;
						
						break;
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
							if(direction.equals("north") || direction.equals("up"))
							{
								if(i == 0)
								{
									System.out.println("You can stare at walls another time");
								}
								else
								{
									if(currentRoom.Look(i - 1, j) == null)
									{
										System.out.println("It's too dark to see");
									}
									else
									{
										if(currentRoom.Look(i - 1, j).itemName.equals("torch"))
										{
											System.out.println("You see a lit torch");
										}
										else
										{
											System.out.println("It's too dark to see");
										}
									}
								}
							}
							else if(direction.equals("south") || direction.equals("down"))
							{
								if(i == 2)
								{
									System.out.println("You can stare at walls another time");
								}
								else
								{
									if(currentRoom.Look(i + 1, j) == null)
									{
										System.out.println("It's too dark to see");
									}
									else
									{
										if(currentRoom.Look(i + 1, j).itemName.equals("torch"))
										{
											System.out.println("You see a lit torch");
										}
										else
										{
											System.out.println("It's too dark to see");
										}
									}
								}
							}
							else if(direction.equals("east") || direction.equals("right"))
							{
								if(j == 2)
								{
									System.out.println("You can stare at walls another time");
								}
								else
								{
									if(currentRoom.Look(i, j + 1) == null)
									{
										System.out.println("It's too dark to see");
									}
									else
									{
										if(currentRoom.Look(i, j + 1).itemName.equals("torch"))
										{
											System.out.println("You see a lit torch");
										}
										else
										{
											System.out.println("It's too dark to see");
										}
									}
								}
							}
							else if(direction.equals("west") || direction.equals("left"))
							{
								if(j == 0)
								{
									System.out.println("You can stare at walls another time");
								}
								else
								{
									if(currentRoom.Look(i, j - 1) == null)
									{
										System.out.println("It's too dark to see");
									}
									else
									{
										if(currentRoom.Look(i, j - 1).itemName.equals("torch"))
										{
											System.out.println("You see a lit torch");
										}
										else
										{
											System.out.println("It's too dark to see");
										}
									}
								}
							}
							
							i = room[0].length;
							j = room[1].length;
							
							break;
						}
						else
						{
							if(direction.equals("north") || direction.equals("up"))
							{
								if(i == 0)
								{
									System.out.println("You can stare at walls another time");
								}
								else
								{
									if(currentRoom.Look(i - 1, j) == null)
									{
										System.out.println("You see nothing");
									}
									else
									{
										if(currentRoom.Look(i - 1, j).itemName.equals("troll") && stairOpen == false)
										{
											System.out.println("You see gaps in the shape of a square, but you're not strong enough to move an "
													+ "entire chunk of floor");
										}
										else
										{
											System.out.println("You see a " + currentRoom.Look(i - 1, j).itemName);
										}
									}
								}
							}
							else if(direction.equals("south") || direction.equals("down"))
							{
								if(i == 2)
								{
									System.out.println("You can stare at walls another time");
								}
								else
								{
									if(currentRoom.Look(i + 1, j) == null)
									{
										System.out.println("You see nothing");
									}
									else
									{
										if(currentRoom.Look(i + 1, j).itemName.equals("troll") && stairOpen == false)
										{
											System.out.println("You see gaps in the shape of a square, but you're not strong enough to move an "
													+ "entire chunk of floor");
										}
										else
										{
											System.out.println("You see a " + currentRoom.Look(i + 1, j).itemName);
										}
									}
								}
							}
							else if(direction.equals("east") || direction.equals("right"))
							{
								if(j == 2)
								{
									System.out.println("You can stare at walls another time");
								}
								else
								{
									if(currentRoom.Look(i, j + 1) == null)
									{
										System.out.println("You see nothing");
									}
									else
									{
										if(currentRoom.Look(i, j + 1).itemName.equals("troll") && stairOpen == false)
										{
											System.out.println("You see gaps in the shape of a square, but you're not strong enough to move an "
													+ "entire chunk of floor");
										}
										else
										{
											System.out.println("You see a " + currentRoom.Look(i, j + 1).itemName);
										}
									}
								}
							}
							else if(direction.equals("west") || direction.equals("left"))
							{
								if(j == 0)
								{
									System.out.println("You can stare at walls another time");
								}
								else
								{
									if(currentRoom.Look(i, j - 1) == null)
									{
										System.out.println("You see nothing");
									}
									else
									{
										if(currentRoom.Look(i, j - 1).itemName.equals("troll") && stairOpen == false)
										{
											System.out.println("You see gaps in the shape of a square, but you're not strong enough to move an "
													+ "entire chunk of floor");
										}
										else
										{
											System.out.println("You see a " + currentRoom.Look(i, j - 1).itemName);
										}
									}
								}
							}
							
							i = room[0].length;
							j = room[1].length;
							
							break;
						}
					}
				}
			}
		}
		
		static int[][] room = new int[3][3];
	}
}
