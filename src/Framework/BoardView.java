package Framework;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.application.Platform;

import javafx.scene.input.MouseEvent;

/**
 * BoardView is an AbstractGameView for board games.
 * It handles drawing the board to the GUI.
 */
public abstract class BoardView extends AbstractGameView
{
	protected ClickHandler clickHandler;

	/**
	 * reDraw wraps draw() and shows it to the scene.
	 */
	public void reDraw(Board board)
	{
		((Pane)this.scene.getRoot()).getChildren().setAll(this.draw(board));
	}

	/**
	 * draw creates a JavaFX gridpane representing this board.
	 * For every location on the board, makeGridNode is called.
	 *
	 * @param board The board to draw.
	 * 
	 * @return The JavaFX GridPane.
	 */
	public GridPane draw(Board board)
	{
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(5);
		gridPane.setVgap(5);

		for (int x = 0; x < board.getSizeX(); x++) {
			for (int y = 0; y < board.getSizeY(); y++) {
				Node node = this.makeGridNode(board.getTile(x, y), x, y);
				
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

	/**
	 * makeGridNode should make a JavaFX node representing the given tile at
	 * location.
	 *
	 * @param disk
	 * @param x
	 * @param y
	 *
	 * @return The JavaFX scene node.
	 */
	abstract protected Node makeGridNode(Tile disk, int x, int y);
}
