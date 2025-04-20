import java.util.ArrayList;
import java.util.Scanner;

public interface I_Enquiry {
	
	// Reply to enquiry function for both Manager and Officer
	void replyToEnquiry(ArrayList<Project> currentProjects, String NRIC, Scanner scanner);
}
