package com.homework;


import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.homework.model.SeatHold;
import com.homework.serviceimpl.TicketService;
import com.homework.util.Status;




@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/application-context.xml"})
public class TestTicketService {
	
	@Autowired
	TicketService ticket;

	

	
	@Test
	public  void testNumSeatsAvailable() {
		
        Assert.assertNotEquals(ticket.numSeatsAvailable(Optional.of(1)), 0);
        Assert.assertEquals(ticket.numSeatsAvailable(Optional.of(0)), 0);
        
	}

	
	@Test
	public  void testFindAndHoldSeats1() {
		/* Test 1: OnHoldTest
		 * 3 seats from level 1 
		 */
		SeatHold onHold = ticket.findAndHoldSeats(3, Optional.of(1), Optional.of(4), "venu@gmail.com");
		for(int i=0; i<onHold.getSeatsToHold().size();i++){
			//status of the seat should be ONHOLD
			Assert.assertEquals(Status.ONHOLD, onHold.getSeatsToHold().get(0).getStatus());
		}
	}
	
	
	@Test
	public  void testFindAndHoldSeats2() {
		/* Test 2: data test
		 * 3 seats from level 1 
		 */
		//3 seats from level 1 
		SeatHold threeSeatsFirstLevel = ticket.findAndHoldSeats(3, Optional.of(1), Optional.of(3), "venu@gmail.com");
		//SeatHold should have only request seat counts
		Assert.assertEquals(3,threeSeatsFirstLevel.getSeatsToHold().size());
		
		//SeatHold should have the email id updated
		Assert.assertEquals("venu@gmail.com",threeSeatsFirstLevel.getCustomerEmail());
		
		//3 seats from level 2 
		SeatHold threeSeatsSecondLevel = ticket.findAndHoldSeats(3, Optional.of(2), Optional.of(3), "venu@gmail.com");
		
		//bothObjects should not be equal
		Assert.assertNotEquals(threeSeatsFirstLevel, threeSeatsSecondLevel);
		
		//both should have different seats
		Assert.assertNotEquals(threeSeatsFirstLevel.getSeatsToHold().get(0), threeSeatsSecondLevel.getSeatsToHold().get(0));
		
		//Both should have different id
		Assert.assertNotEquals(threeSeatsFirstLevel.getId(), threeSeatsSecondLevel.getId());
	}

	
	@Test
	public  void testFindAndHoldSeats3() {
		/* Test 3: Duplicate hold
		 * 3 seats from level 1 
		 */
		//3 seats from level 1 
		SeatHold threeSeatsFirstLevel = ticket.findAndHoldSeats(3, Optional.of(1), Optional.of(3), "venu@gmail.com");
				
		//3 seats from level 2 
		SeatHold threeSeatsSecondLevel = ticket.findAndHoldSeats(3, Optional.of(2), Optional.of(3), "venunettem@gmail.com");
		
		//bothObjects should not be equal
		Assert.assertNotEquals(threeSeatsFirstLevel, threeSeatsSecondLevel);
		
		//Both should have different id
		Assert.assertNotEquals(threeSeatsFirstLevel.getId(), threeSeatsSecondLevel.getId());
	}

	
	@Test
	public  void testFindAndHoldSeats4() {
		/* Test 4: hold expire
		 * 3 seats from level 1 
		 */
		SeatHold holdAndExpiry = ticket.findAndHoldSeats(3, Optional.of(1), Optional.of(3), "venu@gmail.com");
		try {
			Thread.sleep((30*1000)+1);
			//after 30 secs(20000ms), SeatHold should expire
			Assert.assertFalse(holdAndExpiry.getSeatsToHold().get(0).isOnHold());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	
	@Test
	public  void testReserveSeats() {
		//Holding 3 seats from level 1 
		SeatHold seatHold = ticket.findAndHoldSeats(3, Optional.of(1), Optional.of(3), "venu@gmail.com");
		
		String confirmId = ticket.reserveSeats(seatHold.getId(),"venu@gmail.com");
		
		//Should return the confirmation id, should not be null
		Assert.assertNotNull(confirmId);
		
		//After reserving,status should be changed to "RESERVED"
		for(int i=0; i<seatHold.getSeatsToHold().size();i++){
			Assert.assertEquals(Status.RESERVED, seatHold.getSeatsToHold().get(i).getStatus());
		}
		
		//Duplication reservation should return the message " Seats are not available. Please try again"
		String duplicateReservation = ticket.reserveSeats(seatHold.getId(),"venu@gmail.com");
		Assert.assertEquals(" Seats are no longer available.Please try again",duplicateReservation);
				
	}

	
	public TicketService getTicketService() {
		return ticket;
	}

	
	public void setTicketService(TicketService ticketService) {
		this.ticket = ticketService;
	}
}

