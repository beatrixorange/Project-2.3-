
public class GameController extends AbstractController
{
	private Connection connection;
	
	public GameController(Connection connection)
	{
			GameView view = new GameView();
			
			this.view = view;
			this.connection = connection;
	}
}
