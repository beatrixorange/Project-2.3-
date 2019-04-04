package Connection;
import java.util.ArrayList;
import java.util.Arrays;

public class StringFormat {
	
	public static ArrayList<String> stringToArray(String string) {
		string = string.substring(1, string.length()-1).replace("\"", "");
		ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(string.split(", " )));
		return arrayList;
	}

}
