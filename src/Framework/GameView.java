package Framework;
import Interface.AbstractView;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.Node;

public class GameView extends AbstractView
{
	private Label turnLabel = null;

	private AbstractPlayer player1, player2;

	private Label scoreLabel1, scoreLabel2;

	public GameView(AbstractGameView gameView, AbstractPlayer p1, AbstractPlayer p2)
	{
		this(gameView, p1, p2, false);
	}

	public GameView(AbstractGameView gameView, AbstractPlayer p1, AbstractPlayer p2, boolean hasScores)
	{
		this.player1 = p1;
		this.player2 = p2;

		VBox vbox = new VBox();
		vbox.setAlignment(Pos.TOP_CENTER);
		
		Label name1 = new Label(p1.getName());
		Label name2 = new Label(p2.getName());
		name1.setStyle("-fx-font: 20px Arial;");
		name2.setStyle("-fx-font: 20px Arial;");

		this.turnLabel = new Label("Prepare!");
		this.turnLabel.setStyle("-fx-font: 24px Arial;");

		Region st00fs = new Region();
		Region st00fs2 = new Region();

		HBox hbox1 = new HBox();

		hbox1.setHgrow(st00fs, Priority.ALWAYS);
		hbox1.setHgrow(st00fs2, Priority.ALWAYS);

		hbox1.getChildren().addAll(name1, st00fs, this.turnLabel, st00fs2, name2);
		hbox1.setAlignment(Pos.TOP_LEFT);

		HBox hbox2 = new HBox();

		this.scoreLabel1 = new Label("0");
		this.scoreLabel2 = new Label("0");
		this.scoreLabel1.setStyle("-fx-font: 20px Arial;");
		this.scoreLabel2.setStyle("-fx-font: 20px Arial;");

		Region st00fs3 = new Region();

		hbox2.setHgrow(st00fs3, Priority.ALWAYS);
		hbox2.getChildren().addAll(this.scoreLabel1, st00fs3, this.scoreLabel2);
		hbox2.setAlignment(Pos.TOP_LEFT);

		Node node = gameView.getScene().getRoot();
		if (hasScores) {
			vbox.getChildren().addAll(hbox1, hbox2, node);
		} else {
			vbox.getChildren().addAll(hbox1, node);
		}
		
		this.scene = new Scene(vbox,800,800);
	}

	public void updateTurn(boolean newTurn)
	{
		System.out.println("Switch turn in view: " + newTurn);
		String name = (newTurn) ? this.player2.getName() : this.player1.getName();
		this.turnLabel.setText(name + "'s turn");
	}

	public void setScores(int player1, int player2)
	{
		this.scoreLabel1.setText(player1 + "");
		this.scoreLabel2.setText(player2 + "");
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Game";
	}
}
