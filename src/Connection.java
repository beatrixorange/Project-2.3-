import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connection {
	
	private Socket socket;
	private BufferedReader reader;
	private BufferedWriter writer;
	private InputStreamReader input;
	private OutputStreamWriter output;
	private boolean loggedIn;
	
	public Connection() {
		loggedIn = false;
	}
	
	public void connect() throws UnknownHostException, IOException {
		socket = new Socket("localhost", 7789);
		input = new InputStreamReader(socket.getInputStream());
		output = new OutputStreamWriter(socket.getOutputStream());
		reader = new BufferedReader(input);
		writer = new BufferedWriter(output);
		receive();
			
	}
	
	public void receive() {	
		while(true) {
			String line;
			try {
				line = reader.readLine();
				System.out.println(line);
				login("willem");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
		}
		
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
    	}
    }
	
	public static void main(String args[]) throws UnknownHostException, IOException {
		Connection x = new Connection();
		x.connect();
	}
	
	
 
} 