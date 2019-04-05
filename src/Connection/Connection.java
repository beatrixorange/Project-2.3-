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
import Connection.Events.UpdatedPlayerListEvent;
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
		gameList = new ArrayList<String>();
		playerList = new ArrayList<String>();
		loginEventTriggered = false;
		quote = '"';
	}
	
	public void connect(String ipAdress) throws UnknownHostException, IOException {
		socket = new Socket(ipAdress, 7789);
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
						if(line != null && line.startsWith("OK") || line.startsWith("ERR") || line.startsWith("SVR")) {
							if(loggedIn == true && loginEventTriggered == false) {
								triggerEvent(new LoginSuccesEvent());
								loginEventTriggered = true;
							}
							if(line.contains("SVR PLAYERLIST")) {
								ArrayList a = StringFormat.stringToArray(line.substring("SVR PLAYERLIST ".length()));
								playerList = a;	
								triggerEvent(new UpdatedPlayerListEvent());
							}
							if(line.contains("SVR GAMELIST")) {
								ArrayList b = StringFormat.stringToArray(line.substring("SVR GAMELIST ".length()));
								gameList = b;
							}
							if(line.contains("SVR GAME CHALLENGE {CHALLENGER")) {
								String challenger = StringFormat.stringFormat(line.substring("SVR GAME CHALLENGE {CHALLENGER: ".length()));
								System.out.println(challenger);
								String t = "SVR GAME CHALLENGE {CHALLENGER: " + challenger + ", CHALLENGENUMBER: ";
								String challengeNum = StringFormat.stringFormat(line.substring(t.length()));
								challengeNum = challengeNum.replace(" ", "");
								System.out.println(challengeNum);
								String t2 = "SVR GAME CHALLENGE {CHALLENGER: " + challenger + ", CHALLENGENUMBER: " + challengeNum + ", GAMETYPE: ";
								String gameType = StringFormat.stringFormat(line.substring(t2.length()+3));
								System.out.println(gameType);
								triggerEvent(new ChallengedEvent(challenger, gameType, Integer.parseInt(challengeNum)));
							}
							if(line.contains("SVR GAME CHALLENGE CANCELLED")) {
								String challengeNumC = StringFormat.stringFormat(line.substring("SVR GAME CHALLENGE CANCELLED {CHALLENGENUMBER: ".length()));
								triggerEvent(new ChallengeCancelledEvent(Integer.parseInt(challengeNumC)));
							}
							if(line.contains("SVR GAME YOURTURN")) {
								triggerEvent(new YourMoveEvent());
							}
							if(line.contains("SVR GAME MOVE")) {
								String player = StringFormat.stringFormat(line.substring("SVR GAME MOVE {PLAYER: ".length()));
								String b = "SVR GAME MOVE {PLAYER: " + player + ", MOVE: ";
								String move = StringFormat.stringFormat(line.substring(b.length()));
								move = move.replace(" ","");
								triggerEvent(new TurnEvent(player, Integer.parseInt(move)));
							}
							if(line.contains("SVR GAME MATCH")) {
								String playerToMove = StringFormat.stringFormat(line.substring("SVR GAME MATCH {PLAYERTOMOVE: ".length()));
								String t = "SVR GAME MATCH {PLAYERTOMOVE: " + playerToMove + ", GAMETYPE: "; 
								String gameType = StringFormat.stringFormat(line.substring(t.length()));
								gameType = gameType.replace(" ","");
								String t2 = "SVR GAME MATCH {PLAYERTOMOVE: " + playerToMove + ", GAMETYPE: " + gameType + ", OPPONENT: ";
								String opponent = StringFormat.stringFormat(line.substring(t2.length()+2));
								opponent = opponent.replace(" ","");
								System.out.println(playerToMove);
								System.out.println(gameType);
								System.out.println(opponent);
								triggerEvent(new MatchStartEvent(playerToMove, gameType, opponent));
							}
							if(line.contains("SVR GAME COMMENT")){
								if(line.contains("WIN") || line.contains("LOSS") || line.contains("TIE")) {
									if(line.contains("WIN")){
										//SVR GAME WIN {PLAYERONESCORE: "0", PLAYERTWOSCORE: "0", COMMENT: "Turn timelimit reached"}
										triggerEvent(new MatchWonEvent());
									}
									if(line.contains("LOSS")){
										//SVR GAME LOSS {PLAYERONESCORE: "0", PLAYERTWOSCORE: "0", COMMENT: "Turn timelimit reached"}
										triggerEvent(new MatchLostEvent());
									}
									if(line.contains("TIE")){
										//SVR GAME DRAW {PLAYERONESCORE: "0", PLAYERTWOSCORE: "0", COMMENT: "Turn timelimit reached"}
										triggerEvent(new MatchTiedEvent());
									}
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
    	return playerList;
    }
	
	
	public static void main(String args[]) throws UnknownHostException, IOException {
		Connection x = new Connection();
		x.connect("localhost");
		x.login("kees");
		x.subscribe("Tic-tac-toe");
	}
	
	
 
} 
