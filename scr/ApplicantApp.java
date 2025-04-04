import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class ApplicantApp {
	public void start(Applicant applicant, ArrayList<Project> projectList) {
		Scanner sc = new Scanner(System.in);	
		// Display applicant details so that he/she can better understand the criteria they meet for the projects
		System.out.printf("Name: %s\n", applicant.getName());
		System.out.printf("Age: %d\n", applicant.getAge());
		System.out.printf("Marital Status: %s\n", applicant.getMatritialSatus());
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
			System.out.printf("Enter your choice: ");
			choice = sc.nextInt();
			

			if (choice == 1) {
				applicant.viewAvailableProjects(projectList);
			}
			else if (choice == 2) {
				applicant.viewAppliedProjects(projectList);
				System.out.printf("Enter the project number to apply for: ");	
				int projectNumber = sc.nextInt();
				if (applicant.getAppliedProject() == null){
					applicant.applyForProject(projectList.get(projectNumber-1));
				}
				else{
					System.out.println("Cannot apply for project, already applied for a project: " + applicant.getAppliedProject().getProjectName());
				}
			}
			
		} while (choice != -1);


		
		
		
	}
}
