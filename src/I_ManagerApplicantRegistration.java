import java.util.ArrayList;

public interface I_ManagerApplicantRegistration {
	
	// Allows the manager to approve or reject an applicant's registration
	void approveOrRejectApplication(ArrayList<Project> currentProjects, ArrayList<Applicant> applicants, String username);
	
	 // Approves an applicant's withdrawal request
	void approveWithdrawal(ArrayList<Project> currentProjects, ArrayList<Applicant> applicants);
	
	// Generates a report of applicants filtered accordingly
	void generateApplicantReport(ArrayList<Applicant> applicants, String maritalStatusFilter, Integer minAge, Integer maxAge);
		
}
