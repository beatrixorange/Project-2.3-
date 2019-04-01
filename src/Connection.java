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
	
	public void connect() throws UnknownHostException, IOException {
		socket = new Socket("145.33.225.170", 7789);
		input = new InputStreamReader(socket.getInputStream());
		output = new OutputStreamWriter(socket.getOutputStream());
		reader = new BufferedReader(input);
		writer = new BufferedWriter(output);
		receive();
			
	}
	
	public void receive() throws IOException {
		Connection con = this;
		
		while(true) {
			String line = reader.readLine();
			System.out.println(line);
			
		}
		
	}
	
	public static void main(String args[]) throws UnknownHostException, IOException {
		Connection x = new Connection();
		x.connect();
	}
	
    public void sendCommand(String command) throws IOException {
        if (writer != null) {
            writer.write(command);
            writer.newLine();
            writer.flush();
            
        }
        else {
        	System.out.println("No connection");
        }

    }
	
 
} 