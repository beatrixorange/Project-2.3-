package Interface;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.Modality;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Connection.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;

public class Popup
{
	private Stage stage;
	private ListView<String> list;
	private Button button;
	private Button button2;
	private Connection connection;

	public Popup(String title, String contents)
	{
		init(title, contents, null, null);
	}
	
	public Popup(String title, String contents, String button1, String button2, Connection connection)
	{
		init(title, contents, button1, button2);
	}
	
	private void init(String title, String contents, String b1text, String b2text)
	{
		this.connection = connection;
		this.stage=new Stage();

		this.stage.initModality(Modality.APPLICATION_MODAL);
		this.stage.setTitle(title);

		Label label = new Label(contents);

		HBox hBox = new HBox();
		if (b1text != null) {
			this.list = new ListView<String>();
			updatePlayers(connection.getChallengerList());
			hBox.getChildren().add(list);
			
		}
				
		this.button = new Button((b1text != null) ? b1text : "Close");
		button.setOnAction((e) -> {
			stage.close();
		});
		
		hBox.getChildren().add(button);
		
		if (b1text != null) {
			this.button2 = new Button(b2text);
			hBox.getChildren().add(button2);

		}

		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, hBox);
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
	
	public void onButton2(EventHandler handler)
	{
		this.button2.setOnAction(e -> {
			handler.handle(e);
			Iterator it = connection.getChallengerList().entrySet().iterator();
			while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        if(pair.getValue().equals(this.list.getSelectionModel().getSelectedItem())) {
		        	String s = (String) pair.getKey();
		        	connection.acceptChallenge((s));
		        };
			}
		});
	}

	public void show()
	{
		this.stage.show();
	}
	
	public void updatePlayers(HashMap<Integer, String> temp) {
		ObservableList<String> items = FXCollections.observableArrayList();
			Iterator it = temp.entrySet().iterator();
			while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        items.add((String) pair.getValue());
		        it.remove();
			
		}
		list.setItems(items);
	}
}
