package Framework;
import javafx.scene.paint.Color;

/**
 * Tile represents a single state of a tile on the board.
 * A tile can be empty, owned by player one, or owned by player2.
 */
public enum Tile
{
	EMPTY, ONE, TWO;

	/**
	 * byTurn returns the Tile for the given turn.
	 *
	 * @param turn
	 *
	 * @return TWO for true turn, ONE for false turn.
	 */
	public static Tile byTurn(boolean turn)
	{
		if (turn) {
			return Tile.TWO;
		}

		return Tile.ONE;
	}

	/**
	 * other returns the opponing tile.
	 * TWO when given ONE, ONE when given TWO.
	 *
	 * @param t
	 *
	 * @return tile
	 */
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

	/**
	 * @inheritDoc
	 */
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
