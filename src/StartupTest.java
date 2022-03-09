import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

class StartupTest {

	
	//	Trries plenty of tests about fetching responses
	//		Namely a few regular cases, then a few generic keys / null keys
	@Test
	void testFetchResponse() {		
		
		//	I feel like this isn't proper practice with JUnit test, but I'm not sure of a better way
		HashMap<String, HashMap<String, String>> dict = ReadFile.loadDict("./src/Dictionary.txt");
		String generic = "generic";
		
		
		//	Test the generic case
		assertEquals("\nOS > Hm, it seems I was unable to understand what you said.", Startup.fetchResponse(generic, generic, dict));
		
		
		//	Tests an unknown key one
		assertEquals("\nOS > Sorry, I didn't understand \"verisimilitude\".", Startup.fetchResponse("verisimilitude", "generic", dict));
		
		
		//	Tests an unknown key two
		assertEquals("\nOS > Sorry, I didn't understand \"sociopath\".", Startup.fetchResponse("os", "sociopath", dict));
		
		
		//	Tests two unknown 
		
		
	}

}
