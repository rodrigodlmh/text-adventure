package game;

public abstract class Item {
	
	public String itemName;
	public String itemDescription;
	
	abstract boolean Interact();
	
	abstract void SetValues();
}
