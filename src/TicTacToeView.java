import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TicTacToeView extends AbstractGameView {
	
	public TicTacToeView(){
		//Pane pane = new Pane();
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(33);
		gridPane.getColumnConstraints().add(column1);
		gridPane.getColumnConstraints().add(column1);
		gridPane.getColumnConstraints().add(column1);
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				Rectangle rect = new Rectangle(x*200,y*200,199,199);

				rect.setStroke(Color.BLACK);
				
				gridPane.add(rect, y, x);
			}
		}
		
		this.scene = new Scene(gridPane);
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Tic-Tac-Toe";
	}
	
}
