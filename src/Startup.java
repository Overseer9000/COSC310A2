
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;

import java.net.*;
import java.io.*;


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
			System.out.print("\nUser > ");
			
			
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
	
	
	//	Either fetches a response as defined in the dictionary or calls to Wolfram Alpha
	public static String fetchResponse(String keyOne, String keyTwo, HashMap<String, HashMap<String, String>> dict) {
		
		//	Performs a Wolfram Alpha search, if applicable
		//		Applicable is defined by the user inputting a String of the format "what is/are ?"
		if(keyOne.contains("search"))
			return apiResponse("http://api.wolframalpha.com/v2/query?input=" + keyTwo + "&appid=6RHKK7-QH2EG66896&format=plaintext");
		
		
		//	Performs a Wikipedia search, if applicable
		//		Applicable is defined by the user inputting a String of the format "define ?"
		if(keyOne.contains("define"))
			return apiResponse("https://en.wikipedia.org/w/api.php?action=query&list=search&srsearch=" + keyTwo + "&format=xml");
		
		
		//	Otherwise, returns a generic response
		return fetchGenericResponse(keyOne, keyTwo, dict);
		
	}
	
	
	//	Stitches together an input
	//		Mainly just puts the two keys into the HashMap to get a response
	//		But handles some formatting & error catching
	public static String fetchGenericResponse(String keyOne, String keyTwo, HashMap<String, HashMap<String, String>> dict) {
		String out = "\nOS > ";
		
		if(keyOne == "generic")
			return out + "Hm, it seems I was unable to understand what you said.";
		
		if(dict.get(keyOne) == null)
			return out + "Sorry, I didn't understand \"" + keyOne + "\".";
		
		if(dict.get(keyOne).get(keyTwo) == null)
			return out + "Sorry, I didn't understand \"" + keyTwo + "\" in this context.";
		
		return out + dict.get(keyOne).get(keyTwo);
	}
	
	
	//	Fetches the response using the passed URL
	public static String apiResponse(String url) {
		
		//	Creates the output
		String out = "\nOS > ";
		
		//	Fetches the page
		try {
			
			//	Gets the input stream
			InputStream feed = new URL(url).openStream();
			
			
			//	Converts the byte stream to a char stream
			//		Converts it to a String
			//		HOWEVER, is an XML file so that still need to be parsed
			while(feed.available() > 0)
				out += (char) feed.read();

			
			feed.close();
		}
		catch(Exception er) {
			//	If any error occurs, print it out
			out += "Uh-oh! There was an error trying to connect to Earth! Have you checked the planet hasn't been destroyed yet?";
			out += "\nOS > If earth is still intact, maybe this is the problem: " + er.toString();
		}
		
		
		return out;
		
	}
}
