package game;

public class Shield extends Item{
	
	int recievedDamageDecrease;
	
	Shield() {
		itemName = "shield";
		itemDescription = "Decreases incoming damage";
		used = false;
		recievedDamageDecrease = 10;
	}
	
	@Override
	void Interact() {
		// TODO Auto-generated method stub
		
	}

}
