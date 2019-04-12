package Framework;

/**
 * Board is the data representation of a board in a boardgame.
 */
public class Board
{
	protected Tile[][] board;

	protected int sizeX, sizeY;

	/**
	 * Board initialises this class with existing board data.
	 *
	 * @param sizeX Size of board.
	 * @param sizeY Size of board.
	 * @param board Existing board data.
	 */
	public Board(int sizeX, int sizeY, Tile[][] board)
	{
		this.sizeX = sizeX;
		this.sizeY = sizeY;

		this.board = board;
	}

	/**
	 * Board initialises a board with the given size.
	 *
	 * @param sizeX Size of board.
	 * @param sizeY Size of board.
	 */
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

	/**
	 * getTile returns the tile on the given location of the board.
	 *
	 * @param x
	 * @param y
	 *
	 * @return Tile
	 */
	public Tile getTile(int x, int y)
	{
		return this.board[x][y];
	}

	/**
	 * putTile puts the given Tile on the given location on the board.
	 *
	 * @param x
	 * @param y
	 * @param disk
	 */
	public void putTile(int x, int y, Tile disk)
	{
		this.board[x][y] = disk;
	}

	/**
	 * isEmpty checks if the given tile location contains an empty tile.
	 *
	 * @param x
	 * @param y
	 * 
	 * @return True if empty tile at location.
	 */
	public boolean isEmpty(int x, int y)
	{
		return this.board[x][y] == Tile.EMPTY;
	}

	/**
	 * isValid checks if the given location is on the board, and
	 * not outside it.
	 *
	 * @param x
	 * @param y
	 *
	 * @return True if valid location.
	 */
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

	/**
	 * xyToMove transform the given location in x,y coordinates
	 * to the tile location system used by the gameserver.
	 *
	 * @param x
	 * @param y
	 *
	 * @return Location identifier.
	 */
	public int xyToMove(int x, int y)
	{
		return x*this.getSizeX() + y;
	}

	/**
	 * moveToXY transform the given gameserver tile location system
	 * to a regular x,y coordinates system.
	 *
	 * @param int
	 *
	 * @return X and Y coordinates.
	 */
	public int[] moveToXY(int move)
	{
		int x = (int)Math.floor((double)move/this.getSizeX());
		int y = (move)%this.getSizeX();

		return new int[]{x, y};
	}

	/**
	 * @inheritDoc
	 */
	public Board clone() throws CloneNotSupportedException
	{
		Tile[][] tiles = new Tile[this.sizeX][this.sizeY];

		for (int x = 0; x < this.sizeX; x++) {
			for (int y = 0; y < this.sizeY; y++) {
				Tile t = Tile.EMPTY;
				if (this.getTile(x, y) == Tile.ONE) {
					t = Tile.ONE;
				} else if (this.getTile(x, y) == Tile.TWO) {
					t = Tile.TWO;
				}

				tiles[x][y] = t;
			}
		}

		return new Board(this.sizeX, this.sizeY, tiles);
	}

	/**
	 * @inheritDoc
	 */
	public String toString()
	{
		String str = "";

		for (int x = 0; x < this.sizeX; x++) {
			for (int y = 0; y < this.sizeY; y++) {
				str += this.getTile(x, y);
			}
			str += "\n";
		}

		return str;
	}
}
