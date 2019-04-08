package Interface;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.event.EventHandler;

public class Popup
{
	private Stage stage;
	private Button button;

	public Popup(String title, String contents)
	{
		this.stage=new Stage();

		this.stage.initModality(Modality.APPLICATION_MODAL);
		this.stage.setTitle(title);

		Label label = new Label(contents);

		this.button = new Button("Close");
		button.setOnAction((e) -> {
			stage.close();
		});

		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, button);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout, 300, 250);
		this.stage.setScene(scene);

	}

	public void onClose(EventHandler handler)
	{
		this.button.setOnAction(e -> {
			handler.handle(e);
			stage.close();
		});
	}

	public void show()
	{
		this.stage.show();
	}
}
