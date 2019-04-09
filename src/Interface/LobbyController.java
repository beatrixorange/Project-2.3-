package Interface;
import java.util.ArrayList;

import Connection.Connection;
import Connection.Events.UpdatedPlayerListEvent;
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
			
			lView.setOnQuickPlayButtonPressHandler(this);
			lView.setOnInviteButtonPressHandler(this);
			lView.setOnRefreshButtonPressHandler(this);
			
			this.connection = connection;

			this.connection.register(event -> {
				if (!(event instanceof MatchStartEvent)) {
					return;
				}

				MatchStartEvent e = (MatchStartEvent)event;

				String gameType = e.getGameType();
				String opponent = e.getOpponent();
				boolean startTurn = e.getPlayerToMove().equals(e.getOpponent());

				System.out.println("startturn " + startTurn);
				Platform.runLater(() -> {
					Router.get().startRemoteGame(gameType, startTurn, opponent);
				});
			});
			
			
			this.connection.register(event -> {
				Platform.runLater(() -> {
					if (event instanceof ChallengedEvent) {
				
				

				ChallengedEvent cE = (ChallengedEvent)event;
				

				//boolean startTurn = e.getPlayerToMove().equals(e.getOpponent());
				Popup popup = new Popup("New Challenger","These player(s) have invited you to a duel!", "Accept", "Close", this.connection);
				popup.show();
				popup.onClose(eve -> {
					this.quit();
				});
				/*System.out.println("startturn " + startTurn);
				Platform.runLater(() -> {
					Router.get().startRemoteGame(gameType, startTurn, opponent);
				});*/
				}
				});
			});


			System.out.println(this.connection.getUsername());
			if (this.connection.getUsername().startsWith("reversitest")) {
				this.connection.subscribe("Reversi");
			}
		}
		
		public void onRefreshButtonPress()
		{
			 connection.updatePlayerList();
			 
			 connection.register(event -> {
				 if (event instanceof UpdatedPlayerListEvent) {
					 lView.updatePlayers(connection.getPlayerList());
				 }
			 });
			
			// TODO: Stuur invite via Connection
		}
		
		
		public void onQuickPlayButtonPress(String game, boolean isRegularPlayer)
		{
			if (game == null) {
				return;
			}
			
			System.out.println("Hoi " + game + " " + isRegularPlayer);

			this.connection.subscribe(game);
		}
		
		public void onInviteButtonPress(String game, boolean isRegularPlayer, String invitePlayer)
		{
			if (game == null) {
				return;
			}
			
			
			this.connection.sendChallenge(invitePlayer, game);
			
			System.out.println("Hoi " + invitePlayer + ", ik wil jou graag eem " + isRegularPlayer + " " + game);
			// TODO: Stuur invite via Connection
		}
		
		
		
		public String getTitle() {
			return "Lobby";
		}
		
		public void quit()
		{
			// TODO: De-register

			Router.get().toLobby();
		}
}
