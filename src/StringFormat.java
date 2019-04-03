import java.util.ArrayList;

public class StringFormat {
	
	public static String[] stringToArray(String string) {
		string = string.substring(1, string.length()-1).replace("\"", "");
		
		return string.split(", " );
	}

}
