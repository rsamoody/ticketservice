package com.ticketing.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.ticketing.model.Constants.Level;

public class Venue {
	private final static Venue venue;	
	
	private final Sector[] sectors = new Sector[Level.values().length]; 
	// Holds a list of all empty seats in the venue.
	// Sorted based on the seats desirability.
	private final Set<Seat> availableSeats = new TreeSet<Seat>();   
    // Holds a list of seat with temporary hold 
	private final Map<Integer, SeatHold> holdSeats = new HashMap<Integer, SeatHold>();
	// Holds a list of seats with final sale.
    private final Map<String, SeatHold> bookedSeats = new HashMap<String, SeatHold>();
	
	private Venue() {		
		for (Level level : Level.values()) {
			Sector sector = new Sector(level);;
			sectors[level.levelId()] = sector;
			availableSeats.addAll(sector.getAvailableSeats());
		}
	}
	
	static{
		venue = new Venue();
	}
	
	public static Venue getInstance(){
		return venue;
	}

	public Sector[] getSectors() {
		return sectors;
	}

	public Set<Seat> getAvailableSeats() {
		return availableSeats;
	}

	public Map<Integer, SeatHold> getHoldSeats() {
		return holdSeats;
	}

	public Map<String, SeatHold> getBookedSeats() {
		return bookedSeats;
	}	
	
	
}
