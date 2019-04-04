package Connection;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import Connection.Events.ChallengeCancelledEvent;
import Connection.Events.ChallengedEvent;
import Connection.Events.ForfeitEvent;
import Connection.Events.LoginSuccesEvent;
import Connection.Events.MatchLostEvent;
import Connection.Events.MatchStartEvent;
import Connection.Events.MatchTiedEvent;
import Connection.Events.MatchWonEvent;
import Connection.Events.OpponentDisconnectedEvent;
import Connection.Events.TurnEvent;
import Connection.Events.YourMoveEvent;

public class Connection extends Registrator {
	
	private Socket socket;
	private BufferedReader reader;
	private BufferedWriter writer;
	private InputStreamReader input;
	private OutputStreamWriter output;
	private boolean loggedIn;
	private boolean subscribed;
	private boolean loginEventTriggered;
	private ArrayList challengers;
	private ArrayList playerList;
	private ArrayList gameList;
	private String loggedUsername;
	private char quote;
	
	public Connection()  {
		loggedIn = false;
		subscribed = false;
		challengers = new ArrayList();
		gameList = null;
		playerList = null;
		loginEventTriggered = false;
		quote = '"';
	}
	
	public void connect() throws UnknownHostException, IOException {
		socket = new Socket("145.33.225.170", 7789);
		input = new InputStreamReader(socket.getInputStream());
		output = new OutputStreamWriter(socket.getOutputStream());
		reader = new BufferedReader(input);
		writer = new BufferedWriter(output);
		receive();
			
	}
	
	public synchronized void receive() {	
		Thread receive = new Thread(new Runnable() {
			public void run() {
				while(true) {
					String line;
					try {
						line = reader.readLine();
						System.out.println(line);
						//login("kees");
						if(line != null && line.startsWith("OK") || line.startsWith("ERR") || line.startsWith("SVR")) {
							if(loggedIn == true && loginEventTriggered == false) {
								triggerEvent(new LoginSuccesEvent());
								loginEventTriggered = true;
							}
							if(line.contains("SVR PLAYERLIST")) {
								ArrayList a = StringFormat.stringToArray(line.substring("SVR PLAYERLIST ".length()));
								playerList = a;	
							}
							if(line.contains("SVR GAMELIST")) {
								ArrayList b = StringFormat.stringToArray(line.substring("SVR GAMELIST ".length()));
								gameList = b;
							}
							if(line.contains("SVR GAME CHALLENGE {CHALLENGER")) {
								triggerEvent(new ChallengedEvent());
							}
							if(line.contains("SVR GAME CHALLENGE CANCELLED")) {
								triggerEvent(new ChallengeCancelledEvent());
							}
							if(line.contains("SVR GAME YOURTURN")) {
								triggerEvent(new YourMoveEvent());
							}
							if(line.contains("SVR GAME MOVE")) {
								//uitvogelen hoe lang de string zonder speler is en dan uitzoeken waar we moeten substringen
								//triggerEvent(new TurnEvent());

							}
							if(line.contains("SVR GAME MATCH")) {
								triggerEvent(new MatchStartEvent());
							}
							if(line.contains("SVR GAME COMMENT")){
								if(line.contains("forfeited")) {
									triggerEvent(new ForfeitEvent());
								}
								if(line.contains("WIN") || line.contains("LOSS") || line.contains("TIE")) {
									if(line.contains("WIN")){
										triggerEvent(new MatchWonEvent());
									}
									if(line.contains("LOSS")){
										triggerEvent(new MatchLostEvent());
									}
									if(line.contains("TIE")){
										triggerEvent(new MatchTiedEvent());
									}
								}
								if(line.contains("disconnected")) {
									triggerEvent(new OpponentDisconnectedEvent());
								}
							}
							
							
						}
			
		
						
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
							
				}
			}
		});
		receive.start();
	
	}
	
    public void sendCommand(String command){
        if (writer != null) {
            try {
				writer.write(command);
				writer.newLine();
	            writer.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        }
        else {
        	System.out.println("No connection");
        }

    }
    
    public void login(String username) {
    	if(loggedIn == false) {
    		sendCommand("login " + username);
    		loggedIn = true;
    		loggedUsername = username;
    	}
    }
    
    public String getUsername() {
    	return loggedUsername;
    }
    
    public void logout() {
    	if(loggedIn == true) {
    		sendCommand("logout");
    		loggedIn = false;
    		try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    public void subscribe(String gameType) {
    	if (subscribed == false) {
    		sendCommand("subscribe " + gameType);
    		subscribed = true;
    	}
    }
    
    public void setSubscribed(boolean subscribed) {
    	this.subscribed = subscribed;
    }
    
    public boolean getSubscribed() {
    	return subscribed;
    }
    
    public void makeMove(int move) {
    	sendCommand("move " + move);
    }
    
    public void sendChallenge(String player, String gameType) {
    	char quote = '"';
    	sendCommand("challenge "  + quote + player + quote + " " + quote + gameType + quote);
    }
    
    public void acceptChallenge(int challengeNumber) {
    	sendCommand("challenge accept " + challengeNumber);
    }
    
    public void updateGameList() {
    	sendCommand("get gamelist");  			
    }
    public ArrayList getGameList() {
    	return gameList;
    }
    
    public synchronized void updatePlayerList() {
        sendCommand("get playerlist");
    }
    public synchronized ArrayList getPlayerList() {
    	String a = "henkie";
    	playerList.add(a);
    	return playerList;
    }
	
	
	public static void main(String args[]) throws UnknownHostException, IOException {
		Connection x = new Connection();
		x.connect();
	}
	
	
 
} 
