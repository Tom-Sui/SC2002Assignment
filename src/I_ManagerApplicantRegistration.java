import java.util.ArrayList;
/**
 * This interface defines methods for managing applicant registrations.
 * It includes methods for approving or rejecting applications,
 * approving withdrawal requests, and generating reports of applicants.
 * 
 * @see Applicant
 * @see Project
 */
public interface I_ManagerApplicantRegistration {
	/**
	 * Registers a new applicant in the system.
	 * <p>
	 * This method allows a manager to register a new applicant by providing their details.
	 * </p>
	 * 
	 * @param currentProjects The list of current projects in the system
	 * @param applicants The list of applicants in the system
	 * @param username The NRIC of the HDB Manager or Project Officer managing the project
	 */
	// Allows the manager to approve or reject an applicant's registration
	void approveOrRejectApplication(ArrayList<Project> currentProjects, ArrayList<Applicant> applicants, String username);
	/**
	 * Processes an applicant's withdrawal request and updates the relevant records.
	 * <p>
	 * This method allows a manager to process an applicant's withdrawal request by providing their NRIC and the reason for withdrawal.
	 * </p>
	 * 
	 * @param currentProjects The list of current projects in the system
	 * @param applicants The list of applicants in the system
	 * 
	 */

	 // Approves an applicant's withdrawal request
	void approveWithdrawal(ArrayList<Project> currentProjects, ArrayList<Applicant> applicants);
	/**
	 * Generates a report of applicants based on specified filters.
	 * <p>
	 * This method allows a manager to generate a report of applicants by providing filters such as marital status and age range.
	 * </p>
	 * 
	 * @param applicants The list of applicants in the system
	 * @param maritalStatusFilter The marital status filter for the report
	 * @param minAge The minimum age filter for the report
	 * @param maxAge The maximum age filter for the report
	 */
	// Generates a report of applicants filtered accordingly
	void generateApplicantReport(ArrayList<Applicant> applicants, String maritalStatusFilter, Integer minAge, Integer maxAge);
		
}
