
import java.util.Scanner;
import java.util.ArrayList;

//	This class intends to parse the user input and extract the necessary keys
public class ParseInput {
	
	
	//	Grabs the text, then parses it
	public static String[] getInput(Scanner scanner,ArrayList<Key> keys) {
		
		String input = scanner.nextLine();
		
		return parseInput(input, keys);
		
	}
	
	
	
	//	Given a single String input, returns a String array of legnth 2 containing both keys
	//		If no key could be found, the String will be empty
	public static String[] parseInput(String input, ArrayList<Key> keys) {
		
		//	Declares the String
		String[] out = new String[2];
		
		
		//	Cleans the String
		String cleanInput = cleanString(input);
		
		//	Deliminate the String into an array via a space
		String[] delimInput = cleanInput.split(" ");
		
		
		
		
		//	Checks the input for text matching a primary and a secondary key
		for(String delim : delimInput)
			for(Key key : keys) {
			
				//	Java Strings are very silly, so this double .contains are used to ensure they equal
				//		The == oprator rarely works properly with Strings
				if(matchesPrimary(delim, key))
					out[0] = key.getPrimary();
				
				
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
	
	
	
	
	public static boolean matches(String one, String two) {
		return one.contains(two) && two.contains(one);
	}
	
	public static boolean matchesPrimary(String str, Key key) {
		boolean contains = false;
		
		if(matches(str, key.getPrimary()))
			contains = true;
			
		
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
				"'"
		};
		
		
		for(String badChar : badChars)
			input = input.replace((String) badChar, " ");
		
		input = input.toLowerCase();
		
		return input;
		
	}

}
