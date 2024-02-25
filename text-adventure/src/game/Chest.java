package game;

public class Chest extends Item{
	
	public Item itemInChest;
	
	Chest() {
		itemName = "chest";
		itemDescription = "A chest that could hold valuables";
		used = false;
		itemInChest = new Sword();
	}

	@Override
	void Interact() {
		
		
	}
}
