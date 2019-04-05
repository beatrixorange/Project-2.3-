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
}
