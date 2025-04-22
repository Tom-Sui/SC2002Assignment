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
public interface I_ListOutProjects {
	/**
	 * Lists all existing projects in the system for viewing.
	 * <p>
	 * This method retrieves and displays all projects currently available in the system.
	 * </p>
	 * 
	 * @param currentProjects The list of current projects in the system
	 */
	// Lists all existing projects in the system for viewing
	void listAllExistingProjects(ArrayList<Project> currentProjects);
	/**
	 * Lists projects specific to a user, identified by their username.
	 * <p>
	 * This method retrieves and displays all projects associated with a specific user.
	 * </p>
	 * 
	 * @param currentProjects The list of current projects in the system
	 * @param username The username of the user whose projects are to be listed
	 */
	// Lists projects specific to a user, identified by their username
	void listSpecificProjects(ArrayList<Project> currentProjects, String username);
}
