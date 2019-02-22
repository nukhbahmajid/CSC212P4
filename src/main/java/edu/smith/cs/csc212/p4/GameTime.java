package edu.smith.cs.csc212.p4;

public class GameTime {
	
	/**
	 * The number of hours inside the game 
	 */
	private int hours; 
	
//	/**
//	 * The time that should be displayed for each step taken 
//	 */
//	private String time;
	
	/**
	 * How many days have you spent inside the game?
	 */
	private int days;
	
	
	/**
	 * Just using those fields above in the methods, so there is no need to specify something in the constructor
	 */
	public GameTime() {
		this.hours = 0;
	 
	}
	
	
	/**
	 * Increase the no. of hours by 1 for every step taken inside the game 
	 */
	public void increaseHour() {
		this.hours += 1;
	}
	
	/**
	 * Get the total number of hours the player has stayed in the game and current time
	 */
	public void time() {
		int instanceHours = this.hours;
		if(instanceHours >= 24) {
			this.days += 1; 
			instanceHours %= 24;
		}
		System.out.println("The time right now is: " + this.hours + ":00");
		System.out.println("\nTime spent in the game: " + ((this.days * 24) + this.hours) + " hours");
		System.out.println("It has been " + this.days + " days and " + this.hours + " hours\n");
	}
	
	/**
	 * Get the hours back in a 24hr clock format - used later to determine time of the day. 
	 */
	public int getHours() {
		if (this.hours >= 24) {
			int modHours = this.hours;
			modHours %= 24;
			return modHours;
		} else {
			return this.hours;
		}
	}
	
	/**
	 * Is it night time? 
	 */
	public boolean isNightTime() {
		if(this.getHours() >= 17) {
			return true;
		} else {
			return false;
		}
	}
	

}
