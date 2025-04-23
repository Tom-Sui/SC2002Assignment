import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public abstract class BaseManagerOfficerRegistration implements I_ManagerOfficerRegistration{
	
	Date currentTime = new Date();	
	
	/**
	 * Rejects an officer's registration.
	 * <p>
	 * This helper method updates the officer's registration status from "PENDING" to "REJECTED"
	 * in the officer registration file. The update is performed by locating the record matching
	 * the specified officer NRIC.
	 * </p>
	 *
	 * @param filePath The path to the directory containing the registration file (must not be null)
	 * @param officerNRIC The NRIC of the officer to reject (must not be null or empty, used as unique identifier)
	 *
	 * @throws NullPointerException if either parameter is null
	 * @throws IllegalArgumentException if officerNRIC is empty
	 * @throws IOException if there is an error updating the registration file
	 *
	 * @see General#editOtherFile(String, String, String, String, String)
	 *
	 */       
    public void rejectRegistration(String filePath, String officerNRIC, HDBOfficer officer, Project project) {
    	General.editOtherFile(filePath,"/OfficerRegistrationList.txt", "REJECTED", "PENDING", officerNRIC);
        officer.setOfficerRegistrationStatus(project, OfficerRegistrationStatus.REJECTED);        
    }
    
    
	/**
	 * Approve an officer's registration.
	 * <p>
	 * This helper method updates the officer's registration status from "PENDING" to "APPROVED"
	 * in the officer registration file. The update is performed by locating the record matching
	 * the specified officer NRIC.
	 * </p>
	 *
	 * @param filePath The path to the directory containing the registration file (must not be null)
	 * @param officerNRIC The NRIC of the officer to reject (must not be null or empty, used as unique identifier)
	 *
	 * @throws NullPointerException if either parameter is null
	 * @throws IllegalArgumentException if officerNRIC is empty
	 * @throws IOException if there is an error updating the registration file
	 *
	 * @see General#editOtherFile(String, String, String, String, String)
	 *
	 */
    // Approve the officer registration
    public void approveRegistration(String filePath, String officerNRIC, HDBOfficer officer, Project project) {
    	General.editOtherFile(filePath,"/OfficerRegistrationList.txt", "APPROVED", "PENDING", officerNRIC);
        officer.setOfficerRegistrationStatus(project, OfficerRegistrationStatus.APPROVED);
    }
    
    
    // View enquries for all projects
    // public void viewEnquiries(ArrayList<Project> currentProjects) {
    	
    // 	for(Project project: currentProjects) {
    		
    // 		System.out.printf("Enquiries for Project %s\n", project.getProjectName());
    		
    // 		ArrayList<Enquiry> enquiries = project.getEnquiries();
    		
    // 		for(Enquiry enquiry: enquiries) {
    			
    // 			System.out.printf("Applicant ID: %d\n", enquiry.getApplicant());
    // 			System.out.printf("Message: %s\n", enquiry.getMessage());
    			
    // 			if(enquiry.getReplies().equals("")) {
    // 				System.out.printf("There is no reply yet");
    // 			} else {
    // 				System.out.printf("Reply: %s\n", enquiry.getReplies());
    // 			}
    // 		}
    // 	}
    // }

}

