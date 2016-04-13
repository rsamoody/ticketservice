package com.ticketing.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ticketing.model.Constants.Level;
import com.ticketing.model.Seat;
import com.ticketing.model.SeatHold;
import com.ticketing.model.Sector;
import com.ticketing.model.Venue;
import com.ticketing.service.TicketService;

public class TicketServiceImpl  implements TicketService  {

	public synchronized int numSeatsAvailable(Optional<Integer> venueLevel) {
		if( venueLevel.isPresent()){
			return Venue.getInstance().getAvailableSeats().size();
		}else{
			return Venue.getInstance().getSectors()[venueLevel.get()].getAvailableSeats().size();
		}
	}

	public synchronized SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel, String customerEmail) {
		// check if the min and max have values
		int min = minLevel.isPresent() ? minLevel.get() -1 : 0; 
		int max = maxLevel.isPresent() ? maxLevel.get() - 1 : Level.values().length - 1;		
		
		// check if these sectors have enough free seats, otherwise return null
		Sector[] sectors = Venue.getInstance().getSectors();
		int totalAvailableSeats = 0;
		for(int i = min; i <= max; i++){
			totalAvailableSeats += sectors[i].getAvailableSeats().size();
		}
		if( totalAvailableSeats < numSeats) return null;
		
		// hold seats from the specified levels
		List<Seat> holds = new ArrayList<Seat>();
		int unfulfilled = numSeats;
		for(int i = min; i <= max; i++){
			unfulfilled = findAndHoldSeats(unfulfilled, sectors[i], holds);
			if( unfulfilled == 0 ) break;
		}
		SeatHold seatHold = new SeatHold(holds, customerEmail);
		Venue.getInstance().getHoldSeats().put(seatHold.getId(), seatHold);
		return seatHold;
	}

	public synchronized String reserveSeats(int seatHoldId, String customerEmail) {
		SeatHold seatHold = Venue.getInstance().getHoldSeats().get(seatHoldId);
		if( seatHold == null || !customerEmail.equals(seatHold)) return null;
		String refNum = UUID.randomUUID().toString();
		seatHold.setReservation(refNum);
		Venue.getInstance().getHoldSeats().remove(seatHold);
		Venue.getInstance().getBookedSeats().put(refNum, seatHold);
		return refNum;
	}
	
	/** 
	 * Find and hold the best available seats for a customer 
	 * @param numSeats the number of seats to find and hold 
	 * @param sector the level to book the seats 	
	 * @param holds a list of hold seats 
	 * @return the number of unfulfilled seats
	 */ 
	private synchronized int findAndHoldSeats(int numSeats, Sector sector, List<Seat> holds){			
		int counter = 0;
		List<Seat> seatHolds = new ArrayList<Seat>(); 
		Iterator<Seat> availableSeats = sector.getAvailableSeats().iterator();	
		while(availableSeats.hasNext() && counter <= numSeats){
			seatHolds.add(availableSeats.next());
			counter++;
		}
		holds.addAll(seatHolds);
		Venue.getInstance().getAvailableSeats().removeAll(seatHolds);		
		sector.getAvailableSeats().removeAll(seatHolds);
		
		return numSeats - counter;
	}

}
