package com.homework.util;


public enum LevelData {
	
	ORCHESTRA(1,"Orchestra",25,50,100.00),
	MAIN(2,"Main",20,100,75.00),
	BALCONY1(3,"Balcony 1",15,100,50.00),
	BALCONY2(4,"Balcony 2",15,100,40.00);
	
	int id;
	String name;
	int noOFRows;
	int noOfCols;
	double price;
	
	LevelData(int id, String name, int noOFRows, int noOfCols, double price){
		this.id = id;
		this.name =  name;
		this.noOFRows = noOFRows;
		this.noOfCols = noOfCols;
		this.price = price;
	}
	
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public int getNoOFRows() {
		return noOFRows;
	}
	
	public int getNoOfCols() {
		return noOfCols;
	}
	
	public double getPrice() {
		return price;
	}
	
	public LevelData getMockLevel(int id){
		for(LevelData data : LevelData.values()){
			if(data.getId() == id){
				return data;
			}
		}
		return null;
	}

}
