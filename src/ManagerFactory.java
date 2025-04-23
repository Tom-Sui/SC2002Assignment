import java.util.ArrayList;
/**
 * Interface for listing out projects in the system.
 * <p>
 * This interface defines methods for listing all existing projects and specific projects
 * associated with a particular user.
 * </p>
 * 
 * @see Project
 */
// Contains all the calling functions for Manager
public class ManagerFactory {
	/**
	 * Creates a default instance of the HDBManager class with default parameters.
	 * <p>
	 * This method initializes the HDBManager with a ProjectManager, InputValidator,
	 * ManagerOfficerRegistration, ManagerApplicantRegistration, and ReplyEnquiry.
	 * </p>
	 * 
	 * @return A new instance of HDBManager with default parameters
	 */
	public static HDBManager defaultManager()
	{
		ArrayList<Project> managedProjects = new ArrayList<>(); 
		
		ProjectManager projectManager = new ProjectManager(managedProjects);

		return new HDBManager(projectManager, projectManager, 
				  			  new InputValidator(), new ManagerOfficerRegistration(), 
				  			  new ManagerApplicantRegistration());
	}
}
