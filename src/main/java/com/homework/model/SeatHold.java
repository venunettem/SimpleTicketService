package com.homework.model;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class SeatHold implements Serializable {

	private static final long serialVersionUID = 1326010690994209400L;

	private static final AtomicInteger ID_GENERATOR = new AtomicInteger();

	private int id;

	private int numberOfSeats;

	private long expireTime;

	private List<Seat> seatsToHold;

	private String customerEmail;

	private String errorMessage;

	public SeatHold(int numberOfSeats, String customerEmail) {
		this.id = ID_GENERATOR.incrementAndGet();
		this.numberOfSeats = numberOfSeats;
		this.seatsToHold = new ArrayList<Seat>(numberOfSeats);
		this.customerEmail = customerEmail;

	}

	
	 
	public boolean isOnHold() {
		return System.currentTimeMillis() <= this.expireTime;
	}

	
	public int getId() {
		return id;
	}

	
	public void setId(int id) {
		this.id = id;
	}

	
	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	
	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	
	public long getExpireTime() {
		return expireTime;
	}

	
	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

	
	public List<Seat> getSeatsToHold() {
		return seatsToHold;
	}

	
	public void setSeatsToHold(List<Seat> seatsToHold) {
		this.seatsToHold = seatsToHold;
	}

	
	public void addSeatToHold(Seat seatToHold) {
		this.seatsToHold.add(seatToHold);
	}

	
	public String getCustomerEmail() {
		return customerEmail;
	}

	
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	
	public String getErrorMessage() {
		return errorMessage;
	}

	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}

