package Connection;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.SocketException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

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

// The Connection class does all the communication with the server and does communication with other classes in the project
// When the server sends a specific command this class catches it and sends an object of the type Event containing data specific to the event.
public class Connection extends Registrator {
	
	private Socket socket;
	private BufferedReader reader;
	private BufferedWriter writer;
	private InputStreamReader input;
	private OutputStreamWriter output;
	private boolean loggedIn;
	private boolean subscribed;
	private boolean loginEventTriggered;
	private HashMap<Integer, String> challengers;
	private ArrayList<String> playerList;
	private ArrayList<String> gameList;
	private String loggedUsername;
	private char quote;

	
	public Connection()  {
		loggedIn = false;
		subscribed = false;
		challengers = new HashMap<Integer, String>();
		gameList = new ArrayList<String>();
		playerList = new ArrayList<String>();
		loginEventTriggered = false;
		quote = '"';// Found this easier to work than the official way to insert quotes in Strings.
	}
	// create new socket for connection and create new input/output stream and reader/writers
	public void connect(String ipAdress) throws UnknownHostException, IOException {
		socket = new Socket(ipAdress, 7789);
		input = new InputStreamReader(socket.getInputStream());
		output = new OutputStreamWriter(socket.getOutputStream());
		reader = new BufferedReader(input);
		writer = new BufferedWriter(output);
		receive();
			
	}
	// multithreaded function that listens to the server, splits the strings and triggers events 
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
								triggerEvent(new LoginSuccesEvent()); // succesfully loggedin
								loginEventTriggered = true;
							}
							if(line.contains("SVR PLAYERLIST")) {
								ArrayList a = StringFormat.stringToArray(line.substring("SVR PLAYERLIST ".length())); // using a string editing function to add players to an arraylist.
								playerList = a;	
								triggerEvent(new UpdatedPlayerListEvent());//Send notification to all registered classes that the playerlist has been updated.
							}
							if(line.contains("SVR GAMELIST")) {
								ArrayList b = StringFormat.stringToArray(line.substring("SVR GAMELIST ".length()));
								gameList = b;
							}
							if(line.contains("SVR GAME CHALLENGE {CHALLENGER")) {
								// If another player challenges us the player's username and the challengenumber will be passed to the ChallengedEvent object
								String challenger = StringFormat.stringFormat(line.substring("SVR GAME CHALLENGE {CHALLENGER: ".length())); // same string editing function but for strings instead of arrays.
								System.out.println(challenger);
								String t = "SVR GAME CHALLENGE {CHALLENGER: " + challenger + ", CHALLENGENUMBER: ";
								String challengeNum = StringFormat.stringFormat(line.substring(t.length()));
								challengeNum = challengeNum.replace(" ", "");
								String t2 = "SVR GAME CHALLENGE {CHALLENGER: " + challenger + ", CHALLENGENUMBER: " + challengeNum + ", GAMETYPE: ";
								String gameType = StringFormat.stringFormat(line.substring(t2.length()+3));
								challengers.put(Integer.parseInt(challengeNum), challenger);
								triggerEvent(new ChallengedEvent(challenger, gameType, Integer.parseInt(challengeNum)));
							}
							if(line.contains("SVR GAME CHALLENGE CANCELLED")) {
								// If a challenged gets cancelled send a notification to all registered classes with the challengenumber passed to it.
								String challengeNumC = StringFormat.stringFormat(line.substring("SVR GAME CHALLENGE CANCELLED {CHALLENGENUMBER: ".length()));
								challengers.remove(Integer.parseInt(challengeNumC));
								triggerEvent(new ChallengeCancelledEvent(Integer.parseInt(challengeNumC)));
							}
							if(line.contains("SVR GAME YOURTURN")) {
								// send a YourMoveEvent
								triggerEvent(new YourMoveEvent());
							}
							if(line.contains("SVR GAME MOVE")) {
								// If a player makes a move send a TurnEvent containing the move and the player who made the move
								String player = StringFormat.stringFormat(line.substring("SVR GAME MOVE {PLAYER: ".length()));
								String b = "SVR GAME MOVE {PLAYER: " + player + ", MOVE: ";
								String move = StringFormat.stringFormat(line.substring(b.length()));
								move = move.replace(" ","");
								triggerEvent(new TurnEvent(player, Integer.parseInt(move)));
							}
							if(line.contains("SVR GAME MATCH")) {
								// When a match starts send a MatchStartEvent to all registered classes with the playerToMove, gameType and opponent in it.
								String playerToMove = StringFormat.stringFormat(line.substring("SVR GAME MATCH {PLAYERTOMOVE: ".length()));
								String t = "SVR GAME MATCH {PLAYERTOMOVE: " + playerToMove + ", GAMETYPE: "; 
								String gameType = StringFormat.stringFormat(line.substring(t.length()));
								gameType = gameType.replace(" ","");
								String t2 = "SVR GAME MATCH {PLAYERTOMOVE: " + playerToMove + ", GAMETYPE: " + gameType + ", OPPONENT: ";
								String opponent = StringFormat.stringFormat(line.substring(t2.length()+3));
								//opponent = opponent.replace(" ","");
								System.out.println(opponent);
								System.out.println(playerToMove);
								//triggerEvent(new MatchStartEvent(playerToMove, gameType, opponent));
							}
						
							if(line.contains("WIN") || line.contains("LOSS") || line.contains("TIE")) {
								// Send the match finished score to all registered classes
								subscribed = false;
								if(line.contains("WIN")){
									String playerOneScore = StringFormat.stringFormat(line.substring("SVR GAME WIN {PLAYERONESCORE: ".length()));
									String t = "SVR GAME WIN {PLAYERONESCORE: " + playerOneScore + ", PLAYERTWOSCORE: ";
									String playerTwoScore = StringFormat.stringFormat(line.substring(t.length()));
									playerTwoScore = playerTwoScore.replace(" ", "");
									triggerEvent(new MatchWonEvent(Integer.parseInt(playerOneScore), Integer.parseInt(playerTwoScore)));
								}
								if(line.contains("LOSS")){
									String playerOneScore = StringFormat.stringFormat(line.substring("SVR GAME LOSS {PLAYERONESCORE: ".length()));
									String t = "SVR GAME LOSS {PLAYERONESCORE: " + playerOneScore + ", PLAYERTWOSCORE: ";
									String playerTwoScore = StringFormat.stringFormat(line.substring(t.length()));
									playerTwoScore = playerTwoScore.replace(" ", "");
									triggerEvent(new MatchLostEvent(Integer.parseInt(playerOneScore), Integer.parseInt(playerTwoScore)));
								}
								if(line.contains("TIE")){
									String playerOneScore = StringFormat.stringFormat(line.substring("SVR GAME DRAW {PLAYERONESCORE: ".length()));
									String t = "SVR GAME DRAW {PLAYERONESCORE: " + playerOneScore + ", PLAYERTWOSCORE: ";
									String playerTwoScore = StringFormat.stringFormat(line.substring(t.length()));
									playerTwoScore = playerTwoScore.replace(" ", "");
									triggerEvent(new MatchTiedEvent(Integer.parseInt(playerOneScore), Integer.parseInt(playerTwoScore)));
								}
							}
							
							
							
						}
			
		
						
					} catch (SocketException e) {
						System.out.println(e.getMessage());
						return;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
							
				}
			}
		});
		receive.start();
	
	}
	// functions sends commands to the server
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
    // login function
    public void login(String username) {
    	if(loggedIn == false) {
    		sendCommand("login " + username);
    		loggedIn = true;
    		loggedUsername = username;
    	}
    }
    
    // getter for username
    public String getUsername() {
    	return loggedUsername;
    }
    
    //logout function
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
    
    //function to subscribe to a game
    public void subscribe(String gameType) {
    	if (subscribed == false) {
    		sendCommand("subscribe " + gameType);
    		subscribed = true;
    	}
    }
    
    // setter for boolean subscribed
    public void setSubscribed(boolean subscribed) {
    	this.subscribed = subscribed;
    }
    
    //getter for subscribed
    public boolean getSubscribed() {
    	return subscribed;
    }
    
    
   //make move function to tell the server to make a move
    public void makeMove(int move) {
    	sendCommand("move " + move);
    }
    
    // function to send a challenge to a specific player in a specific game
    public void sendChallenge(String player, String gameType) {
    	sendCommand("challenge "  + quote + player + quote + " " + quote + gameType + quote);
    }
    
    // function to accept a challenge
    public void acceptChallenge(int challengeNumber) {
    	sendCommand("challenge accept " + challengeNumber);
    }
    
    //send command to server to update the game list
    public void updateGameList() {
    	sendCommand("get gamelist");  			
    }
    
    //returns an arraylist containing all the games
    public ArrayList getGameList() {
    	return gameList;
    }
    
    // send command to server to update the playerlist
    public synchronized void updatePlayerList() {
        sendCommand("get playerlist");
    }
    
    // returns an arraylist containing all the players
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
