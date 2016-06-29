package com.homework.ticketdao;



import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.homework.model.Level;
import com.homework.util.LevelData;


public class TicketDao {

	private static Map<Integer, Level> venue = new ConcurrentHashMap<Integer, Level>();

	
	static {
		for (LevelData data : LevelData.values()) {
			venue.put(data.getId(), getLevel(data));
		}

	}

	private static Level getLevel(LevelData data) {
		return new Level(data.getId(), data.getName(), data.getPrice(), data.getNoOFRows(), data.getNoOfCols());
	}

	
	public int getSeatAvailability(Optional<Integer> venueLevel) {
		int availableSeats = 0;

		if (null != venueLevel && venueLevel.isPresent()) {
			if (venueLevel.get() > 0) {
				return venue.get(venueLevel.get()).getNumberOfSeatsAvailable();
			}
		} else {
			
			for (Integer level : venue.keySet()) {
				availableSeats = availableSeats + venue.get(level).getNumberOfSeatsAvailable();
			}
		}
		return availableSeats;
	}

	public Level getVenueLevel(int venueLevel) throws Exception {

		if (venueLevel < 1 && venueLevel > 4) {
			throw new Exception("Sorry, invalid venue level");
		}

		return venue.get(venueLevel);
	}

}
