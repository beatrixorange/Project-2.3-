package Connection;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Connection {
	
	private Socket socket;
	private BufferedReader reader;
	private BufferedWriter writer;
	private InputStreamReader input;
	private OutputStreamWriter output;
	private boolean loggedIn;
	private boolean subscribed;
	private ArrayList challengers;
	private String[] playerList;
	private String[] gameList;
	private String loggedinU;
	private boolean called;
	
	public Connection() {
		called = false;
		loggedIn = false;
		subscribed = false;
		challengers = new ArrayList();
		gameList = null;
		playerList = null;
	}
	
	public void connect() throws UnknownHostException, IOException {
		socket = new Socket("localhost", 7789);
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
							if(line.contains("SVR PLAYERLIST")) {
								String[] a = StringFormat.stringToArray(line.substring("SVR PLAYERLIST ".length()));
								playerList = a;	
							}
							if(line.contains("SVR GAMELIST")) {
								String[] b = StringFormat.stringToArray(line.substring("SVR GAMELIST ".length()));
								gameList = b;
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
    		loggedinU = username;
    		loggedIn = true;
    	}
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
    	if(subscribed = false) {
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
    	sendCommand("challenge " + player  + " " + gameType);
    }
    
    public void acceptChallenge(int challengeNumber) {
    	sendCommand("challenge accept " + challengeNumber);
    }
    
    public String[] getGameList() {
    	sendCommand("get gamelist");
    	return gameList;
    			
    }
    
    public String[] getPlayerList() {
        sendCommand("get playerlist");
    	return playerList;

    }
	
	
	public static void main(String args[]) throws UnknownHostException, IOException {
		Connection x = new Connection();
		x.connect();
	}
	
	
 
} 