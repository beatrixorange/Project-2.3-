import javafx.scene.layout.VBox;

public class ReversiController extends AbstractGameController implements ClickHandler
{
	ReversiView rView = null;

	public ReversiController(GameController parent)
	{
		this.parentController = parent;

		rView = new ReversiView(this);

		this.view = rView;
	}

	public void onBoardClick(int posX, int posY)
	{
		System.out.println("Wat ga jij clicken op " + posX + " en " + posY);

		this.rView.putDisk(posX, posY, (this.turn) ? Disk.TWO : Disk.ONE);
		this.rView.reDraw();

		this.switchTurn();
	}

	public boolean isValidMove(int x, int y)
	{
		return false;
	}
}
