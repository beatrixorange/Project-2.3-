import javafx.scene.layout.VBox;

public class ReversiController extends AbstractGameController implements ClickHandler
{
	ReversiView rView = null;

	private boolean turn;

	public ReversiController()
	{
		rView = new ReversiView(this);

		this.view = rView;
	}

	public void onBoardClick(int posX, int posY)
	{
		System.out.println("Wat ga jij clicken op " + posX + " en " + posY);

		this.rView.putDisk(posX, posY, (this.turn) ? Disk.TWO : Disk.ONE);
		this.rView.reDraw();

		this.turn = !this.turn;
	}
}
