import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.Node;

abstract public class ReversiBoard extends Board
{
	public ReversiBoard(int sizeX, int sizeY)
	{
		super(sizeX, sizeY);
	}

	public Node makeGridNode(Disk disk)
	{
		Rectangle rect = new Rectangle();
		rect.setWidth(50);
		rect.setHeight(50);
		rect.setFill(this.color(disk));

		return rect;
	}

	private Color color(Disk disk)
	{
		switch (disk) {
		case ONE:
			return Color.BLACK;
		case TWO:
			return Color.WHITE;
		default:
			return Color.GRAY;
		}
	}
}
