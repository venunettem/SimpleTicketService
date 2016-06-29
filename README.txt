Simple Ticket Service
 
 
 
System Requirements:
1. Install Java 1.8
2. Install Maven 3.3.9
3. Set Environment variables for Java and Maven 

How to Run:
1. Download or Clone the project from the below Repository to your local environment.
    https://github.com/venu4433/SimpleTicketService.git
2. Open Command prompt and navigate to the saved directory
3. Run the command “mvn clean install”.
4. Navigate to the generated target directory and run the command
        “java –jar SimpleTicketService-0.1-jar-with-dependencies.jar”.
5. Now new menu will appear prompting to enter the user input, enter valid inputs.
 
Running Tests:
use the command "mvn test"
 
 
Assumptions: 

1.	No seat number preference to the user. User doesn’t have facility to choose seat numbers because of high-demand, seats will be hold based on the availability.
2.	Seats can’t be hold in multiple levels in a single transaction. Suppose if the user want 5 seats, 2 in Level-1 and 3 in Level-2.
    This type of transaction can’t be performed. First 2 seats are to be hold in Level-1 and then 3 seats in Level-2 in two different transactions.
3.	All the inputs are entered in console and outputs will be displayed in the same.
4.	Hold time can be set based on our requirement. It is set to 30 seconds in this application.
5.	If user selected to exit, the transactions performed before this will be erased automatically.

 
