
import java.util.Scanner;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.ArrayList;

//	This class intends to parse the user input and extract the necessary keys
public class ParseInput {
	
	
	//	Grabs the text, then parses it
	public static String[] getInput(Scanner scanner, ArrayList<Key> keys, StanfordCoreNLP pipeline) {
		
		String input = scanner.nextLine();
		
		return parseInput(input, keys, pipeline);
		
	}
	
	
	
	//	Given a single String input, returns a String array of legnth 2 containing both keys
	//		If no key could be found, the String will be empty
	public static String[] parseInput(String input, ArrayList<Key> keys, StanfordCoreNLP pipeline) {
		
		//	Declares the String
		String[] out = new String[2];
		
		
		//	Cleans the String
		String cleanInput = cleanString(input);
		
		
		
		//	If the users wants to search a specific term, sets up the appropriate keys
		if(cleanInput.contains("what is ") || cleanInput.contains("what are")) {
			
			//	Gets the position of the substring that comprises the search
			int len = 0;
			if(cleanInput.contains("what is "))
				len = "what is ".length();
			else if(cleanInput.contains("what are"))
				len = "what are ".length();
			
			
			//	Sets the keys
			out[0] = "search";
			out[1] = cleanInput.substring(cleanInput.indexOf("what are") + len);
			
			
			//	Removes whitespace from the search
			//		URL, as used by the API, can't contain whitespace
			//		WolframAPI uses '+' as the space character
			while(out[1].contains(" "))
				out[1] = out[1].replace(' ', '+');
			
			
			return out;
		}
		
		
		//	If the users wants a definition via wikipedia
		if(cleanInput.contains("define ")){
			
			//	Gets the position of the substring
			int len = "define ".length();
			
			
			//	Sets the keys
			out[0] = "define";
			out[1] = cleanInput.substring(cleanInput.indexOf("define ") + len);
			
			
			//	Removes whitespace from the search
			//		URL, as used by the API, can't contain whitespace
			//		WikiAPI uses "%20" as the space character
			while(cleanInput.indexOf(' ') != -1) {
				int wIndex = cleanInput.indexOf(' ');
				cleanInput = cleanInput.substring(0, wIndex) + "%20" + cleanInput.substring(wIndex + 1);
			}
			
			return out;
		}
		
		
		//	Deliminate the String into an array via a space
		String[] delimInput = cleanInput.split(" ");
		
		
		
		
		
		//	Checks the input for text matching a primary and a secondary key
		for(String delim : delimInput)
			for(Key key : keys) {
			
				//	Java Strings are very silly, so this double .contains are used to ensure they equal
				//		The == operator rarely works properly with Strings
				if(matchesPrimary(delim, key))
					out[0] = key.getPrimary();
				
				//Checks if input contains a name and returns a boolean value
				//If input contains a name and sets primary key as "human"
				if(NamedEntity.getNamedEntity(input, pipeline)) {
					out[0]= "human";
				}
				
				//	Likewise for negative sentiment
				if(SentimentAnalysis.isNegative(input, pipeline)) {
					out[0]= "negative";
					
					//	Sets the secondary key to generic as there only is one response
					out[1] = "generic";
				}
				
				if(matchesSecondary(delim, key))
					out[1] = key.getSecondary();
			}
		
	
	
		
		//	By the earlier bad input check, this error shouldn't ever be thrown. It's here just in case
		if(out[0] == null)
			out[0] = "generic";
		
		
		//	Sets the default response to the secondary key  should nothing be entered
		if(out[1] == null)
			out[1] = "generic";
		
	    
		return out;
	}
	
	
	
	//	Given some Strings, see if they are equal
	//		Uses "one.contains(two) && two.contains(one)" as the == operator DOES NOT WORK WELL with Strings
	public static boolean matches(String one, String two) {
		return one.contains(two) && two.contains(one);
	}
	
	//	Same as above, but so long as the String matches at least one of the primary key or any of its synonyms
	public static boolean matchesPrimary(String str, Key key) {
		boolean contains = false;
		
		//	Checks the primary key itself
		if(matches(str, key.getPrimary()))
			contains = true;
			
		
		//	Checks each of the synonyms
		for(String check : key.getSynonyms())
			if(str.contains(check) && check.contains(str))
				contains = true;

		
		return contains;
	}
	
	public static boolean matchesSecondary(String str, Key key) {		
		return matches(str, key.getSecondary()); 
	}
	
	
	
	//	Cleans the input String
	//		Does so by replacing several characters with a space and sets the casing
	//		Then splits the input into arrays via the space
	public static String cleanString(String input) {
		
		//	A String array containing chars
		//		...yeah, you read that correct
		//		They're 'Strings' because String.replace requires a String inputs
		String[] badChars = new String[] {
				".",
				",",
				"?",
				"!",
				"'",
				"(",
				")"
		};
		
		
		for(String badChar : badChars)
			input = input.replace((String) badChar, " ");
		
		
		//	Makes the String a bit easier to parse
		input = input.toLowerCase();
		input = input.stripTrailing();
		
		return input;
		
	}

}
