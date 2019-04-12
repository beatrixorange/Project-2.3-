package Framework;

/**
 * AbstractPlayer is a player in a game.
 */
public abstract class AbstractPlayer {
	protected String name;

	public AbstractPlayer(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}

	public String getNameDisplay()
	{
		if (this.name.startsWith("reversitest")) {
			return this.name.replaceFirst("^reversitest", "");
		}
		return this.name;
	}

	public int[] makeMove()
	{
		return null;
	}
}
