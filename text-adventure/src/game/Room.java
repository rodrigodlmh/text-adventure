package game;
import java.utils.random;
public class Room {

    public string roomName

    public int[3][3] grid;

    public  Item item1;
    public Item item2;
    public Item item3;
    public Item item4;
    public static void Room(String Name, Item item1, Item item2, Item item2, Item item3, Item4) {

        this.roomName = Name;
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        this.item4 = item4;

        for (int[] xcord : grid) {
            for (int ycord : xcord) {
                switch (new Random().nextiInt(4)) {
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
        }

    }

    public Item MovePlayer(int xcord, int ycord){
        switch (grid[xcord][ycord]) {
            case 1:
                return item1;
            break;
            case 2:
                return item2;
            break;
            case 3:
                return item3;
            break;
            case 4:
                return item4;
            break;
            default:
                break;
        }
    }

	@Override
	public String toString () {
        System.out.println("-------");
		System.out.println("|" + grid[0][0] + "|" + grid[1][0] + "|" + grid[2][0] + "|");
		System.out.println("-------");
		System.out.println("|" + grid[0][1] + "|" + grid[1][1] + "|" + grid[2][1] + "|");
		System.out.println("-------");
		System.out.println("|" + grid[0][2] + "|" + grid[1][2] + "|" + grid[2][2] + "|");
		System.out.println("-------");
	}
}