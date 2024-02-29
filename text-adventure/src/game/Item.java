package game;

public abstract class Item {
	
	public String itemName;
	public String itemDescription;
	public boolean used;
	
	Item() {
		itemName = "";
		itemDescription = "";
		used = false;
	}
	
	abstract void Interact();
}
