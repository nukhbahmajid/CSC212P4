package edu.smith.cs.csc212.p4;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BassHall implements GameWorld{
	
	private Map<String, Place> places = new HashMap<>();
	
	/**
	 * The values isolated from the map, into a values list of data type Place
	 */
	private List<Place> placesList = new ArrayList<>(); 
	
	/**
	 * Get all the item keys from ALL the places
	 */
	private List<String> allKeys = new ArrayList<String>();
	
	/**
	 * The items that the user has collected. 
	 */
	private List<String> stuff = new ArrayList<String>();
	
	
	/** 
	 * Where should the player start? 
	 */
	@Override
	public String getStart() {
		return "entranceHall";
	}
	
	public BassHall() {
		
		
		
		Place entranceHall = insert(Place.create("entranceHall",
				"You instantly recognize this place - it's the entrance hallway of the damned Bass Hall.\nThe door out should no longer work for you till you get your shit together for finals.\n"));
		entranceHall.addExit(new Exit("hallway", 
				"You see a hallway on your left. Enter the hallway."));
		entranceHall.addExit(new Exit("youngLibrary", "You sense a familiar place. This is a trap...you know it! Why are you trying to go here??"));
		entranceHall.addExit(new Exit("elevator", "There's an elevator to your right."));
		entranceHall.addExit(new Exit("bathrooms", "There's a door right next to the elevator."));
		
		
		
		Place hallway = insert(Place.create("hallway", "Clearly psych department has done an outstanding job in simulating a psych ward's gloomy hallways."));
		hallway.addExit(new Exit("entranceHall", "Retreat back to the entrance hall."));
		hallway.addExit(new Exit("jordansOffice", "You have an epiphany. Your head's a lot clearer now. Perhaps, you're headed in the right direction. Go on?"));
		hallway.hasKeys = true;
		hallway.putKey("OneCard");
		
		
		
		Place youngLibrary = insert(Place.create("youngLibrary", "It's Young Library." +
		" This place has been your makeshift home for several nights through all these Smith years. \n" + "...Something feels off"));
		youngLibrary.addExit(new Exit("entranceHall", "Retreat back to the entrance hall."));
		youngLibrary.addExit(new SecretExit("specialCollections", "A secluded door inside the library beckons you. Perhaps this is not the best time to be distracted..?"));
		
		
		
		Place bathrooms = insert(Place.create("bathrooms", "Ughhhh...of all places you're stuck in a stinky bathroom...")); 
		bathrooms.addExit(new Exit("entranceHall", "Go back to the entrance."));
		
		
		
		Place elevator = insert(Place.create("elevator", "Strange...the elevator goes to the 1st & 2nd floors only.\n" + "Finals have whacked your mind.\n" + "Do you even need to take an elevator?\n"));
		elevator.addExit(new Exit("entranceHall", "Get out of the elevator."));
		elevator.addExit(new Exit("readingRoom", "Take the elevator up to the 2nd floor. Exit the hall at your second left."));
		
		
		
		Place specialCollections = insert(Place.create("specialCollections", "This is the special collections room.\n" +  "You clearly don't belong here!\n" + "You haven't taken a non-STEM class in your life!\n"
				+ "EDIT HERE - ONE WAY: There's no way back."));
		specialCollections.hasKeys = true;
		specialCollections.putKey("Laptop"); 
		
		
		
		// lead by a one way path. it is a dead end. can't go back. game over
		Place readingRoom = insert(Place.terminal("readingRoom", "Oh no. This is the reading room.\nAll your friends are here, and you end up talking for hours. \n" + 
		"Wallow in self-loathing!!!\nNothing can save you from finals now!"));
		
		// game over, you win. 
		Place jordansOffice = insert(Place.terminal("jordansOffice", "You've reached Jordan's Office!\n" + "CSC250 is hard, but at least there's hope now!\n" + "Let's get done with finals once and for all!"));
		
		
		checkAllExitsGoSomewhere();
		
		for (Map.Entry<String, Place> place : places.entrySet()) {
			placesList.add(place.getValue());
		}
		
		for (Place eachPlace : placesList) {
			for (int i = 0; i < eachPlace.getKeys().size(); i++) {
				List<String> keysFromPlace = eachPlace.getKeys();
				allKeys.add(keysFromPlace.get(i));
			}
		}	
		
		
	}
	
	private Place insert(Place p) {
		places.put(p.getId(), p);
		return p; 
	}
	
	/**
	 * I like this method for checking to make sure that my graph makes sense!
	 */
	private void checkAllExitsGoSomewhere() {
		boolean missing = false;
		// For every place:
		for (Place p : places.values()) {
			// For every exit from that place:
			for (Exit x : p.getVisibleExits()) {
				// That exit goes to somewhere that exists!
				if (!places.containsKey(x.getTarget())) {
					// Don't leave immediately, but check everything all at once.
					missing = true;
					// Print every exit with a missing place:
					System.err.println("Found exit pointing at " + x.getTarget() + " which does not exist as a place.");
				}
			}
		}
		
		// Now that we've checked every exit for every place, crash if we printed any errors.
		if (missing) {
			throw new RuntimeException("You have some exits to nowhere!");
		}
	}

	/**
	 * Get a Place object by name.
	 */
	@Override
	public Place getPlace(String id) {
		return this.places.get(id);
	}
	
	
	/**
	 * Method prompted by user input to collect the key items found in the space
	 */
	public List<String> stuff(String placeid) {
		stuff.addAll(this.places.get(placeid).getKeys());
		return stuff;
	}

}
