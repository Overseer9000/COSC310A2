
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class Startup {
	
	
	//	Starts the program up, then enters the primary loop
	//		Mostly loads the Dictionary into active memory
	public static void main(String args[]) {
		
		// This section will have everything required for startup
		//		Which includes the loading of the Dictionary
		
		//	Reads the dictionary file and puts each entry into a HashMap
		HashMap<String, HashMap<String, String>>	dict = ReadFile.loadDict("./src/Dictionary.txt");
		
		//	Puts the keys into an ArrayList
		ArrayList<Key>				 				keys = ReadFile.loadKeys("./src/Dictionary.txt");
		
		
		//	Gets the pipleine so it doens't have to make multiple calls to it
		StanfordCoreNLP pipeline = Pipeline.getPipeline();
		
		
		//	Enters the primary loop
		mainLoop(dict, keys, pipeline);
		
	}
	
	
	//	This loop will call functions to read input and produce output
	public static void mainLoop(HashMap<String, HashMap<String, String>> dict, ArrayList<Key> keys, StanfordCoreNLP pipeline) {
		
		
		//	Used to know when to exit the primary loop
		boolean go = true;
		
		
		//	Scanner temp
		Scanner scanner = new Scanner(System.in);
		
		
		
		//	The primary loop
		while(go) {
			
			
			//	Does some formatting
			System.out.print("User > ");
			
			
			//	Fetches the keys from the user's input
			String input[] = ParseInput.getInput(scanner, keys, pipeline);
			
			
			//	Fetches the appropriate response
			String response = fetchResponse(input[0], input[1], dict);
			
			
			//	Prints the response out
			System.out.println(response);
			
		}
		
		
		//	Closes the input
		scanner.close();
		
	}
	
	
	//	Stitches together an input
	//		Mainly just puts the two keys into the HashMap to get a response
	//		But handles some formatting & error catching
	public static String fetchResponse(String keyOne, String keyTwo, HashMap<String, HashMap<String, String>> dict) {
		
		
		String out = "\nOS > ";
		
		if(keyOne == "generic")
			return out + "Hm, it seems I was unable to understand what you said.";
		
		if(dict.get(keyOne) == null)
			return out + "Sorry, I didn't understand \"" + keyOne + "\".";
		
		if(dict.get(keyOne).get(keyTwo) == null)
			return out + "Sorry, I didn't understand \"" + keyTwo + "\" in this context.";
		
		return out + dict.get(keyOne).get(keyTwo);
	}
}
