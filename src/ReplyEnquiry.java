import java.util.ArrayList;
import java.util.Scanner;

public class ReplyEnquiry implements I_Enquiry {
    private final EnquiryService enquiryService;

    public ReplyEnquiry() {
        this.enquiryService = new EnquiryService();
    }

    /**
	 * To approve or reject application to certain project
	 * @param applicant Application to be approved or rejected
	 * @param project Target project
	 */
	public void replyToEnquiry(ArrayList<Project> currentProjects, String NRIC, Scanner scanner) {
		for (Project project : currentProjects) {
			if (project.getHDBManager().getNRIC().equals(NRIC) || project.getHDBOfficer().contains(NRIC)) {
				ArrayList<Enquiry> enquiries = enquiryService.getEnquiriesByProject(project.getProjectName());
				
				for (Enquiry enquiry : enquiries) {
					System.out.printf("Enquiry: %s\n", enquiry.getMessage());
					
					if (!enquiry.hasReply()) {
						String answer = "";
						while (true) {
							System.out.println("Would you like to reply to this enquiry? Type Yes/No \n");
							answer = scanner.nextLine().trim();
							
							if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("no")) {
								break;
							} else {
								System.out.println("Please enter Yes or No.");
							}
						}
						
						if (answer.equalsIgnoreCase("yes")) {
							System.out.println("Enter your reply: ");
							String reply = scanner.nextLine().trim();
							
							Enquiry replyEnquiry = new Enquiry(enquiry.getUserNric(), enquiry.getProjectName(), reply, 0);
							enquiryService.addEnquiry(replyEnquiry);
							enquiry.setReplyID(replyEnquiry.getEnquiryID());
							enquiryService.writeEnquiriesToFile();
							
							System.out.println("Reply successfully recorded.");
						} else {
							System.out.println("Skipping this enquiry.");
							continue;
						}
					}
					
					if (enquiry.hasReply()) {
						Enquiry replyEnquiry = enquiryService.getEnquiryById(enquiry.getReplyID());
						if (replyEnquiry != null) {
							System.out.printf("Reply: %s\n", replyEnquiry.getMessage());
						}
					}
				}
			} else {
				System.out.println("Invalid NRIC. Please try again!\n");
				continue;
			}
		}
	}
}
