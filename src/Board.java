public class Board
{
	protected Disk[][] board;

	protected int sizeX, sizeY;

	public Board(int sizeX, int sizeY)
	{
		this.sizeX = sizeX;
		this.sizeY = sizeY;

		// Initialise board.
		this.board = new Disk[sizeX][sizeY];
		for (int x = 0; x < this.sizeX; x++) {
			for (int y = 0; y < this.sizeY; y++) {
				this.board[x][y] = Disk.EMPTY;
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

	public Disk getDisk(int x, int y)
	{
		return this.board[x][y];
	}

	public void putDisk(int x, int y, Disk disk)
	{
		this.board[x][y] = disk;
	}
}
