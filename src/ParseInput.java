
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
		
		
		
		//	Checks for a bad input
		if(!containsAnyPrimary(cleanInput, keys))
			return new String[] {"", ""};
		
		
		//	Checks the input for text matching a primary and a secondary key
		for(String delim : delimInput)
			for(Key key : keys) {
				String primary = key.getPrimary();
				String secondary = key.getSecondary();
			
				//	Java Strings are very silly, so this double .contains are used to ensure they equal
				//		The == oprator rarely works properly with Strings
				if(delim.contains(primary) && primary.contains(delim))
					out[0] = delim;
				
				if(delim.contains(secondary) && secondary.contains(delim))
					out[1] = delim;
			}
		
		
		
		//	By the earlier bad input check, this error shouldn't ever be thrown. It's here just in case
		if(out[0] == null)
			throw new Error("Uh oh, no primary key!");
		
		
		//	Sets the default response to the secondary key  should nothing be entered
		if(out[1] == null)
			out[1] = "generic";
		
		
		
		return out;
	}
	
	
	
	
	
	//	Checks if a String contains at least one item from a String array
	public static boolean containsAny(String str, ArrayList<String> toCheck) {
		
		boolean contains = false;
		
		for(String check : toCheck)
			if(str.contains(check))
				contains = true;
		
		
		return contains;
	}
	
	public static boolean containsAnyPrimary(String str, ArrayList<Key> toCheck) {
		ArrayList<String> strs = new ArrayList<String>();
		
		for(Key primary : toCheck)
			strs.add(primary.getPrimary());
		
		return containsAny(str, strs);
	}
	
	public static boolean containsAnySecondary(String str, ArrayList<Key> toCheck) {
		ArrayList<String> strs = new ArrayList<String>();
		
		for(Key secondary : toCheck)
			if(!strs.contains(secondary.getSecondary()))
				strs.add(secondary.getSecondary());
		
		return containsAny(str, strs);
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
