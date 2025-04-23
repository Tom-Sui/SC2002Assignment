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
// Interface class for project management
public interface I_ProjectManager {
    /**
     * Method to set or assign a project to be managed by the Project Manager.
     * <p>
     * This method sets or assigns a project to be managed by the Project Manager.
     * </p>
     * @param project The project to be managed
     */

    void setManagedProjects(Project project);

    /**
     * Method to retrieve the currently active project for a specific user (identified by userName).
     * If printCheck is true, additional details about the active project may be printed.
     * <p>
     * This method retrieves the currently active project for a specific user and prints details if required.
     * </p>
     * @param currentProjects The list of current projects in the system
     * @param userName The NRIC of the HDB Manager or Project Officer managing the project
     * @param printCheck A flag to indicate whether to print additional details about the active project
     * @return The currently active project for the specified user
     */

    Project currentActiveProject(ArrayList<Project> currentProjects, String userName, boolean printCheck);
    // Method to search for and return a specific project from the list of currentProjects.
    /**
     * <p>
     * This method searches for a specific project in the list of current projects and returns it.
     * </p>
     * @param currentProjects The list of current projects in the system
     * @param projectName The name of the project to be searched for
     * @return The project if found, null otherwise
     */

    Project returnProject(ArrayList<Project> currentProjects, String projectName);
    // Method to check if a project's deadline has passed.
    /**
     * <p>
     * This method checks if a project's deadline has passed based on the current date.
     * </p>
     * @param currentProjects The list of current projects in the system
     * @param projectName The name of the project to be checked
     * @return true if the project deadline has passed, false otherwise
     */

    boolean PastDateCheckerProject(ArrayList<Project> currentProjects, String projectName);
    /**
     * Method to determine if a project is still within its application period.
     * <p>
     * This method checks if a project is still within its application period based on the current date.
     * </p>
     * @param currentProjects The list of current projects in the system
     * @param projectName The name of the project to be checked
     * @return true if the project is within the application period, false otherwise
     */

    boolean isWithinApplicationPeriod(ArrayList<Project> currentProjects, String projectName);
    /**
     * Method to toggle the visibility of a specific project.
     * <p>
     * This method toggles the visibility of a project for a specific user.
     * </p>
     * @param currentProjects The list of current projects in the system
     * @param projectName The name of the project to be toggled
     * @param username The NRIC of the HDB Manager or Project Officer managing the project
     */

    void toggleVisibility(ArrayList<Project> currentProjects, String projectName, String username);
    // Method to perform a check for past deadlines across all projects in the list.
    /**
     * <p>
     * This method checks if any projects in the list have past deadlines and updates their status accordingly.
     * </p>
     * @param currentProjects The list of current projects in the system
     */
    void PastDateChecker(ArrayList<Project> currentProjects);
    // Method to set status across all projects in the list
    /**
     * <p>
     * This method sets the status of all projects in the list to a specified value.
     * </p>
     * @param currentProjects The list of current projects in the system
     */
    void updateMaritalStatus(ArrayList<Project> currentProjects);
    // Method to create a new project and add it to the system.
    /**
     * <p>
     * This method allows a manager to create a new project and add it to the system.
     * </p>
     * @param projectDetails The details of the project to be created
     * @param hdbManager The list of HDB managers in the system
     * @param hdbOfficer The list of HDB officers in the system
     * @return The list of projects after adding the new project
     */
    ArrayList<Project> createProject(String projectDetails, ArrayList<HDBManager> hdbManager, ArrayList<HDBOfficer> hdbOfficer);
    //Method to edit an existing project's details.
    /**
     * <p>
     * This method allows a manager to edit the details of an existing project.
     * </p>
     * @param updateContent The new content to update the project with
     * @param target The target project to be updated
     * @param currentProjects The list of current projects in the system
     * @param i The index of the project to be updated
     * @return true if the project was successfully edited, false otherwise
     */
    boolean editProject(String updateContent, String target, ArrayList<Project> currentProjects, int i);

    /**
     * Method to delete a specific project from the system.
     * <p>
     * This method allows a manager to delete a project from the system.
     * </p>
     * @param targetProject The project to be deleted
     * @return The deleted project
     */
    Project deletProject(Project targetProject);
}