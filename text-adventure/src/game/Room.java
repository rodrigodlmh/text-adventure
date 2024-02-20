package game;

import java.beans.JavaBean;
import java.util.Random;
import java.io.Serializable;

@JavaBean
public class Room implements Serializable {

	private final String roomName;

	private final int[][]grid = new int[3][3];

	private Item item1;
	private Item item2;
	private Item item3;
	private Item item4;

	private int limit1;
	private int limit2;
	private int limit3;
	private int limit4;

	/**
	 * <p>
	 * The constructor takes a name and four items.
	 * The room is then filled with the items based on each item's spawn rate.
	 * If no item spawns, that space is left blank.
	 * </p>
	 *
	 * @param Name the name of the room
	 * @param item1 an item passed into the room
	 * @param item2 an item passed into the room
	 * @param item3 an item passed into the room
	 * @param item4 an item passed into the room
	 * @param limit1 the maximum amount of item1
	 * @param limit2 the maximum amount of item2
	 * @param limit3 the maximum amount of item3
	 * @param limit4 the maximum amount of item4
	 */
	public Room(String Name, Item item1, Item item2, Item item3, Item item4, int limit1, int limit2, int limit3, int limit4) {

		this.roomName = Name;
		this.item1 = item1;
		this.item2 = item2;
		this.item3 = item3;
		this.item4 = item4;

		this.limit1 = limit1;
		this.limit2 = limit2;
		this.limit3 = limit3;
		this.limit4 = limit4;

		int blanks = 9 - limit1 - limit2 - limit3 - limit4;

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (blanks > 0) {
					switch (new Random().nextInt(0,4)) {
						case 1:
							if (limit1 > 0) {
								grid[i][j] = 1;
								limit1 -= 1;
							} else {
								grid[i][j] = 0;
								blanks -= 1;
							}
							break;
						case 2:
							if (limit2 > 0) {
								grid[i][j] = 2;
								limit2 -= 1;
							} else {
								grid[i][j] = 0;
								blanks -= 1;
							}
							break;
						case 3:
							if (limit3 > 0) {
								grid[i][j] = 3;
								limit3 -= 1;
							} else {
								grid[i][j] = 0;
								blanks -= 1;
							}
							break;
						case 4:
							if (limit4 > 0) {
								grid[i][j] = 4;
								limit4 -= 1;
							} else {
								grid[i][j] = 0;
								blanks -= 1;
							}
							break;
						default:
							grid[i][j] = 0;
							blanks -= 1;
							break;
					}

				} else {
					switch (new Random().nextInt(1,4)) {
						case 1:
							grid[i][j] = 2;
							limit1 -= 1;
							break;
						case 2:
							grid[i][j] = 2;
							limit2 -= 1;
							break;
						case 3:
							grid[i][j] = 3;
							limit3 -= 1;
							break;
						case 4:
							grid[i][j] = 4;
							limit4 -= 1;
							break;
						default:
							grid[i][j] = 0;
							blanks -= 1;
							break;
					}
				}
			}
		}

	}

	/**
	 * <p>
	 * This method accepts a set of x y cords and returns an item for the player to interact with.
	 * TODO This should return an item class directly
	 * </p>
	 *
	 * @param xcord
	 * @param ycord
	 * @return Item
	 */
	public int MovePlayer(int xcord, int ycord) {
		return switch (grid[xcord][ycord]) {
			case 1 -> 1;
			case 2 -> 2;
			case 3 -> 3;
			case 4 -> 4;
			default -> 0;
		};
	}

	@Override
	public String toString() {
		return "-------\n" + "|" + grid[0][0] + "|" + grid[1][0] + "|" + grid[2][0] + "|" + "\n-------\n"+"|" + grid[0][1] + "|" + grid[1][1] + "|" + grid[2][1] + "|"+"\n-------\n"+"|" + grid[0][2] + "|" + grid[1][2] + "|" + grid[2][2] + "|"+"\n-------";
	}
}