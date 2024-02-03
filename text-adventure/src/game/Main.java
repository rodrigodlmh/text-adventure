package game;

public class Main {

	public static void main(String[] args) {

		System.out.print("Text Adventure Game");

		Item weapon = new Item();
		Item sheild = new Item();
		Item heal = new Item();
		Item monster = new Item();
		Room MyRoom = new Room("Dungeon", weapon, sheild, heal, monster);
	}

}
