package Interface;
import java.util.ArrayList;

import Connection.Connection;

import Connection.Events.MatchStartEvent;

import javafx.application.Platform;

public class LobbyController extends AbstractController
{	
		private Connection connection;

		public LobbyController(Connection connection)
		{	
			LobbyView view = new LobbyView();
			view.setOnQuickPlayButtonPressHandler(this);
			view.setOnInviteButtonPressHandler(this);
			view.setOnRefreshButtonPressHandler(this);
			
			this.view = view;
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
		
		public ArrayList onRefreshButtonPress()
		{
			 connection.updatePlayerList();
			 System.out.println("hot");
			 return connection.getPlayerList();
			
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
