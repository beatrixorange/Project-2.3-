
public class AbstractGameController extends AbstractController
{
	protected Disk[][] board;

	protected int sizeX, sizeY;

	protected boolean turn;

	protected GameController parentController;

	protected void switchTurn()
	{
		this.turn = !this.turn;

		if (this.parentController != null) {
			this.parentController.switchedTurn(this.turn);
		}
	}

	protected void initBoard(int sizeX, int sizeY)
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
}
