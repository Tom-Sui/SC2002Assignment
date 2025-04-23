import java.util.ArrayList;

// Contains all the calling functions for Manager
public class ManagerFactory {
	public static HDBManager defaultManager()
	{
		ArrayList<Project> managedProjects = new ArrayList<>(); 
		
		ProjectManager projectManager = new ProjectManager(null, managedProjects);

		return new HDBManager(projectManager, projectManager, 
				  			  new InputValidator(), new ManagerOfficerRegistration(), 
				  			  new ManagerApplicantRegistration());
	}
}
