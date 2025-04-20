import java.util.ArrayList;
import java.util.Scanner;

public class ReplyEnquiry implements I_Enquiry{
    /**
	 * To approve or reject application to certain project
	 * @param applicant Application to be approved or rejected
	 * @param project Target project
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
