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

	public boolean isEmpty(int x, int y)
	{
		return this.board[x][y] == Tile.EMPTY;
	}

	public boolean isValid(int x, int y)
	{
		if (x < 0 || y < 0) {
			return false;
		}

		if (x >= this.sizeX || y >= this.sizeY) {
			return false;
		}

		return true;
	}

	public boolean notEmptyAndNot(int x, int y, Tile not)
	{
		Tile t = this.board[x][y];
		if (t == Tile.EMPTY) {
			return false;
		}

		if (t == not) {
			return false;
		}

		return true;
	}

	public int xyToMove(int x, int y)
	{
		return x*this.getSizeX() + y;
	}

	public int[] moveToXY(int move)
	{
		// TODO: Check this. This is error prone.
		int x = (int)Math.floor((double)move/this.getSizeX());
		int y = (move)%this.getSizeX();

		return new int[]{x, y};
	}
}
