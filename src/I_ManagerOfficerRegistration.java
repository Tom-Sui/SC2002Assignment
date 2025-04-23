import java.util.ArrayList;
/**
 * Interface for managing officer registration requests in the HDB system.
 * <p>
 * This interface defines methods for approving or rejecting officer registrations,
 * viewing pending or completed requests, and handling enquiries related to projects.
 * </p>
 * 
 * @see Project
 * @see HDBOfficer
 * @see Applicant
 */
public interface I_ManagerOfficerRegistration {
	// Rejects an HDB officer's registration and updates the relevant records
	/**
	 * Rejects an HDB officer's registration and updates the relevant records.
	 * <p>
	 * This method allows a manager to reject an HDB officer's registration by providing their NRIC and the reason for rejection.
	 * </p>
	 * 
	 * @param filePath file path to the directory containing the registration file
	 * @param officerNRIC The NRIC of the officer to reject
	 * @param officer The HDB officer object to be rejected
	 * @param project The project associated with the officer's registration
	 */

	void rejectRegistration(String filePath, String officerNRIC, HDBOfficer officer, Project project);
	// Approves an HDB officer's registration and updates the relevant records
	/**
	 * Approves an HDB officer's registration and updates the relevant records.
	 * <p>
	 * This method allows a manager to approve an HDB officer's registration by providing their NRIC.
	 * </p>
	 * 
	 * @param filePath file path to the directory containing the registration file
	 * @param officerNRIC The NRIC of the officer to approve
	 * @param officer The HDB officer object to be approved
	 * @param project The project associated with the officer's registration
	 */
	void approveRegistration(String filePath, String officerNRIC, HDBOfficer officer, Project project);
	// Displays the list of pending or completed officer registration requests
	/**
	 * Displays the list of pending or completed officer registration requests.
	 * <p>
	 * This method reads the officer registration file and prints the details of each request.
	 * </p>
	 * 
	 */

	void viewOfficerRegistrationList();
	// Allows the manager to approve or reject an officer's registration request
	/**
	 * Allows the manager to approve or reject an officer's registration request.
	 * <p>
	 * This method allows a manager to approve or reject an officer's registration request by providing their NRIC and the reason for rejection.
	 * </p>
	 * 
	 * @param currentProjects The list of current projects in the system
	 * @param applicants The list of applicants in the system
	 * @param HDBOfficers The list of HDB officers in the system
	 * @param userName The NRIC of the HDB Manager or Project Officer managing the project
	 */
	void approveOrRejectOfficerRegistration(ArrayList<Project> currentProjects, ArrayList<Applicant> applicants, ArrayList<HDBOfficer> HDBOfficers, String userName);
	// Displays enquiries related to all projects for review by the manager
	/**
	 * Displays enquiries related to all projects for review by the manager.
	 * <p>
	 * This method allows a manager to view all enquiries made by applicants regarding housing projects.
	 * </p>
	 * 
	 * @param currentProjects The list of current projects in the system
	 */
	void viewEnquiries(ArrayList<Project> currentProjects);
}
