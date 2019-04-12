package Interface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import Connection.Connection;
import Connection.Events.UpdatedPlayerListEvent;
import Framework.HumanPlayer;
import Connection.Events.ChallengedEvent;
import Connection.Events.LoginSuccesEvent;
import Connection.Events.MatchStartEvent;
import Connection.Events.UpdatedPlayerListEvent;
import Interface.Popup;

import javafx.application.Platform;

public class LobbyController extends AbstractController
{	
		private Connection connection;
		private LobbyView lView;

		public LobbyController(Connection connection)
		{	
			this.lView = new LobbyView();
			this.view = lView;
			this.connection = connection;
			
			lView.setOnQuickPlayButtonPressHandler(this);
			lView.setOnInviteButtonPressHandler(this);
			lView.setOnRefreshButtonPressHandler(this);
			
			// Update player list when opening lobby
			this.connection.updatePlayerList();
			this.connection.register(event -> {
				 if (event instanceof UpdatedPlayerListEvent) {
					 Platform.runLater(() -> {
					 	lView.updatePlayers(connection.getPlayerList());
					 });
				 }
			});
			
			this.connection.register(event -> {
				if (!(event instanceof MatchStartEvent)) {
					return;
				}

				MatchStartEvent e = (MatchStartEvent)event;

				String gameType = e.getGameType();
				String opponent = e.getOpponent();
				boolean startTurn = e.getPlayerToMove().equals(e.getOpponent());
				boolean player = lView.getToggle();
				System.out.println(player);

				System.out.println("startturn " + startTurn);
				Platform.runLater(() -> {
					Router.get().startRemoteGame(gameType, startTurn, opponent, player);
					this.quit();
				});
			});
			
			this.connection.register(event -> {
				if (!(event instanceof ChallengedEvent)) {
					return;
				}

				ChallengedEvent cE = (ChallengedEvent)event;
				Platform.runLater(() -> {
					Popup popup = new Popup("New Challenger",
							"These player(s) have invited you to a duel!",
							null, "Accept", this.connection);
					popup.button2List();
					popup.show();
				});
			});

			System.out.println(this.connection.getUsername());

			// (Debugging code. Nothing to see here.)
			if (this.connection.getUsername().startsWith("reversitest")) {
				this.connection.subscribe("Reversi");
			}
		}
		
		public void onRefreshButtonPress()
		{
			// update player list then wait for event to add player list to view
			 connection.updatePlayerList();
			 connection.register(event -> {
				 if (event instanceof UpdatedPlayerListEvent) {
					 Platform.runLater(() -> {
					 	lView.updatePlayers(connection.getPlayerList());
					 });
				 }
			 });
		}
		
		
		public void onQuickPlayButtonPress(String game, boolean isRegularPlayer)
		{
			// if no game is selected you cant press quickplay
			if (game == null) {
				return;
			}
			
			System.out.println("Hoi " + game + " " + isRegularPlayer);
			// search for random player
			this.connection.subscribe(game);
		}
		
		public void onInviteButtonPress(String game, boolean isRegularPlayer, String invitePlayer)
		{
			if (game == null) {
				return;
			}
			
			
			this.connection.sendChallenge(invitePlayer, game);
			
			System.out.println("Hoi " + invitePlayer + ", ik wil jou graag eem " + isRegularPlayer + " " + game);
		}
		
		public String getTitle() {
			return "Lobby";
		}
		
		public void quit()
		{
			this.connection.deRegister();
		}
}
