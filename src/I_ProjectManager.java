import java.util.ArrayList;

// Interface class for project management
public interface I_ProjectManager {
    
    // Method to set or assign a project to be managed by the Project Manager.
    void setManagedProjects(Project project);
    
    // Method to retrieve the currently active project for a specific user (identified by userName).
    // If printCheck is true, additional details about the active project may be printed.
    Project currentActiveProject(ArrayList<Project> currentProjects, String userName, boolean printCheck);
    
    // Method to search for and return a specific project from the list of currentProjects.
    Project returnProject(ArrayList<Project> currentProjects, String projectName);
    
    // Method to check if a project's deadline has passed.
    boolean PastDateCheckerProject(ArrayList<Project> currentProjects, String projectName);
    
    // Method to determine if a project is still within its application period.
    boolean isWithinApplicationPeriod(ArrayList<Project> currentProjects, String projectName);
    
    // Method to toggle the visibility of a specific project.
    void toggleVisibility(ArrayList<Project> currentProjects, String projectName, String username);
    
    // Method to perform a bulk check for past deadlines across all projects in the list.
    void PastDateChecker(ArrayList<Project> currentProjects);

    // Method to create a new project and add it to the system.
    ArrayList<Project> createProject(String projectDetails, ArrayList<HDBManager> hdbManager, ArrayList<HDBOfficer> hdbOfficer);
    
    // Method to edit an existing project's details.
    boolean editProject(String projectName, String updateContent, String target, ArrayList<Project> currentProjects, int i);
    
    // Method to delete a specific project from the system.
    Project deletProject(Project targetProject);
}