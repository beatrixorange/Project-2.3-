package Framework;
import javafx.scene.paint.Color;

public enum Tile
{
	EMPTY, ONE, TWO;

	public static Tile byTurn(boolean turn)
	{
		if (turn) {
			return Tile.TWO;
		}

		return Tile.ONE;
	}

	public static Tile other(Tile t)
	{
		if (t == Tile.ONE) {
			return Tile.TWO;
		} else if (t == Tile.TWO) {
			return Tile.ONE;
		}

		System.out.println("waat tile.other");
		return Tile.EMPTY;
	}

	public String toString()
	{
		if (this == Tile.ONE) {
			return "1";
		}
		
		if (this == Tile.TWO) {
			return "2";
		}

		return " ";
	}
}
