package Connection;
import java.util.ArrayList;
import java.util.Arrays;

// This class helps with editing the strings that the server sends the connection class.
public class StringFormat {
	
	// function that removes all the characters from the server message and only puts the necessary information in the list.
	public static ArrayList<String> stringToArray(String string) {
		string = string.substring(1, string.length()-1).replace("\"", "");
		ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(string.split(", " )));
		return arrayList;
	}
	
	//Almost the same function but now for the strings
	public static String stringFormat(String s) {
		String[] a = s.split(",");
		String string = a[0];
		string = string.substring(1, string.length()-1).replace("\"", "");
		return string;
	}

}
