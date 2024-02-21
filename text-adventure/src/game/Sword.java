package game;

public class Sword extends Item{
	
	int baseDamageIncrease;
	
	Sword() {
		itemName = "Sword";
		itemDescription = "A weapon that will increase your damage";
		used = false;
		baseDamageIncrease = 10;
	}
	
	@Override
	void Interact() {
		// TODO Auto-generated method stub
		
	}
	
}
