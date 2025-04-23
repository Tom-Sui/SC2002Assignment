import java.util.ArrayList;
/**
 * This interface defines methods for listing projects in a housing project application system.
 * It includes methods for listing all existing projects and specific projects based on user criteria.
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