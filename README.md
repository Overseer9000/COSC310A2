# COSC310A2
OVERSEER chat bot 
This is the space station chat bot, speaking and guiding our crew.

It was created by Skyler Alderson, Robert Barnstead, Abhiek Bist, and Livia Zalilla (as well as David Brideson before they dropped the class).


# Code Description
## Class: Startup
The Startup class loads the Dictionary into a HashMap, then enters the primary loops for grabbing the user's input before replying. To reply, the OVERSEER parses the user input by extracting a pair of keys, which are used in a HashMap to retrieve a pregenerated response.

## Class: readFile
The readFile class loads a text file. Depending on the function called, it will either return a nested HashMap containing the replies the OVERSER can make, or a list of keys with their synonyms (used for parsing user input).

## Text file: Dictionary
A custom formatted text file that contains an important word with a colon seperating it from its synonyms. The following indented lines give the sub-key (a "W" such as who, what, where...) where a colon then seperates it from the response.
