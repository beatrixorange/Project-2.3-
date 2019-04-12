package Framework;

import Interface.AbstractView;

/**
 * AbstractGameView forms the basis for games.
 */
public abstract class AbstractGameView extends AbstractView
{
	protected boolean myTurn;

	public void setMyTurn(boolean newMyTurn)
	{
		this.myTurn = newMyTurn;
	}
}
