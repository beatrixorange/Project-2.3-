package Framework;

public class Board
{
	protected Tile[][] board;

	protected int sizeX, sizeY;

	public Board(int sizeX, int sizeY)
	{
		this.sizeX = sizeX;
		this.sizeY = sizeY;

		// Initialise board.
		this.board = new Tile[sizeX][sizeY];
		for (int x = 0; x < this.sizeX; x++) {
			for (int y = 0; y < this.sizeY; y++) {
				this.board[x][y] = Tile.EMPTY;
			}
		}
	}

	public int getSizeX()
	{
		return this.sizeX;
	}

	public int getSizeY()
	{
		return this.sizeY;
	}

	public Tile getTile(int x, int y)
	{
		return this.board[x][y];
	}

	public void putTile(int x, int y, Tile disk)
	{
		this.board[x][y] = disk;
	}
}
