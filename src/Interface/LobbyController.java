package Interface;
import java.util.ArrayList;

import Connection.Connection;
import Connection.Events.UpdatedPlayerListEvent;
import Connection.Events.LoginSuccesEvent;
import Connection.Events.MatchStartEvent;
import Connection.Events.UpdatedPlayerListEvent;

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
					this.connection.deRegister();
					Router.get().startRemoteGame(gameType, startTurn, opponent);
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
			 System.out.println("hot");
			 
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
}
