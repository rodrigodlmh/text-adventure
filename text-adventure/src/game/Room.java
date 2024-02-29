package game;

import java.beans.JavaBean;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.io.Serializable;

@JavaBean
public class Room implements Serializable {

	private String roomName;

	private Item[][] grid = new Item[3][3];

	private Item item1;
	private Item item2;
	private Item item3;
	private Item item4;
	
	public String GetName()
	{
		return roomName;
	}
	
	public Item[][] getGrid()
	{
		return grid;
	}
	
	public Room()
	{
		
	}
	
	/**
	 * <p>
	 * The constructor takes a name and four items.
	 * If no item spawns, that space is left blank.
	 * </p>
	 *
	 * @param name the name of the room
	 * @param item1 an item passed into the room
	 * @param item2 an item passed into the room
	 * @param item3 an item passed into the room
	 * @param item4 an item passed into the room
	 **/
	public Room(String name, Item item1, Item item2, Item item3, Item item4) {
		this.roomName = name;
		this.item1 = item1;
		this.item2 = item2;
		this.item3 = item3;
		this.item4 = item4;
		
		// Create a list of coordinates
		List<Coordinate> allCoordinates = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				allCoordinates.add(new Coordinate(i, j));
			}
		}

		// Fill the room
		for (int itemNumber = 1; itemNumber <= 4; itemNumber++) {
			if (!allCoordinates.isEmpty()) {
				int randomIndex = new Random().nextInt(allCoordinates.size());
				Coordinate selectedCoordinate = allCoordinates.remove(randomIndex);

				switch (itemNumber) {
					case 1:
						grid[selectedCoordinate.getRow()][selectedCoordinate.getCol()] = item1;
						break;
					case 2:
						grid[selectedCoordinate.getRow()][selectedCoordinate.getCol()] = item2;
						break;
					case 3:
						grid[selectedCoordinate.getRow()][selectedCoordinate.getCol()] = item3;
						break;
					case 4:
						grid[selectedCoordinate.getRow()][selectedCoordinate.getCol()] = item4;
						break;
				}
			}
		}
	}

	static class Coordinate {
		private final int row;
		private final int col;

		public Coordinate(int row, int col) {
			this.row = row;
			this.col = col;
		}

		public int getRow() {
			return row;
		}

		public int getCol() {
			return col;
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