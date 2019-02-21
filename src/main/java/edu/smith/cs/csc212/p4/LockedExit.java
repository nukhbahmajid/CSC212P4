package edu.smith.cs.csc212.p4;

public class LockedExit extends Exit {
	
	private boolean isLocked;
	
	public LockedExit(String target, String description) {
		super(target, description);
		this.isLocked = true;
	} 
	
	
	/**
	 * Check if this exit is locked. 
	 * @return - boolean isLocked
	 */
	public boolean isLocked() {
		return this.isLocked;
	}
	
	
	/**
	 * Set whether this exit is now locked or not
	 */
	public void setIsLocked(boolean newIsLocked) {
		this.isLocked = newIsLocked;
	}

}
