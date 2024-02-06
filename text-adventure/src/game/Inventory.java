package game;

import java.util.ArrayList;

public class Inventory {
	
	// Max items that can be stored in the inventory
	private int inventoryCapacity = 20;
	
	// Array list to add items more easily
	private ArrayList<Item> items = new ArrayList<Item>();
	
	// Is inventory full
	private boolean inventoryIsFull = false;
	
	// Function to add item to the inventory
	boolean AddItemToInventory(Item item) {
		// Check if iventory is full
		if(!inventoryIsFull) {
			// Add item to the item arraylist
			items.add(item);
			// Check if size of the array list exceeds the inventory capacity
			if(items.size() >= inventoryCapacity) {
				// Set inventory is full to true
				inventoryIsFull = true;
			}
			return true;
		} else {
			System.out.print("Inventory Is Full");
			return false;
		}
	}
	
	void DisplayInventory() {
		for(int i = 0; i < items.size(); i++) {
			System.out.println(String.valueOf(i)+ items.get(i).itemName);
		}
	}
	
	// Function to read the inventory from file/database
	void ReadInventory() {
		
	}
	
	// Function to write/save the inventory to a file/database
	void WriteInventory() {
		
	}
}
