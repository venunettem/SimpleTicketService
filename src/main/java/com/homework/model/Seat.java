package com.homework.model;


import java.io.Serializable;

import com.homework.util.Status;


public class Seat implements Serializable {

	private static final long serialVersionUID = 1162753972457739410L;

	private Level venueLevel;

	private int seatNumber;

	private Status status = Status.AVAILABLE;

	private long holdExpireTime;

	private long holdReserveId;

	public Seat(int seatNumber, Level venueLevel) {
		this.seatNumber = seatNumber;
		this.venueLevel = venueLevel;
	}

	
	public Level getVenueLevel() {
		return venueLevel;
	}

	
	public void setVenueLevel(Level venueLevel) {
		this.venueLevel = venueLevel;
	}

	
	public int getSeatNumber() {
		return seatNumber;
	}

	
	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	
	public Status getStatus() {
		return status;
	}

	
	public void setStatus(Status status) {
		this.status = status;
	}

	
	public long getHoldExpireTime() {
		return holdExpireTime;
	}

	
	public void setHoldExpireTime(long holdExpireTime) {
		this.holdExpireTime = holdExpireTime;
	}

	
	public long getHoldReserveId() {
		return holdReserveId;
	}

	
	public void setHoldReserveId(long holdReserveId) {
		this.holdReserveId = holdReserveId;
	}

	
	public boolean isOnHold() {
		if (Status.ONHOLD.equals(status)) {
			return System.currentTimeMillis() <= this.holdExpireTime;
		}
		return true;
	}

	public boolean isAvailable() {
		if (Status.AVAILABLE.equals(status)) {
			return true;
		} else if (Status.ONHOLD.equals(status) && System.currentTimeMillis() >= this.holdExpireTime) {
			this.status = Status.AVAILABLE;
			this.getVenueLevel().updateAvailability(Status.AVAILABLE);
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return new StringBuilder("\n ------------------------------").append("\n Seat Number=").append(this.seatNumber)
				.append("\n Venue Level=").append(this.venueLevel.getId())
				.toString();

	}

}
