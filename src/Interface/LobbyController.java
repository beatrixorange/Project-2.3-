package Interface;
import java.util.ArrayList;

import Connection.Connection;
import Connection.Events.UpdatedPlayerListEvent;
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

				String gameType = "Reversi";

				Platform.runLater(() -> {
					Router.get().startRemoteGame(gameType);
				});
			});

			/*try {
				this.connection.connect();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
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
			
			System.out.println("Hoi " + invitePlayer + ", ik wil jou graag eem " + isRegularPlayer + " " + game);
			// TODO: Stuur invite via Connection
		}
		
		
		
		public String getTitle() {
			return "Lobby";
		}
}
