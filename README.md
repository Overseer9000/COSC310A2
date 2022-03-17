# COSC310A2
OVERSEER chat bot 
This is the spaceship chat bot, speaking and guiding our crew aboard the Blue Dwarf.

It was created by Skyler Alderson, Robert Barnstead, Abhiek Bist, and Livia Zalilla (as well as David Brideson before they dropped the class).


# Code Description
## Class: Startup
The Startup class loads the Dictionary into a HashMap, then enters the primary loops for grabbing the user's input before replying via calling functions from the classes below.


## Class: ReadFile
The ReadFile class loads a text file. Depending on the function called, it will either return a nested HashMap containing the replies the OVERSER can make, or a list of keys with their synonyms (used for parsing user input) in the form of an ArrayList of Keys. The text file is custom formatted to make reading it easier.


## Class: ParseInput
The ParseInput class contains several functions used in gathering, cleaning, and extracting the keys from the user's input. After gathering a String from the user, it will deliminate it into an array of Strings where each data item is a singlular word. It will clean each item in the String array by removing problematic punctuation and whitespace. Finally, each word is compared to a list of keys; if there is a match, the key is returned.


## Class: Key
A Key contains two Strings and an ArrayList of Strings. The two Strings contain the primary and secondary keys used in the response hashmap, and the ArrayList contains all synonyms used for the primary key. The purpose of this class is to package up all information about keys into one place to make parsing text within ParseInput easier.

## Class: NamedEntity
Recognizes named entities (person and company names, etc.) in text. Principally, this annotator uses machine learning sequence models to label entities. The NamedEntity class implements stanford's Named Entity Recognition API to our chatbot. This class takes the input typed by a user and looks for a person's name.

example: User > my name is Bob
         OS > hello human
         User > Where is Dave
         OS > Sorry I can not disclose their location.


# Class: SentimentAnalysis
Recognizes the sentiment of text using Socher et al’s sentiment model. This class takes input and checks if the sentiment behind our input is negative or not. If the sentiment is determined to be negative, Overseer responds with a generic response acknowledging the user's frustation.

example: User > I am angry / User > I hate this place
         OS > I understand your frustration, please let me help you.
         

## Text file: Dictionary
A custom formatted text file that contains an important word with a colon seperating it from its synonyms. The following indented lines give the sub-key (a "W" such as who, what, where...) where a colon then seperates it from the response.
