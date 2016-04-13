package com.ticketing.model;

import java.util.Set;
import java.util.TreeSet;

import com.ticketing.model.Constants.Level;

public class Sector {
	private final Level level;
	private final Set<Seat> availableSeats;
	
	protected Sector(Level level){
		this.level = level;
		this.availableSeats = new TreeSet<Seat>();
		for(int i = 0; i < this.level.rows(); i++){
			for(int j = 0; j < level.seats(); j++){
				this.availableSeats.add(new Seat(level, i, j));
			}
		}
	}

	public Level getLevel() {
		return level;
	}

	public Set<Seat> getAvailableSeats() {
		return availableSeats;
	}
	
	

}
