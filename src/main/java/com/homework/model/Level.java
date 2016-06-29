package com.homework.model;



import java.io.Serializable;

import com.homework.util.Status;


public class Level implements Serializable {

	private static final long serialVersionUID = -257967709165637657L;

	private int id;
	private String name;
	private double price;
	private int numberOfRows;
	private int numberOfColumns;
	private Seat seats[][];
	private int numberOfSeatsAvailable;
	private int numberOfSeatsHeld;

	
	public Level(int id, String name, double price, int rows, int seatsPerRow) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.numberOfRows = rows;
		this.numberOfColumns = seatsPerRow;
		this.numberOfSeatsAvailable = numberOfRows * numberOfColumns;
		seats = new Seat[numberOfRows][numberOfColumns];

		for (int i = 0; i < numberOfRows; i++) {
			for (int j = 0; j < numberOfColumns; j++) {
				seats[i][j] = new Seat(i * numberOfColumns + j + 1, this);
			}
		}
	}

	
	public int getId() {
		return id;
	}

	
	public void setId(int id) {
		this.id = id;
	}

	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	
	public double getPrice() {
		return price;
	}

	
	public void setPrice(double price) {
		this.price = price;
	}

	
	public int getNumberOfRows() {
		return numberOfRows;
	}

	
	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	
	public int getNumberOfColumns() {
		return numberOfColumns;
	}

	
	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}

	
	public Seat[][] getSeats() {
		return seats;
	}

	
	public void setSeats(Seat[][] seats) {
		this.seats = seats;
	}

	
	public int getNumberOfSeatsAvailable() {
		return numberOfSeatsAvailable;
	}

	
	public void setNumberOfSeatsAvailable(int numberOfSeatsAvailable) {
		this.numberOfSeatsAvailable = numberOfSeatsAvailable;
	}

	
	public int getNumberOfSeatsHeld() {
		return numberOfSeatsHeld;
	}

	public void setNumberOfSeatsHeld(int numberOfSeatsHeld) {
		this.numberOfSeatsHeld = numberOfSeatsHeld;
	}

	// Updating the seat count for each status
	public void updateAvailability(Status statusChange) {
		switch (statusChange) {
		case ONHOLD:
			numberOfSeatsHeld++;
			numberOfSeatsAvailable--;
			break;
		case RESERVED:
			numberOfSeatsHeld--;
			break;
		case AVAILABLE:
			numberOfSeatsHeld--;
			numberOfSeatsAvailable++;
		default:
			break;
		}
	}
}
