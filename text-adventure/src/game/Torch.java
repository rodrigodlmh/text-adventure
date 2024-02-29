package game;

public class Torch extends Item{
	
	int baseDamageIncrease;
	
	Torch() {
		itemName = "torch";
		itemDescription = "Lights up an area";
		used = false;
		baseDamageIncrease = 5;
	}
	
	@Override
	void Interact() {
		// TODO Auto-generated method stub
		
	}

}
