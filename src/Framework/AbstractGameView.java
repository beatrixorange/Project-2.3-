package Framework;

import Interface.AbstractView;

public abstract class AbstractGameView extends AbstractView
{
	protected boolean myTurn;

	public void setMyTurn(boolean newMyTurn)
	{
		this.myTurn = newMyTurn;
	}
}
