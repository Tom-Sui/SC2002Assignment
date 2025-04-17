import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The main application interface for applicants in the housing system.
 * Provides a console-based menu system for applicants to interact with housing projects.
 * 
 * @see Applicant
 * @see Project
 */
public class ApplicantApp {
    
    /**
     * Starts the applicant application interface.
     * Displays a menu of options and processes user input to perform housing-related actions.
     * 
     * @param applicant the Applicant object using the application
     * @param projectList the list of available housing projects
     */
    public void start(Applicant applicant, ArrayList<Project> projectList) {
        Scanner sc = new Scanner(System.in);    
        // Filter projects based on applicant's marital status
        projectList = ProjectLogic.filterProjectsByMaritalStatus(projectList, applicant.getMaritalStatus());
        
        // Display applicant details for reference
        System.out.printf("Name: %s\n", applicant.getName());
        System.out.printf("Age: %d\n", applicant.getAge());
        System.out.printf("Marital Status: %s\n", applicant.getMaritalStatus());
        
        // Menu options
        ArrayList<String> applicantFeatures = new ArrayList<>(List.of(
                "View Available Projects", 
                "View Applied Projects", 
                "Apply Project", 
                "Book Flat", 
                "Request Withdrawal"
        ));
        
        int choice;
        do {
            // Display menu
            System.out.println("============================");
            System.out.println("List of Available Features:");
            int noOfFeatures = applicantFeatures.size();
            for (int i = 0; i < noOfFeatures; i++) {
                System.out.printf("%d. %s\n", i+1, applicantFeatures.get(i));
            }
            System.out.printf("-1. Exit\n");
            System.out.println();
            System.out.printf("Enter your choice: ");
            choice = sc.nextInt();

            if (choice == 1) {
                // View available projects
                System.out.println("\nList of available projects:");
                System.out.println("============================");
                applicant.viewAvailableProjects(projectList);
            }
            else if (choice == 2) {
                // View current application status
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
                // Apply for a project
                if (applicant.getCurrentApplication() != null) {
                    System.out.println("Cannot apply for project, already applied for a project: " + 
                                      applicant.getCurrentApplication().getProject().getProjectName());
                    continue;
                }
                applicant.viewAvailableProjects(projectList);
                System.out.printf("Enter the project number to apply for: ");    
                int projectNumber = sc.nextInt();
                Project project = projectList.get(projectNumber-1);
                ArrayList<FlatType> filteredFlatTypes = FlatTypeLogic.filterFlatTypesByMaritalStatus(
                    project.getFlatTypes(), applicant.getMaritalStatus());
                
                if (filteredFlatTypes.isEmpty()) {
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
                // Book a flat
                Application currentApplication = applicant.getCurrentApplication();
                if (currentApplication == null || 
                    currentApplication.getApplicationStatus() != ApplicationStatus.SUCCESSFUL || 
                    currentApplication.getBookingRequested() == true) {
                    break;
                }
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
            else if (choice == 5) { 
                // Request withdrawal from application
                Application currentApplication = applicant.getCurrentApplication();
                if (currentApplication == null) { 
                    System.out.println("No application found.");
                    continue;
                } 
                applicant.requestWithdrawal();
                System.out.println("Withdrawal request is sent.");
            }
        } while (choice != -1);
    }
}