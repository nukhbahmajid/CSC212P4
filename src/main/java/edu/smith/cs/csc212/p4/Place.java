package edu.smith.cs.csc212.p4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This represents a place in our text adventure.
 * @author jfoley
 *
 */
public class Place {
	/**
	 * This is a list of places we can get to from this place.
	 */
	private List<Exit> exits;
	/**
	 * This is the identifier of the place.
	 */
	private String id;
	/**
	 * What to tell the user about this place.
	 */
	private String description;
	/**
	 * Whether reaching this place ends the game.
	 */
	private boolean terminal;
	
	/**
	 * Does this instance of place have keys?
	 */
	public boolean hasKeys;
	
	/**
	 * Has the player collected the keys from this place
	 */
	public boolean hasCollected; 
	
	
	/**
	 *  What are certain keys available in a place? Player should be able to retrieve them using another method. 
	 */
	private Map<Place, String> keysInPlaces = new HashMap<>();
	
	
	/**
	 * Internal only constructor for Place. Use {@link #create(String, String)} or {@link #terminal(String, String)} instead.
	 * @param id - the internal id of this place.
	 * @param description - the user-facing description of the place.
	 * @param terminal - whether this place ends the game.
	 */
	private Place(String id, String description, boolean terminal) {
		this.id = id;
		this.description = description;
		this.exits = new ArrayList<>();
		this.terminal = terminal;
		this.hasKeys = false;
		this.hasCollected = false;
	}
	
	/**
	 * Create an exit for the user to navigate to another Place.
	 * @param exit - the description and target of the other Place.
	 */
	public void addExit(Exit exit) {
		this.exits.add(exit);
	}
	
	/**
	 * For gameplay, whether this place ends the game.
	 * @return true if this is the end.
	 */
	public boolean isTerminalState() {
		return this.terminal;
	}
	
	/**
	 * The internal id of this place, for referring to it in {@link Exit} objects.
	 * @return the id.
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * The narrative description of this place.
	 * Give a night time description additionally if isNight() is true. 
	 * @return what we show to a player about this place.
	 */
	public String getDescription(boolean isNightTime) {
		if (isNightTime == true) {
			if(this.getId() == "youngLibrary") {
				return (this.description + "\nThe night has befallen, and you still haven't managed to get work done!\n");
			} else if (this.getId() == "entranceHall") {
				return (this.description + "\nIt's so late already. Might as well go home at this point - but can you?\n");
			}
		} else {
			return this.description;
		}
		return this.description;
	}

	/**
	 * Get a view of the exits from this Place, for navigation.
	 * @return all the exits from this place.
	 */
	public List<Exit> getVisibleExits() {
		List<Exit> output = new ArrayList<>();
		for (Exit e : this.exits) {
			
		    if (e.isSecret()) {
		      // don't show to player
		    } else {
		      output.add(e);
		    }
		  }
		 return output;
	}
	
	/**
	 * This is a terminal location (good or bad).
	 * @param id - this is the id of the place (for creating {@link Exit} objects that go here).
	 * @param description - this is the description of the place.
	 * @return the Place object.
	 */
	public static Place terminal(String id, String description) {
		return new Place(id, description, true);
	}
	
	/**
	 * Create a place with an id and description.
	 * @param id - this is the id of the place (for creating {@link Exit} objects that go here).
	 * @param description - this is what we show to the user.
	 * @return the new Place object (add exits to it).
	 */
	public static Place create(String id, String description) {
		return new Place(id, description, false);
	}
	
	/**
	 * Implements what we need to put Place in a HashSet or HashMap.
	 */
	public int hashCode() {
		return this.id.hashCode();
	}
	
	/**
	 * Give a string for debugging what place is what.
	 */
	public String toString() {
		return "Place("+this.id+" with "+this.exits.size()+" exits.)";
	}
	
	/**
	 * Whether this is the same place as another.
	 */
	public boolean equals(Object other) {
		if (other instanceof Place) {
			return this.id.equals(((Place) other).id);
		}
		return false;
	}
	
	/**
	 * Search for any secret exits related to this place. Hidden exits get revealed. 
	 */
	public void search() {
		for (Exit e : this.exits) {
			if (e instanceof SecretExit) {
				SecretExit exit = (SecretExit) e;
				if (exit.isSecret()) {
					exit.search();
				}
			}
		}
	}
	
	
	/**
	 * Retrieve the list of keys present in this instance of Place
	 */
	public List<String> getKeys() {
		List<String> theKeys = new ArrayList<String>(this.keysInPlaces.values());
		return theKeys;	
	}
	
	
	/**
	 * You should be to add keys to places in the BassHall class
	 * @param keyItem - the item that is going be to be a key for another place.
	 */
	public void putKey(String keyItem) {
		this.keysInPlaces.put(this, keyItem);
	}
	
}

