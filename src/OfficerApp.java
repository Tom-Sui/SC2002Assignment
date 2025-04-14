import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OfficerApp {
	public static void start(HDBOfficer officer) {
		Scanner sc = new Scanner(System.in);
		ArrayList<String> officerFeatures = new ArrayList<>(List.of(
			    "View Project Registration Status", 
			    "Register for Project", 
			    "View all booking requests",
			    "Help applicants book flat",
			    "Generate flat selection receipt"
		));	

		// Displaying managing officer details (if he is a managing officer for a project)
		ArrayList<String> managingOfficerFeatures = new ArrayList<>(List.of(
			    "View Project Details" 
		));	
		int choice;
		do {
			// Display the list of features available
			System.out.println("============================");
			System.out.println("List of Available Features:");
			if (officer.isManagingOfficer()) {
				officerFeatures.addAll(managingOfficerFeatures);
			}
			int noOfFeatures = officerFeatures.size();
			for (int i=0; i<noOfFeatures; i++) {
				System.out.printf("%d. %s\n", i+1, officerFeatures.get(i));
			}
			System.out.printf("-1. Exit\n");
			System.out.println();
			System.out.printf("Enter your choice: ");
			choice = sc.nextInt();

			if (choice == 1) {
		    	System.out.println("\nView Registration Status:");
				System.out.println("============================");
				officer.getOfficerRegistrationStatus();
			}
			else if (choice == 2) {
				continue;
				
			}
			else if (choice == 3) {
		    	System.out.println("\nView All Booking Requests:");
				System.out.println("============================");
				officer.viewBookRequests();
			}
			else if (choice == 4) {
				ArrayList<Application> filteredApplications = ApplicationLogic.filterByNotBooked(officer.getApplications());
		    	System.out.println("\nHelp applicants book flat:");
				System.out.println("============================");
				ApplicationLogic.displayApplications(filteredApplications);
				System.out.print("Enter the application number to help book their flat: ");
				int chosenApplication = sc.nextInt();
				officer.helpBookFlat(filteredApplications.get(chosenApplication-1));
			}
			else if (choice == 5) {
				ArrayList<Application> filteredApplications = ApplicationLogic.filterByBooked(officer.getApplications());
		    	System.out.println("\nGenerate flat selection receipt:");
				System.out.println("============================");
				ApplicationLogic.displayApplications(filteredApplications);
			}
			else if (choice == 6){ 
				// View project details
				System.out.println("\nView Project Details:");
				System.out.println("============================");
				officer.viewProjectDetails(); 
			}
			
		} while (choice != -1);
		
	}
}
