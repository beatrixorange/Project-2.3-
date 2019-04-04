package Framework;

import Interface.AbstractController;

public class AbstractGameController extends AbstractController
{
	protected Board board;

	protected boolean turn = false;

	protected int turnSwitches = 0;

	protected GameController parentController;

	protected void switchTurn()
	{
		this.turn = !this.turn;

		this.turnSwitches++;

		if (this.parentController != null) {
			this.parentController.switchedTurn(this.turn);
		}
	}

	protected Tile turnsTile()
	{
		if (!this.turn) {
			return Tile.ONE;
		}

		return Tile.TWO;
	}
}
