package game;

import java.beans.JavaBean;
import java.util.Random;
import java.io.Serializable;

@JavaBean
public class Room implements Serializable {

	private String roomName;

	private int[][] grid = new int[3][3];

	private Item item1;
	private Item item2;
	private Item item3;
	private Item item4;
	
	public String GetName()
	{
		return roomName;
	}
	
	public int[][] getGrid()
	{
		return grid;
	}

	public Room()
	{
		
	}
	
	/**
	 * <p>
	 * The constructor takes a name and four items.
	 * The room is then filled with the items based on each item's spawn rate.
	 * If no item spawns, that space is left blank.
	 * </p>
	 *
	 * @param Name
	 * @param item1
	 * @param item2
	 * @param item2
	 * @param item3
	 */
	public Room(String Name, Item item1, Item item2, Item item3, Item item4) {

		this.roomName = Name;
		this.item1 = item1;
		this.item2 = item2;
		this.item3 = item3;
		this.item4 = item4;

		/*for (int[] xcord : grid) {
			for (int ycord : xcord) {
				switch (new Random().nextInt(4)) {
					case 1:
						grid[xcord][ycord] = item1.Spawn() ? 1 : 0;
						break;
					case 2:
						grid[xcord][ycord] = item1.Spawn() ? 2 : 0;
						break;
					case 3:
						grid[xcord][ycord] = item1.Spawn() ? 3 : 0;
						break;
					case 4:
						grid[xcord][ycord] = item1.Spawn() ? 4 : 0;
						break;
					default:
						grid[xcord][ycord] = 0;
						break;
				}
			}
		}*/

	}

	/**
	 * <p>
	 * This method accepts a set of x y cords and returns an item for the player to interact with.
	 * </p>
	 *
	 * @param xcord
	 * @param ycord
	 * @return Item
	 */
	public Item MovePlayer(int xcord, int ycord) {
		switch (grid[xcord][ycord]) {
			case 1:
				return item1;
			case 2:
				return item2;
			case 3:
				return item3;
			case 4:
				return item4;
			default:
				return null;
		}
	}

	@Override
	public String toString() {
		return "-------\n" + "|" + grid[0][0] + "|" + grid[1][0] + "|" + grid[2][0] + "|" + "\n-------\n"+"|" + grid[0][1] + "|" + grid[1][1] + "|" + grid[2][1] + "|"+"\n-------\n"+"|" + grid[0][2] + "|" + grid[1][2] + "|" + grid[2][2] + "|"+"\n-------";
	}
}