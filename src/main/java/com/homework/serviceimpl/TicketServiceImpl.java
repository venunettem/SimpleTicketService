package com.homework.serviceimpl;



import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homework.model.Level;
import com.homework.model.Seat;
import com.homework.model.SeatHold;
import com.homework.ticketdao.TicketDao;
import com.homework.util.Status;


@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketDao ticketDao;

	private long holdTimeSecs = 30;

	private Map<Integer, SeatHold> seatsHold = new ConcurrentHashMap<Integer, SeatHold>();

	
	public int numSeatsAvailable(Optional<Integer> venueLevel) {
		return ticketDao.getSeatAvailability(venueLevel);
	}

	
	//  minLevel = 1 best available, maxLevel =4 least available
	public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel,
			String customerEmail) {

		SeatHold seatHold = new SeatHold(numSeats, customerEmail);

		if (numSeats <= 0) {
			seatHold.setErrorMessage("Sorry, Invalid Number of Seats Requested, Please try again");
			return seatHold;
		}

		if (numSeats > numSeatsAvailable(null)) {
			seatHold.setErrorMessage("Sorry, only" + numSeatsAvailable(null) + "seats are available");
			return seatHold;
		}

		int startLevel = 1;
		int endLevel = 4;

		if (null != minLevel && minLevel.isPresent() &&  minLevel.get() > 0)
			startLevel = minLevel.get();

		if (null != maxLevel && maxLevel.isPresent() && maxLevel.get() > 0)
			endLevel = maxLevel.get();

	
		levelLoop: for (int i = startLevel; i <= endLevel; i++) {
			Level level;
			try {
				level = ticketDao.getVenueLevel(i);

				synchronized (level) {

					for (Seat[] seatRow : level.getSeats()) {
						for (Seat seat : seatRow) {
							if (seat.isAvailable()) {
								seat.setStatus(Status.ONHOLD);
								seat.getVenueLevel().updateAvailability(Status.ONHOLD);
								seat.setHoldExpireTime(System.currentTimeMillis() + holdTimeSecs * 1000);
								seat.setHoldReserveId(seatHold.getId());
								seatHold.addSeatToHold(seat);
							}
							if (seatHold.getSeatsToHold().size() == numSeats)
								break levelLoop;
						}
						if (seatHold.getSeatsToHold().size() == numSeats)
							break levelLoop;
					}
				}
			} catch (Exception e) {
				seatHold.setErrorMessage(e.getMessage());
				return seatHold;
			}

		}
		seatHold.setCustomerEmail(customerEmail);
		seatHold.setExpireTime(System.currentTimeMillis() + holdTimeSecs * 1000);
		getSeatsHold().put(seatHold.getId(), seatHold);

		return seatHold;
	}

	
	public String reserveSeats(int seatHoldId, String customerEmail) {

		if(!getSeatsHold().containsKey(seatHoldId)){
			return " Seats are no longer available.Please try again";
		}
		SeatHold seatHold = getSeatsHold().get(seatHoldId);
		List<Seat> seatsToReserve = seatHold.getSeatsToHold();

		synchronized (seatsHold) {
			if (seatHold.isOnHold()) {
				for (Seat seat : seatsToReserve) {
					seat.setStatus(Status.RESERVED);
					seat.getVenueLevel().updateAvailability(Status.RESERVED);

				}
				seatHold.setExpireTime(System.currentTimeMillis());
				seatsHold.remove(seatHold.getId());
			} else {
				for (Seat seat : seatsToReserve) {
					
					if (seat.getHoldReserveId() == seatHoldId) {
						seat.setStatus(Status.AVAILABLE);
						seat.getVenueLevel().updateAvailability(Status.AVAILABLE);
					}
				}
				return " Seats are no longer available.Please try again";
			}
		}
		System.out.print(" seats are reserved and confirmation code is : CN");
		return String.valueOf(seatHold.getId());
	}

	
	public Map<Integer, SeatHold> getSeatsHold() {
		return seatsHold;
	}

	
	public void setSeatsHold(Map<Integer, SeatHold> seatsHold) {
		this.seatsHold = seatsHold;
	}

	
	public TicketDao getTicketDao() {
		return ticketDao;
	}

	
	public void setTicketDao(TicketDao ticketDao) {
		this.ticketDao = ticketDao;
	}

	
	
	public long getHoldTimeSecs() {
		return holdTimeSecs;
	}

	
	public void setHoldTimeSecs(long holdTimeSecs) {
		this.holdTimeSecs = holdTimeSecs;
	}


	
}
