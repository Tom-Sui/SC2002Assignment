import java.util.ArrayList;

public interface I_ListOutProjects {
	
	// Lists all existing projects in the system for viewing
	void listAllExistingProjects(ArrayList<Project> currentProjects);
		
	// Lists projects specific to a user, identified by their username
	void listSpecificProjects(ArrayList<Project> currentProjects, String username);
}
