import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ApplicantOfficerApp {
	public static void start(User user, ArrayList<Project> projectList){
		Applicant applicant = (Applicant)user;
	    HDBOfficer officer = null;
		if (user instanceof HDBOfficer) { //only need to check if they are HDBOfficer because a HDBOfficer is an applicant
			officer = (HDBOfficer)user;	
		}
		
		Scanner sc = new Scanner(System.in);	
		projectList = ProjectLogic.filterProjectsByMaritalStatus(projectList, user.getMaritalStatus());
		// Display applicant details so that he/she can better understand the criteria they meet for the projects
		System.out.printf("Name: %s\n", applicant.getName());
		System.out.printf("Age: %d\n", applicant.getAge());
		System.out.printf("Marital Status: %s\n", applicant.getMaritalStatus());
		ArrayList<String> applicantFeatures = new ArrayList<>(List.of(
			    "View Available Projects", 
			    "View Applied Projects", 
			    "Apply Project", 
			    "Book Flat", 
			    "Request Withdrawal"
		));
		ArrayList<String> officerFeatures = new ArrayList<>(List.of(
			    "View Project Registration Status", 
			    "Register for Project"
		));	

		// Displaying managing officer details (if he is a managing officer for a project)
		ArrayList<String> managingOfficerFeatures = new ArrayList<>(List.of(
			    "View all booking requests",
			    "Help applicants book flat",
			    "Generate flat selection receipt",
			    "View Project Details" 
		));	
		
		int choice=-2; //DO NOT SET TO -1 BECAUSE WE USE -1 TO EXIT
		do {
			// Display the list of features available
			System.out.println("============================");
			System.out.println("List of Features:");
			int currentIndex = displayFeatures(applicantFeatures, 0);
			if (officer != null) { //Display officer functions only if the user is a HDB Officer
				currentIndex = displayFeatures(officerFeatures, currentIndex);
				if (officer.isManagingOfficer())
					currentIndex = displayFeatures(managingOfficerFeatures, currentIndex); //Display remaining officer features that are only allowed if the officer is managing a project.
			}
			System.out.printf("-1. Exit\n");
			System.out.println();
			try {
				System.out.printf("Enter your choice: ");
				choice = sc.nextInt();
				if (choice == 1) {
			    	System.out.println("\nList of available projects:");
					System.out.println("============================");
					applicant.viewAvailableProjects(projectList);
				}
				else if (choice == 2) {
					Application currentApplication = applicant.getCurrentApplication();
					if (currentApplication != null) {
						System.out.println("Applied Project: " + currentApplication.getProject().getProjectName());
						System.out.println("Application Status: " + applicant.viewApplicationStatus());
						if (currentApplication.getBookingRequested() == true) {
							System.out.println("Flat booking request is sent.");
							System.out.println();
						}
					}
					
				}
				else if (choice == 3) {
					/* 
					Check if the applicant has already applied for a project
					If yes, then print the message and continue the loop
					Else, print the list of applied projects and ask the applicant to enter the project number to apply for
					*/
					if (applicant.getCurrentApplication() != null){
						System.out.println("Cannot apply for project, already applied for a project: " + applicant.getCurrentApplication().getProject().getProjectName());
						continue;
					}
					applicant.viewAvailableProjects(projectList);
					System.out.printf("Enter the project number to apply for: ");	
					int projectNumber = sc.nextInt();
					Project project = projectList.get(projectNumber-1);
					ArrayList<FlatType> filteredFlatTypes = ProjectLogic.filterFlatTypesByMaritalStatus(project.getFlatTypes(), applicant.getMaritalStatus());
					if (filteredFlatTypes.isEmpty()){
						System.out.println("Cannot apply for project, applicant is single: " + project.getProjectName());
						continue;
					}
					System.out.println(FlatTypeLogic.displayFlatTypesView(filteredFlatTypes)); 
					System.out.printf("Enter the flat type number to apply for: ");
					int flatTypeNumber = sc.nextInt();
					FlatType flatType = filteredFlatTypes.get(flatTypeNumber-1);
					applicant.applyForProject(project, flatType);
				}
				else if (choice == 4) {
					Application currentApplication = applicant.getCurrentApplication();
					if (currentApplication == null || currentApplication.getApplicationStatus() != ApplicationStatus.SUCCESSFUL || currentApplication.getBookingRequested() == true) continue;
					else {
						ArrayList<HDBOfficer> projectOfficers = currentApplication.getProject().getHDBOfficer();
						ProjectLogic.displayHDBOfficers(projectOfficers);
						System.out.print("Enter the hdb officer id: ");
						int officerNumber = sc.nextInt();
						HDBOfficer chosenOfficer = projectOfficers.get(officerNumber-1);
						applicant.bookFlat(chosenOfficer);
						System.out.println(chosenOfficer.toString());
					}		
				}
				else if (choice == 5){ 
					Application currentApplication = applicant.getCurrentApplication();
					if (currentApplication == null) { 
						System.out.println("No application found.");
						continue;
					} 
					applicant.requestWithdrawal();
					System.out.println("Withdrawal request is sent.");
				}
				if (officer != null) {
					if (choice == 6) {
						System.out.println("\nView Registration Status:");
						System.out.println("============================");
						officer.getOfficerRegistrationStatus();
					}
					else if (choice == 7) {
						//TODO
						continue;
					}
					if (officer.isManagingOfficer()) {
						if (choice == 8) {
					    	System.out.println("\nView All Booking Requests:");
							System.out.println("============================");
							officer.viewBookRequests();
						}
						else if (choice == 9) {
							ArrayList<Application> filteredApplications = ApplicationLogic.filterByNotBooked(officer.getApplications());
					    	System.out.println("\nHelp applicants book flat:");
							System.out.println("============================");
							ApplicationLogic.displayApplications(filteredApplications);
							System.out.print("Enter the application number to help book their flat: ");
							int chosenApplication = sc.nextInt();
							officer.helpBookFlat(filteredApplications.get(chosenApplication-1));
						}
						else if (choice == 10) {
							ArrayList<Application> filteredApplications = ApplicationLogic.filterByBooked(officer.getApplications());
					    	System.out.println("\nGenerate flat selection receipt:");
							System.out.println("============================");
							ApplicationLogic.displayApplications(filteredApplications);
						}
						else if (choice == 11){ 
							// View project details
							System.out.println("\nView Project Details:");
							System.out.println("============================");
							officer.viewProjectDetails(); 
						}
					}
				}
			}catch (InputMismatchException e){
				System.out.println("Invalid input! Please enter an integer.");
				sc.nextLine(); //Clear invalid input in buffer
			}

		} while (choice != -1);
		
	}
	public static int displayFeatures(ArrayList<String> features, int index) {
		int i;
		for (i=0; i<features.size(); i++) {
			System.out.printf("%d. %s\n", i+1+index, features.get(i));
		}
		return index+i;
	}
}