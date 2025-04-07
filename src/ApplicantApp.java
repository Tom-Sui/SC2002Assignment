import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class ApplicantApp {
	public void start(Applicant applicant, ArrayList<Project> projectList) {
		Scanner sc = new Scanner(System.in);	
		projectList = ProjectLogic.filterProjectsByMaritalStatus(projectList, applicant.getMaritalStatus());
		// Display applicant details so that he/she can better understand the criteria they meet for the projects
		System.out.printf("Name: %s\n", applicant.getName());
		System.out.printf("Age: %d\n", applicant.getAge());
		System.out.printf("Marital Status: %s\n", applicant.getMaritalStatus());
		System.out.println("============================");
		System.out.println("List of Available Features:");
		ArrayList<String> applicantFeatures = new ArrayList<>(List.of(
			    "View Available Projects", 
			    "View Applied Projects", 
			    "Apply Project", 
			    "Book Flat", 
			    "Request Withdrawal"
		));
		
		int choice;
		do {
			// Display the list of features available
			int noOfFeatures = applicantFeatures.size();
			for (int i=0; i<noOfFeatures; i++) {
				System.out.printf("%d. %s\n", i+1, applicantFeatures.get(i));
			}
			System.out.printf("-1. Exit\n");
			System.out.println();
			System.out.printf("Enter your choice: ");
			choice = sc.nextInt();

			if (choice == 1) {
				applicant.viewAvailableProjects(projectList);
			}
			else if (choice == 2) {
				if (applicant.getCurrentApplication() != null) {
					System.out.println("Applied Project: " + applicant.getCurrentApplication().getProject().getProjectName());
					System.out.println("Application Status: " + applicant.viewApplicationStatus());
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
				applicant.bookFlat();
			}
		} while (choice != -1);


		
		
		
	}
}
