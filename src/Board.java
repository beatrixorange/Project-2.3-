import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.application.Platform;

import javafx.scene.input.MouseEvent;

public abstract class Board extends AbstractGameView
{
	private int sizeX, sizeY;

	protected Disk[][] board;

	protected ClickHandler clickHandler;

	public Board(int sizeX, int sizeY)
	{
		this.sizeX = sizeX;
		this.sizeY = sizeY;

		// Initialise board.
		this.board = new Disk[sizeX][sizeY];
		for (int x = 0; x < this.sizeX; x++) {
			for (int y = 0; y < this.sizeY; y++) {
				this.board[x][y] = Disk.EMPTY;
			}
		}
	}

	public void reDraw()
	{
		((Pane)this.scene.getRoot()).getChildren().setAll(this.draw());
		System.out.println("Redraw");
	}

	public GridPane draw()
	{
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(5);
		gridPane.setVgap(5);

		for (int x = 0; x < this.sizeX; x++) {
			for (int y = 0; y < this.sizeY; y++) {
				Node node = this.makeGridNode(this.board[x][y]);
				
				int thisX = x;
				int thisY = y;

				node.setOnMouseClicked((MouseEvent e) -> {
					clickHandler.onBoardClick(thisX, thisY);
				});
								
				gridPane.add(node, y, x);
			}
		}

		return gridPane;
	}

	public void putDisk(int x, int y, Disk disk)
	{
		this.board[x][y] = disk;
	}

	abstract Node makeGridNode(Disk disk);
}
