
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;

public class Startup {
	
	
	//	Starts the program up, then enters the primary loop
	public static void main(String args[]) {
		
		// This section will have everything required for startup
		//		Which includes the loading of the Dictionary
		
		//	Reads the dictionary file and puts each entry into a HashMap
		HashMap<String, HashMap<String, String>>	dict = ReadFile.loadDict("./src/Dictionary");
		
		//	Puts the keys into an ArrayList
		ArrayList<Key>				 			keys = ReadFile.loadKeys("./src/Dictionary");
		
		
		//	An example of how to fetch keys
		//		keys.keySet();
		
		//	An example of how to fetch a key's synonyms
		//		keys.get("help");
		
		
		//	Enters the primary loop
		mainLoop(dict, keys);
		
	}
	
	
	//	This loop will call functions to read input and produce output
	public static void mainLoop(HashMap<String, HashMap<String, String>> dict, ArrayList<Key> keys) {
		
		
		//	Used to know when to exit the primary loop
		boolean go = true;
		
		
		//	Scanner temp
		Scanner scanner = new Scanner(System.in);
		
		
		
		//	The primary loop
		while(go) {
			
			
			//	Does some formatting
			System.out.print("User > ");
			
			
			//	Fetches the keys from the user's input
			String input[] = ParseInput.getInput(scanner, keys);
			String keyOne = input[0];
			String keyTwo = input[1];
			
			//	If the user only enters one key, sets the second to be "generic"
			if(keyTwo == "")
				keyTwo = "generic";
			
			
			//	Fetches the appropriate response
			String response = fetchResponse(keyOne, keyTwo, dict);
			
			
			//	Prints the response out
			System.out.println(response);
			
		}
		
		
		//	Closes the input
		scanner.close();
		
	}
	
	
	public static String fetchResponse(String keyOne, String keyTwo, HashMap<String, HashMap<String, String>> dict) {
		
		
		String out = "\nOS > ";
		
		if(keyOne == "")
			return out + "Hm, it seems I was unable to understand what you said.";
		
		if(dict.get(keyOne) == null)
			return out + "Sorry, I didn't understand \"" + keyOne + "\".";
		
		if(dict.get(keyOne).get(keyTwo) == null)
			return out + "Sorry, I didn't understand \"" + keyTwo + "\".";
		
		return out + dict.get(keyOne).get(keyTwo);
	}
}
