package com.ticketing.model;

public interface Constants {
	public static enum Status {FREE, HOLD, RESERVED};
	
	public static enum Level {ORCHESTRA(1,25,50,100), MAIN(2, 20, 100, 75), BALCONY1(3,15,100,50), BALCONY2(4,15,100,40);				
		private final int levelId;
		private final int rows;
		private final int seats;
		private final double price;
		
		Level(int levelId, int rows, int seats, double price){ 
			this.levelId = levelId;
			this.rows = rows;
			this.seats = seats;
			this.price = price;			
		}
		double price(){	return price; }
		int levelId(){return levelId;}
		int rows(){ return rows;}
		int seats(){ return seats;}
	};

}
