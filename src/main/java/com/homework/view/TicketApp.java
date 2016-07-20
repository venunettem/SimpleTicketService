package com.homework.view;


import java.util.Optional;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.homework.model.SeatHold;
import com.homework.serviceimpl.TicketService;

@Component
public class TicketApp {
	
	
	public static void main(String[] args) {
		try {
			@SuppressWarnings("resource")
			ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
			TicketService ticket = (TicketService) context.getBean("ticket");
		
			boolean options = true;
			Scanner scanner = new Scanner(System.in);
			while (options) {
				
				System.out.println("Choose an option to reserve and hold seats");										
				System.out.println("Enter 1 for Seat Availabilty");
				System.out.println("Enter 2 for Hold & Reserve");
				
				for(int i=0;i<10;i++)
				{
					
				}
				int i=1;
				while(i<10)
				{
					i++;
				}
			
				
				int option = scanner.nextInt();
				if (option == 1) {
					
					System.out.println("Please enter seating Level from 1 to 4 to find the available seats \n");
					int venueLevel=Integer.valueOf(scanner.nextInt());
					System.out.println("Available Seats: "
							+ ticket.numSeatsAvailable(Optional.of(venueLevel)));
					
				} else if (option == 2) {
					
					
					System.out.println("Please enter number of Seats to be held\n");
					int numberOfSeats = scanner.nextInt();
					
					System.out.println("Please enter MINIMUM venue level (from 1 to 4  or -1 to skip) \n");
					int minLevel = scanner.nextInt();
					
					System.out.println("Please enter MAXIMUM venue level (from 1 to 4 or -1 to skip) \n");
					int maxLevel = scanner.nextInt();
					System.out.println("Please enter email address \n");
					
					String email = scanner.next();
					SeatHold seatHold = ticket.findAndHoldSeats(numberOfSeats, Optional.of(minLevel),
							Optional.of(maxLevel), email);
					System.out.println("Requested Seats are ON HOLD for customer id : " + seatHold.getId());

					for (int i = 0; i < seatHold.getSeatsToHold().toArray().length; i++) {
						System.out.println(seatHold.getSeatsToHold().toArray()[i] + " ");
					}

					System.out.print("\nPlease enter 1 to reserve the seats held \n");
					System.out.println("testing 399 SVN");
					System.out.println("testing 3 SVN");
					if (scanner.nextInt() == 1) {
						System.out.println(ticket.reserveSeats(seatHold.getId(), email));
					} else {
						System.out.println("Seats are not confirmed");
					}

				}
				System.out.println("Please press 1 if you want to continue again or press any other number to exit");
				if (scanner.nextInt() != 1) {
					options = false;
				}

			}
			scanner.close();

		} catch (Exception exception) {
			System.out.println("Please enter the valid input : ");
			exception.printStackTrace();
		}

		

	}


}
