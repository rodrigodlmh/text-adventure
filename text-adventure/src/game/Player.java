package game;

public class Player {
	
	private int health = 70;
	private int baseDamage = 0;
	private Item equippedItem;
	private boolean hasShield = false;
	
	public int GetHealth()
	{
		return health;
	}
	
	public void SetDamage(int sword)
	{
		baseDamage += sword;
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
	
	public Inventory GetInventory()
	{
		return inventory;
	}
	
	//
	public void GetAllItemsInRoom(Item[] items) {
	}
	
	public void GetCurrentRoom(/*Room currentRoom*/){
	
	}
	
	public void GetAproachedItem(Item item) {
		
	}
	
	public void DisplayInventory() {
		inventory.Display();
	}
}
