import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class implements the I_Enquiry interface and provides methods for replying to enquiries made by applicants.
 * <p>
 * It includes functionality to check if the NRIC matches either the HDB Manager or Project Officer, prompt for a reply,
 * and update the enquiry status accordingly.
 * </p>
 * 
 * @see I_Enquiry
 * @see Enquiry
 * @see Project
 */
public class ReplyEnquiry implements I_Enquiry{
	/**
	 * This method allows the HDB Manager or Project Officer to reply to enquiries.
	 * It checks if the NRIC matches either role and prompts for a reply.
	 * If the reply is valid, it updates the enquiry status and records the reply.
	 * 
	 * @param currentProjects list of current projects (must not be null, used to verify project references)
	 * @param NRIC the NRIC of the HDB Manager or Project Officer
	 * @param scanner scanner object for user input
	 */
	public void replyToEnquiry(ArrayList<Project> currentProjects, String NRIC, Scanner scanner) {
		
	    for (Project project : currentProjects) {
	    	
	        // Check if the NRIC matches either the HDB Manager or Project Officer
	        if (project.getHDBManager().equals(NRIC) || project.getHDBOfficer().equals(NRIC)) {
	            ArrayList<Enquiry> enquiries = project.getEnquiries();

	            for (Enquiry enquiry : enquiries) {
	                System.out.printf("Enquiry: %s\n", enquiry.getMessage());

	                // Check if the enquiry has already been replied to
	                if (!enquiry.getReplyBoolean()) {
	                    String answer = "";

	                    // Loop until the user provides a valid response
	                    while (true) {
	                        System.out.println("Would you like to reply to this enquiry? Type Yes/No \n");
	                        answer = scanner.nextLine().trim(); // Trim to remove leading/trailing spaces

	                        if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("no")) {
	                            break;
	                            
	                        } else {
	                            System.out.println("Please enter Yes or No.");
	                        }
	                    }

	                    // Handle the reply logic
	                    if (answer.equalsIgnoreCase("Yes")) {
	                        System.out.println("Enter your reply: ");
	                        String reply = scanner.nextLine().trim(); // Trim to remove leading/trailing spaces

	                        enquiry.setStringReply(reply);
	                        enquiry.setReplyBoolean(true);

	                        System.out.println("Reply successfully recorded.");
	                        
	                    } else if (answer.equalsIgnoreCase("No")) {
	                        System.out.println("Skipping this enquiry.");
	                        continue;
	                    }
	                }

	                // Print the reply if it exists
	                System.out.printf("Reply: %s\n", enquiry.getStringReply());
	            }
	        } else {
	            // Skip projects where the NRIC does not match either role
	        	System.out.println("Invalid NRIC. Please try again!\n");
	            continue;
	        }
	    }
	}
}
