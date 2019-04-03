package Framework;

import Interface.AbstractController;

public class AbstractGameController extends AbstractController
{
	protected Board board;

	protected boolean turn;

	protected GameController parentController;

	protected void switchTurn()
	{
		this.turn = !this.turn;

		if (this.parentController != null) {
			this.parentController.switchedTurn(this.turn);
		}
	}
}
