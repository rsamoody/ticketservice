package com.ticketing.model;

import java.util.Calendar;
import java.util.List;

public class SeatHold {
	private static int counter = 1;
	
	private final int id;
	private final List<Seat> seats;
	private final String email;	
	private final long timeStamp;
	private String reservation;
	
	public SeatHold(List<Seat> seats, String email){
		Calendar cal = Calendar.getInstance();
		this.seats = seats;
		this.email = email;
		this.timeStamp = cal.getTime().getTime();
		this.id = counter++;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public String getEmail() {
		return email;
	}	

	public String getReservation() {
		return reservation;
	}

	public int getId() {
		return id;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setReservation(String reservation) {
		this.reservation = reservation;
	}
	
	
	
	
}
