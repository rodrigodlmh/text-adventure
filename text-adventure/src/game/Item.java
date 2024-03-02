package game;

/**
 * 1.2 -- All other entity classes are based off of this one
 */
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
