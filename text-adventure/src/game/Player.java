package game;

public class Player {
	
	private int health = 100;
	private int baseDamage = 10;
	private Item equippedItem;
	private boolean hasShield = false;
	
	public int GetHealth()
	{
		return health;
	}
	
	public void SetHealth(int damage)
	{
		health -= damage;
	}
	
	public boolean GetShield()
	{
		return hasShield;
	}
	
	public void SetShield(boolean shield)
	{
		hasShield = shield;
	}
	
	private Inventory inventory = new Inventory();
	
	//
	public void GetAllItemsInRoom(Item[] items) {
		for(Item item : items) {
			if(item.Interact()) {
				//inventory.AddItemToInventory(item);	
			}
		}
	}
	
	public void GetCurrentRoom(/*Room currentRoom*/){
	
	}
	
	public void GetApproachedItem(Item item) {
		if(item.Interact()) {
			//inventory.AddItemToInventory(item);
		}
	}
	
	public void DisplayInventory() {
		inventory.DisplayInventory();
	}
}
