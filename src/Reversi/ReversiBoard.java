package Reversi;

import Framework.Tile;
import Framework.Board;

public class ReversiBoard extends Board
{
	private ReversiLogic logic = new ReversiLogic();

	public ReversiBoard(int sizeX, int sizeY, boolean startTurn)
	{
		super(sizeX, sizeY);

		int halfX = (int)Math.ceil(sizeX/2);
		int halfY = (int)Math.ceil(sizeY/2);

		if (!startTurn) {
			this.putTile(halfX-1, halfY-1, Tile.TWO);
			this.putTile(halfX-1, halfY,   Tile.ONE);
			this.putTile(halfX,   halfY-1, Tile.ONE);
			this.putTile(halfX,   halfY,   Tile.TWO);
		} else {
			this.putTile(halfX-1, halfY-1, Tile.ONE);
			this.putTile(halfX-1, halfY,   Tile.TWO);
			this.putTile(halfX,   halfY-1, Tile.TWO);
			this.putTile(halfX,   halfY,   Tile.ONE);
		}
	}
}
