import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
/**
 * The main application class for the applicant and HDB officer.
 * This class handles the user interface and interactions for both applicants and HDB officers.
 * It provides options to view available projects, apply for projects, book flats, and manage applications.
 * 
 * @see User
 * @see Project
 * @see FlatType
 * @see Application
 */
public class ApplicantOfficerApp {
	/**
	 * Starts the application for the applicant or HDB officer.
	 * Displays the list of features available to the user and handles user input.
	 * 
	 * @param user the user (applicant or HDB officer) using the application
	 * @param projectList the list of projects available for the user to apply for
	 */
	public static void start(User user, ArrayList<Project> projectList){
		Applicant applicant = (Applicant)user;
	    HDBOfficer officer = null;
	    ApplicationService.updateApplications(applicant);
		projectList = ProjectLogic.filterProjectsByMaritalStatus(projectList, user.getMaritalStatus());
		if (user instanceof HDBOfficer) { //only need to check if they are HDBOfficer because a HDBOfficer is an applicant
			officer = (HDBOfficer)user;	
			/**
			 * List of checks performed to update officer's managedProject and upcomingProjects
			 */
			officer.checkCurrentProject();
			officer.checkNewProject();
			officer.checkApprovedProjects();
			//Filter projects so the officer cannot apply for projects they are managing
			projectList = ProjectLogic.filterProjectsForOfficer(projectList, officer);
		}
		
		Scanner sc = new Scanner(System.in);	
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
		int totalFeatures = applicantFeatures.size() + officerFeatures.size() + managingOfficerFeatures.size();
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
				if (choice < -1 || choice >totalFeatures) {
					System.out.println("Invalid choice. Please enter a valid option shown in the list.");
				}
				if (choice == 1) {
			    	System.out.println("\nList of available projects:");
					System.out.println("============================");
					ProjectLogic.viewAvailableProjects(projectList, applicant);
				}
				else if (choice == 2) {
					Application currentApplication = applicant.getCurrentApplication();
					if (currentApplication != null) {
						System.out.println("Applied Project: " + currentApplication.getProject().getProjectName());
						System.out.println("Application Status: " + currentApplication.getApplicationStatus());
						if (currentApplication.getApplicationStatus() == ApplicationStatus.BOOKED && currentApplication.getBookingRequested() == true) {
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
					int noOfProjects = ProjectLogic.viewAvailableProjects(projectList, applicant);
					if (noOfProjects <= 0) {
						System.out.println("There are currently no projects you can apply for.");
						continue;
					}
					System.out.printf("Enter the project number to apply for: ");	
					try {
						int projectNumber = sc.nextInt();
						Project project = projectList.get(projectNumber-1);
						ArrayList<FlatType> filteredFlatTypes = FlatTypeLogic.filterFlatTypesByMaritalStatus(project.getFlatTypes(), applicant.getMaritalStatus());
						if (filteredFlatTypes.isEmpty()){
							System.out.println("Cannot apply for project, applicant is single: " + project.getProjectName());
							continue;
						}
						System.out.println(FlatTypeLogic.displayFlatTypesView(filteredFlatTypes)); 
						System.out.printf("Enter the flat type number to apply for: ");
						int flatTypeNumber = sc.nextInt();
						FlatType flatType = filteredFlatTypes.get(flatTypeNumber-1);
						
						applicant.applyForProject(project, flatType);
						System.out.println(flatType);
						System.out.println("Project application has been sent.");
						System.out.println("Registered for project: " + project.getProjectName());
						
						//Updating application datafile
						Application application = applicant.getCurrentApplication();
						String projectApplication = String.format("%d,%s,%s,%s,%s,%s,%s",application.getApplicationId(), applicant.getNRIC(), application.getProject().getProjectName(), application.getApplicationStatus(), application.getBookingRequested(),application.getIsBooked(), application.getFlatType().getFlatTypeName());
						General.editFile("./Data/ApplicationList.txt", projectApplication);

						
					} catch (IndexOutOfBoundsException e) {
						System.out.println("Invalid project ID. Please enter a valid project ID.");
						continue;
					}

				}
				else if (choice == 4) {
					Application currentApplication = applicant.getCurrentApplication();
					if (currentApplication == null || currentApplication.getApplicationStatus() != ApplicationStatus.SUCCESSFUL || currentApplication.getBookingRequested() == true) continue;
					else {
						ArrayList<HDBOfficer> projectOfficers = currentApplication.getProject().getHDBOfficer();
						ProjectLogic.displayHDBOfficers(projectOfficers);
						System.out.print("Enter the hdb officer id: ");
						try{
							int officerNumber = sc.nextInt();
							HDBOfficer chosenOfficer = projectOfficers.get(officerNumber-1);
							applicant.bookFlat(chosenOfficer);
							System.out.println(chosenOfficer.toString());
							
		
							Application application = applicant.getCurrentApplication();
		
							//Updating booking datafile
							Booking booking = new Booking(application.getApplicationId(), chosenOfficer.getNRIC());
							String bookingDetails = String.format("%d,%d,%s",booking.getBookingId(), application.getApplicationId(),chosenOfficer.getNRIC());
							General.editFile("./Data/BookingList.txt", bookingDetails);
							
						}
						catch (IndexOutOfBoundsException e) {
						System.out.println("Invalid officer ID. Please enter a valid officer ID.");
						continue;
						}
					}		
				}
				else if (choice == 5){ 
					Application currentApplication = applicant.getCurrentApplication();
					if (currentApplication != null) { 
						General.editFile("./Data/ApplicationList.txt", ApplicationStatus.PENDINGWITHDRAWAL.toString(), currentApplication.getApplicationStatus().toString(), String.valueOf(currentApplication.getApplicationId()));
						ApplicationService.withdrawApplication(currentApplication);
						System.out.println("Withdrawal request is sent.");
					} 
					else
						System.out.println("No application found.");
					
					
				}
				if (officer != null) {
					if (choice == 6) {
						System.out.println("\nView Registration Status:");
						System.out.println("============================");
						officer.viewOfficerRegistrationStatus();
					}
					else if (choice == 7) {
				    	System.out.println("\nRegister for project");
						System.out.println("============================");
						ArrayList<Project> filteredProjects = ProjectLogic.filterProjectsForOfficer(projectList, officer);
						ProjectLogic.viewAvailableProjects(filteredProjects, officer);
						System.out.print("Enter the project number to register for: ");
						try{
							int projectNumber = sc.nextInt();
							Project project = filteredProjects.get(projectNumber-1);
							Map<Project, OfficerRegistrationStatus> registrationStatusMap = officer.getRegistrationStatusMap();
							if (registrationStatusMap.containsKey(project)) {
								System.out.println("You already registed for project: "+ project.getProjectName());
							}
							else if (officer.registerForProject(project)) {
								String officerRegistration = String.format("%s,%s,%s", officer.getNRIC(), project.getProjectName(), OfficerRegistrationStatus.PENDING.toString());
								General.editFile("./Data/OfficerRegistrationList.txt", officerRegistration);
								System.out.println("Registered for project: " + project.getProjectName());
							}
							else {
								System.out.println("Cannot register for project: " + project.getProjectName());
							}
						}
						catch (IndexOutOfBoundsException e) {
							System.out.println("Invalid project ID. Please enter a valid project ID.");
							continue;
						}
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
							try{
								int chosenApplication = sc.nextInt();
								Application app = filteredApplications.get(chosenApplication-1);
								
								if (officer.helpBookFlat(app)) {
									//Updating application datafile
									General.editFile("./Data/ApplicationList.txt", ApplicationStatus.BOOKED.toString(), app.getApplicationStatus().toString(), String.valueOf(app.getApplicationId()));
								}	
								
							} catch (IndexOutOfBoundsException e) {
							System.out.println("Invalid application ID. Please enter a valid application ID.");
							continue;
							}
							
						}
						else if (choice == 10) {
							ArrayList<Application> filteredApplications = ApplicationLogic.filterByBooked(officer.getApplications());
					    	System.out.println("\nGenerate flat selection receipt:");
							System.out.println("============================");
							ApplicationLogic.displayApplications(filteredApplications);
							System.out.print("Enter the application number to generate a flat selection receipt: ");
							try{
								int chosenApplication = sc.nextInt();
								ApplicationService.generateFlatSelectionReceipt(filteredApplications.get(chosenApplication-1));
								ApplicationService.writeReceiptToFile(filteredApplications.get(chosenApplication-1));
							} catch (IndexOutOfBoundsException e) {
								System.out.println("Invalid application ID. Please enter a valid application ID.");
								continue;
							}
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
	/**
	 * Displays the features available to the user.
	 * 
	 * @param features the list of features to display
	 * @param index the starting index for numbering the features
	 * @return the updated index after displaying the features
	 */
	public static int displayFeatures(ArrayList<String> features, int index) {
		int i;
		for (i=0; i<features.size(); i++) {
			System.out.printf("%d. %s\n", i+1+index, features.get(i));
		}
		return index+i;
	}
}