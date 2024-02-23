package game;

public class Chest extends Item{
	
	Item itemInChest;
	
	Chest() {
		itemName = "Chest";
		itemDescription = "A chest that could hold valuables";
		used = false;
		itemInChest = new Sword();
	}

	@Override
	void Interact() {
		
		
	}
}
