package Framework;

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

	public int[] makeMove()
	{
		return null;
	}
}
