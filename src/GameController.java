
public class GameController extends AbstractController
{
	private Connection connection;
	
	public GameController(Connection connection, AbstractPlayer p1, AbstractPlayer p2)
	{
			// Beeld je in dat dit een super mooie factory is die deze instance maakt.
			AbstractGameController controller = new TicTacToeController();

			GameView view = new GameView(controller.view, p1, p2);
			
			this.view = view;
			this.connection = connection;
	}
}
