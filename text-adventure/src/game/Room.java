package game;

import java.beans.JavaBean;
import java.util.Random;
import java.io.Serializable;

@JavaBean
public class Room implements Serializable {

	private final String roomName;

	private final Item[][]grid = new Item[3][3];

	private Item item1;
	private Item item2;
	private Item item3;
	private Item item4;

	/**
	 * <p>
	 * The constructor takes a name and four items.
	 * It also takes a limit for each item.
	 * If no item spawns, that space is left blank.
	 * </p>
	 *
	 * @param Name the name of the room
	 * @param item1 an item passed into the room
	 * @param item2 an item passed into the room
	 * @param item3 an item passed into the room
	 * @param item4 an item passed into the room
	 **/
	public Room(String Name, Item item1, Item item2, Item item3, Item item4) {

		this.roomName = Name;
		this.item1 = item1;
		this.item2 = item2;
		this.item3 = item3;
		this.item4 = item4;

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
					switch (new Random().nextInt(0,4)) {
						case 1:
							grid[i][j] = item1;
							break;
						case 2:
							grid[i][j] = item2;
							break;
						case 3:
							grid[i][j] = item3;
							break;
						case 4:
							grid[i][j] = item4;
							break;
						default:
							grid[i][j] = null;
							break;
					}
			}
		}
	}

	/**
	 * <p>
	 * This method accepts a set of x y cords and returns an item for the player to interact with.
	 * </p>
	 *
	 * @param xcord x cord for the grid
	 * @param ycord y cord for the grid
	 * @return Item
	 */
	public Item MovePlayer(int xcord, int ycord) {
		return grid[xcord][ycord];
	}

	public Item Look(int xcord, int ycord) {
		return grid[xcord][ycord];
	}

	@Override
	public String toString() {
		return "-------\n" + "|" + grid[0][0] + "|" + grid[1][0] + "|" + grid[2][0] + "|" + "\n-------\n"+"|" + grid[0][1] + "|" + grid[1][1] + "|" + grid[2][1] + "|"+"\n-------\n"+"|" + grid[0][2] + "|" + grid[1][2] + "|" + grid[2][2] + "|"+"\n-------";
	}
}