package game;

public class Door extends Item{
	
	boolean isOpen;
	
	Door(String name) {
		itemName = name;
		itemDescription = "A door unlocks a new area but you might need a key";
		used = false;
		isOpen = false;
	}

	@Override
	void Interact() {
		// TODO Auto-generated method stub
		
	}
}
