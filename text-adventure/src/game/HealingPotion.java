package game;

import java.util.Scanner;

public class HealingPotion extends Item{
	
	@Override
	boolean Interact() {
		
		Scanner scanner = new Scanner(System.in);
		System.out.println(itemName);
		System.out.println(itemDescription);
		System.out.println("Do you want to add this item to your inventory?");
		
		// Get input (obviously done different once we have a proper verb-noun system
		String decision = scanner.nextLine();
		scanner.close();
		
		if(decision.equals("Yes")) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	void SetValues() {
		this.itemName = "Healing Potion";
		this.itemDescription = "Heals a random amount of health";
	}
}
