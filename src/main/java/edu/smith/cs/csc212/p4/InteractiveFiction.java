package edu.smith.cs.csc212.p4;

import java.util.List;

/**
 * This is our main class for P4. It interacts with a GameWorld and handles user-input.
 * @author jfoley
 *
 */
public class InteractiveFiction {

	/**
	 * This is where we play the game.
	 * @param args
	 */
	public static void main(String[] args) {
		// This is a text input source (provides getUserWords() and confirm()).
		TextInput input = TextInput.fromArgs(args);

		// This is the game we're playing. CAN CHANGE FROM SPOOKY MANSION. 
		GameWorld game = new BassHall();
		
		// This is the current location of the player (initialize as start).
		// Maybe we'll expand this to a Player object.
		String place = game.getStart();

		// Play the game until quitting.
		// This is too hard to express here, so we just use an infinite loop with breaks.
		while (true) {
			
			// Give the player a hint that they should search the places. 
			System.out.println("Heads-Up: When in doubt, always search the places for hidden exits!\nType \"search\" and hit enter to reveal any secret exits if present!\n");
			// Print the description of where you are.
			Place here = game.getPlace(place);
			System.out.println(here.getDescription());

			// Game over after print!
			if (here.isTerminalState()) {
				break;
			}

			// Show a user the ways out of this place.
			List<Exit> exits = here.getVisibleExits();
			
			for (int i=0; i<exits.size(); i++) {
			    Exit e = exits.get(i);
				System.out.println(" ["+i+"] " + e.getDescription());	
			}
			
			// if a certain place has items laying around then show them
			if (here.hasKeys && (!here.hasCollected)) {
				System.out.println("");
				for(int i = 0; i < here.getKeys().size(); i++) {
					List<String> theKeys = here.getKeys(); 
					System.out.println("["+ (i+1) + "]" + " Item found: " + theKeys.get(i));
					System.out.println("\nIf you want to collect them: type \"take\" and hit enter.\n");	
					}
			
			}
			

			// Figure out what the user wants to do, for now, only "quit" is special.
			List<String> words = input.getUserWords(">");
			if (words.size() == 0) {
				System.out.println("Must type something!");
				continue;
			} else if (words.size() > 1) {
				System.out.println("Only give me 1 word at a time!");
				continue;
			}
			
			// Get the word they typed as lowercase, and no spaces.
			String action = words.get(0).toLowerCase().trim();
			
			// search for exits in this place on user's command
			if(action.equals("search")) {
				if(input.confirm("Ready to search the place top-down?")) {
					here.search();
					continue;
				}
			}
			
			// if there are keys in a certain place give the option to the user to retrieve them.
			if (action.equals("take")) {
				if(input.confirm("Are you sure you want to add the item/s to your inventory?")) {
					BassHall bass = (BassHall) game;
					bass.getStuff(here.getId());
					continue;
				}
			}
			
			// print all the keys that you have in your inventory. 
			if (action.equals("stuff")) {
				if(input.confirm("Display the keys in your inventory?")) {
					BassHall bass = (BassHall) game;
					bass.printStuff();
					continue;
				}
			}
			
			
			if (action.equals("quit") || action.equals("q") || action.equals("escape")) {
				if (input.confirm("Are you sure you want to quit?")) {
					break;
				} else {
					continue;
				}
			}
			
			
			// From here on out, what they typed better be a number!
			Integer exitNum = null;
			try {
				exitNum = Integer.parseInt(action);
			} catch (NumberFormatException nfe) {
				System.out.println("That's not something I understand! Try a number!");
				continue;
			}
			
			if (exitNum < 0 || exitNum > exits.size()) {
				System.out.println("I don't know what to do with that number!");
				continue;
			}

			// Move to the room they indicated. If it is a locked exit, see if you have the "stuff" for it. 
			Exit destination = exits.get(exitNum);
			if (destination instanceof LockedExit) {
				BassHall bass = (BassHall) game;
				if (destination.getTarget() == "youngLibrary") {
					if(bass.stuff.contains("OneCard")) {
						place = destination.getTarget();
					} else {
						System.out.println("\nYou do not have item \"OneCard\" required to enter this place.\n");
					}
				}
				if (destination.getTarget() == "jordansOffice") {
					if(bass.stuff.contains("Laptop")) {
						place = destination.getTarget();
					} else {
						System.out.println("\nYou do not have item \"Laptop\" required to enter this place.\n");
					}
				}
			} else {
				place = destination.getTarget();
			}
			
			
			
		}

		// You get here by "quit" or by reaching a Terminal Place.
		System.out.println(">>> GAME OVER <<<");
	}

}
