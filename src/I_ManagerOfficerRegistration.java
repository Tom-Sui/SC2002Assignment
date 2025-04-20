import java.util.ArrayList;

public interface I_ManagerOfficerRegistration {
	
	// Rejects an HDB officer's registration and updates the relevant records
	void rejectRegistration(String filePath, String officerNRIC, HDBOfficer officer, Project project);
	
	// Approves an HDB officer's registration and updates the relevant records
	void approveRegistration(String filePath, String officerNRIC, HDBOfficer officer, Project project);
	
	// Displays the list of pending or completed officer registration requests
	void viewOfficerRegistrationList();
	
	// Allows the manager to approve or reject an officer's registration request
	void approveOrRejectOfficerRegistration(ArrayList<Project> currentProjects, ArrayList<Applicant> applicants, ArrayList<HDBOfficer> HDBOfficers, String userName);
	
	// Displays enquiries related to all projects for review by the manager
	void viewEnquiries(ArrayList<Project> currentProjects);
}
