package game;

public class Player {
	
	private int health = 100;
	private int baseDamage = 10;
	private Item equippedItem;
	
	private Inventory inventory = new Inventory();
	
	//
	public void GetAllItemsInRoom(Item[] items) {
		for(Item item : items) {
			if(item.Interact()) {
				inventory.AddItemToInventory(item);	
			}
		}
	}
	
	public void GetCurrentRoom(/*Room currentRoom*/){
	
	}
	
	public void GetAproachedItem(Item item) {
		if(item.Interact()) {
			inventory.AddItemToInventory(item);
		}
	}
	
	public void DisplayInventory() {
		inventory.DisplayInventory();
	}
}
