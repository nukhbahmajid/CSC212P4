package edu.smith.cs.csc212.p4;
import java.util.Objects;

public class SecretExit extends Exit {
	/**
	 * This class represents a secret exit from a Place to another Place.
	 */
	
	
	/**
	 * Is the exit a hidden exit? 
	 */
	private boolean hidden;
	
	public SecretExit(String target, String description) {
		super(target, description);
		this.hidden = true;
	}
	
	@Override
	public boolean isSecret() {
		return this.hidden;
	}
	

	public void search() {
		this.hidden = false;
	}

}
