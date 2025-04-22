import java.util.ArrayList;
import java.util.Scanner;
/**
 * Interface for handling enquiries related to housing projects.
 * <p>
 * This interface defines the method for replying to an enquiry made by an applicant.
 * </p>
 * 
 * @see Enquiry
 * @see Project
 */
public interface I_Enquiry {
	/**
	 * Replies to an enquiry made by an applicant.
	 * <p>
	 * This method allows a manager or officer to respond to an enquiry related to a specific project.
	 * </p>
	 * 
	 * @param currentProjects The list of current projects in the system
	 * @param NRIC The NRIC of the user replying to the enquiry
	 * @param scanner The Scanner object for user input
	 */
	// Reply to enquiry function for both Manager and Officer
	void replyToEnquiry(ArrayList<Project> currentProjects, String NRIC, Scanner scanner);
}
