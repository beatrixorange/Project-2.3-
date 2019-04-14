package Interface;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import Framework.AIHardness;

public class LobbyView extends AbstractView {
	
	private Button quickPlayButton;
	private ChoiceBox<String> cb;
	
	private ToggleGroup group;
	private RadioButton playerButton;
	private RadioButton computerButton;
	
	private Button refreshButton;
	private Label playerList;
	private HBox hbRefreshButton;

	private Slider aiHardness;

	private ListView<String> list;
	private Button inviteButton;
	private HBox hbInviteButton;

	public LobbyView() {
		VBox lobby = new VBox();
		lobby.setAlignment(Pos.CENTER);
		
		Label label = new Label("What game do you want to play?");
		
		this.cb = new ChoiceBox<String>(FXCollections.observableArrayList(
			    "Tic-tac-toe", "Reversi", "Coming soon"));
		// TODO: Naming is important as the server is picky.
		
		this.group = new ToggleGroup();
		this.playerButton = new RadioButton("Play as Player");
		playerButton.setToggleGroup(group);

		this.computerButton = new RadioButton("Play as Computer");	
		computerButton.setToggleGroup(group);
		computerButton.setSelected(true);
		
		this.playerList = new Label("Online Players");
		this.refreshButton = new Button("Refresh");
		this.hbRefreshButton = new HBox();
		hbRefreshButton.setSpacing(10.0);
		hbRefreshButton.getChildren().addAll(playerList, refreshButton);

		Label aiHardnessLabel = new Label("Computer skill level:");
		this.aiHardness = new Slider(0, 1, 0.5);
		this.aiHardness.setShowTickMarks(true);
		this.aiHardness.setSnapToTicks(true);
		this.aiHardness.setMajorTickUnit(2);

		HBox aiHbox = new HBox();
		aiHbox.getChildren().addAll(aiHardnessLabel, aiHardness);
		aiHbox.setAlignment(Pos.CENTER);
		
		this.list = new ListView<String>();
		//ObservableList<String> items = FXCollections.observableArrayList ("haha","hihi");
		//list.setItems(items);
		
		this.quickPlayButton = new Button("Quick Play");
		this.inviteButton = new Button("Invite");
		this.hbInviteButton = new HBox();
		hbInviteButton.getChildren().addAll(quickPlayButton, inviteButton);
		
		lobby.getChildren().addAll(label, cb, playerButton, computerButton, aiHbox, hbRefreshButton, list, hbInviteButton);
		
		this.scene = new Scene(lobby);
	}
	
	public void setOnQuickPlayButtonPressHandler(LobbyController handler)
	{
		this.quickPlayButton.setOnAction((ActionEvent e) -> {
			//public void handle(ActionEvent e) {
				handler.onQuickPlayButtonPress(this.cb.getValue(), this.group.getSelectedToggle() == this.playerButton);
			//}
		});
	}
	public void setOnInviteButtonPressHandler(LobbyController handler)
	{
		this.inviteButton.setOnAction((ActionEvent e) -> {
			//public void handle(ActionEvent e) {
				handler.onInviteButtonPress(this.cb.getValue(), this.group.getSelectedToggle() == this.playerButton, this.list.getSelectionModel().getSelectedItem());
			//}
		});
	}
	
	public void setOnRefreshButtonPressHandler(LobbyController handler)
	{
		this.refreshButton.setOnAction((ActionEvent e) -> {
			//public void handle(ActionEvent e) {
				handler.onRefreshButtonPress();
				
			//}
		});
	}
	
	public void updatePlayers(ArrayList<String> temp) {
		ObservableList<String> items = FXCollections.observableArrayList();
		for(int x = 0; x < temp.size();x++) {
			items.add(temp.get(x));
		}
		list.setItems(items);
	}
	
	public Boolean getToggle() {
		boolean player = true;
		RadioButton t = (RadioButton) this.group.getSelectedToggle();
		
		String s = t.getText();
		if(s.equals("Play as Player")) {
			player = true;
		}
		else {
			player = false;
		}
		return player;
	}

	public AIHardness getAIHardness()
	{
		double val = this.aiHardness.getValue();
		System.out.println(val);

		if (val < 0.3) {
			return AIHardness.EASY;
		}

		if (val > 0.7) {
			return AIHardness.HARD;
		}

		return AIHardness.MEDIUM;
	}
	
	@Override
	public String getTitle() {
		return "Lobby";
	}

}
