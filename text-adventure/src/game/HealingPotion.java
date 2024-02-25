package game;

import java.util.Random;

public class HealingPotion extends Item{
	
	int healthRecovery;
	
	HealingPotion() {
		itemName = "potion";
		itemDescription = "A potion that heals up to 50 hp";
		used = false;
		Random random = new Random();
		healthRecovery = random.nextInt(50 - 25 + 1) + 25;
	}

	@Override
	void Interact() {
		// TODO Auto-generated method stub
		
	}
}
