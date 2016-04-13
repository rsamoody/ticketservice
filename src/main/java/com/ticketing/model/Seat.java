package com.ticketing.model;

import com.ticketing.model.Constants.Level;
import com.ticketing.model.Constants.Status;

public class Seat implements Comparable<Seat> {
	private final Level level;
	private final int rowId;
	private final int seatId;
	private Status status;

	protected Seat(Level level, int rowId, int seatId) {
		this.level = level;
		this.rowId = rowId;
		this.seatId = seatId;
		this.status = Status.FREE;
	}

	public Level getLevel() {
		return level;
	}

	public int getRowId() {
		return rowId;
	}

	public int getSeatId() {
		return seatId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	private int calculatePoint(){
		return Math.abs(seatId - (level.seats()/2));  
	}
	
	// Comparison criteia is based on the seats level and distance from center seat.
	// The farther the seat from center the less desirable. The higher the level the less desirable
	public int compareTo(Seat o) {
		return this.level.levelId() == o.level.levelId() ? (this.calculatePoint() - o.calculatePoint()) * -1 : (this.level.levelId() - o.level.levelId()) ;
	}

}
