import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * BaseProjectManager is an abstract class that implements the I_ProjectManager and I_ListOutProjects interfaces.
 * It provides a foundation for managing housing projects, including functionalities for listing, updating,
 * and checking project details.
 * <p>
 * This class contains methods to manage projects, check their status, and print relevant information.
 * </p>
 * 
 * 
 * @see I_ProjectManager
 * @see I_ListOutProjects
 */
// Contains all logic from interface
public abstract class BaseProjectManager implements I_ProjectManager, I_ListOutProjects{
	/**
	 * List of projects managed by this manager.
	 * <p>
	 * This list contains all the projects that are currently being managed by this manager.
	 * </p>
	 */
	protected ArrayList<Project> managedProjects = new ArrayList<>(); 
	
	DateFormat formatter = new SimpleDateFormat("d/M/yyyy");
    Date currentDate = new Date();
	
	/**
	 * Constructor for BaseProjectManager.
	 * <p>
	 * Initializes the managedProjects list.
	 * </p>
	 * 
	 * @param managedProjects the list of projects to be managed (must not be null)
	 */
	// public BaseProjectManager(General general, ArrayList<Project> managedProjects)
	public BaseProjectManager(ArrayList<Project> managedProjects)
	{
		this.managedProjects = managedProjects;
	}
	
	/**
     * Update managed projects that are managed initialized manager
     * 
     * @param project the specific project that meant to be managed by particular manager
     */
    //set managed projects
	@Override
    public void setManagedProjects(Project project){
        this.managedProjects.add(project);
    }
	
        
    /**
	 * Finds and returns the currently active project for the specified manager from a list of projects.
	 * An active project is defined as one where:
	 * <ul>
	 *   <li>Under specific HDB manager's managed projects</li>
	 *   <li>The current date is within the project's application period</li>
	 *   <li>The project is visible</li>
	 * </ul>
	 * 
	 * @param currentProjects the list of projects to search through (must not be null)
	 * @param userName the name of the HDB manager to match (must not be null or empty)
	 * @param printCheck flag to control whether to print status messages:
	 *                  - If true, prints active project details when found or "No Active Project" message when none found
	 *                  - If false, operates silently
	 * @return the currently active project if found, null otherwise
	 * 
	 * @see Project
	 */
    // return Current Active Project (filter manager name)
	@Override
    public Project currentActiveProject(ArrayList<Project> currentProjects, String userName, boolean printCheck)
    {    	
    	for (int i=0; i<currentProjects.size(); i++)
    	{
    		// check for current manager projects only
    		if (currentProjects.get(i).getHDBManager().getNRIC().equals(userName))
    		{
    			// run through to return project within application period 
    			// if within application period and visibility is ON
    			if (!currentDate.before(currentProjects.get(i).getApplicationOpeningDate()) && !currentDate.after(currentProjects.get(i).getApplicationClosingDate()) && currentProjects.get(i).getVisibility() == true)
    			{
    				if(printCheck)
    				{	// print active project only when true
    					this.listRequiredProjects(currentProjects, i);    					
    				}
    				// return project within application period
    				return currentProjects.get(i);
    			}
    		}
    	}    	
    	if(printCheck)
    	{
    		System.out.println("No Active Project at the moment!\n");    		
    	}
    	return null;
    }
	
    /**
	 * Searches for and returns a specific project by its unique name from a list of projects.
	 * 
	 * <p>This method performs a linear search through the provided list of projects
	 * to find a project with a matching name (case-sensitive comparison). The project name
	 * serves as a unique identifier for this lookup operation.</p>
	 *
	 * @param currentProjects The list of projects to search through (must not be null)
	 * @param projectName The name of the project to find (case-sensitive, must not be null or empty)
	 * @return The found {@link Project} object if exists, null if no matching project is found
	 * 
	 * @throws NullPointerException if either currentProjects or projectName is null
	 * 
	 * @see Project#getProjectName()
	 */
    // to check if Project is valid -- return that specific project using projectName (Unique indicator)
	@Override
	public Project returnProject(ArrayList<Project> currentProjects, String projectName) {
    	
    	for (int i=0; i<currentProjects.size(); i++)
    	{
    		// check if project is valid
    		if (currentProjects.get(i).getProjectName().equals(projectName))
    		{  
    			// return valid project
    			return currentProjects.get(i);
    		}
    	}    	
    	// invalid project	
    	return null;
    }
    
    /**
	 * Prints list of all existing projects with their details.
	 * 
	 * @param currentProjects the list of projects to display (must not be null, but can be empty)
	 * 
	 * @see #listRequiredProjects(ArrayList, int)
	 * 
	 * The output format is as follows:
	 * <pre>
	 * === List of All Existing Projects ===
	 * 
	 * [Project 1 details - same format as listRequiredProjects]
	 * [Project 2 details]
	 * ...
	 * 
	 * Total Projects: [count]
	 * =============================== 
	 * </pre>
	 * 
	 * Example output:
	 * <pre>
	 * === List of All Existing Projects ===
	 * 
	 * Project Name: Sunshine Residences | Neighborhood: Tampines | Visibility: true
	 *    - Flat Details:
	 *       - Flat Type: 3-Room, Units: 150, Price: $300000.00
	 *       - Flat Type: 4-Room, Units: 100, Price: $400000.00
	 *    - Application Period: Mon Jan 01 00:00:00 SGT 2024 to Fri Jan 12 23:59:59 SGT 2024
	 *    - HDB Manager: John Tan
	 *    - Available HDB Officer Slots: 2
	 *    - HDB Officer In-Charge: Sarah Lim Michael Wong
	 * 
	 * Total Projects: 1
	 * =============================== 
	 * </pre>
	 */
    // print out all the existing projects
	@Override
    public void listAllExistingProjects(ArrayList<Project> currentProjects) {
    	
    	System.out.println("=== List of All Existing Projects ===\n");
    	for (int i=0; i<currentProjects.size(); i++)
    	{
    		this.listRequiredProjects(currentProjects, i);
    	}
    	
    	System.out.printf("Total Projects: %d\n", currentProjects.size());
    	System.out.println("=============================== \n");
    }
    
    /**
	 * Prints list of projects managed by a specific HDB manager.
	 * <p>
	 * This method displays all projects where the specified user is the assigned HDB manager,
	 * including complete project details and a total count of matching projects.
	 * </p>
	 * 
	 * @param currentProjects the list of projects to filter through (must not be null, but can be empty)
	 * @param username the name of the HDB manager to filter by (case-sensitive, must not be null or empty)
	 * 
	 * @see #listRequiredProjects(ArrayList, int)
	 * 
	 * The output includes:
	 * <pre>
	 * === List of All Your Existing Projects ===
	 * 
	 * [Project details for manager's projects - same format as listRequiredProjects]
	 * ...
	 * 
	 * Total Projects: [count]
	 * =============================== 
	 * </pre>
	 * 
	 */
    // print out only manager-in-charge projects (filter manager name)
	@Override
    public void listSpecificProjects(ArrayList<Project> currentProjects, String username)
    {
    	int count=0;
    	System.out.println("=== List of All Your Existing Projects ===\n");
    	
    	for (int i=0; i<currentProjects.size(); i++)
    	{
    		// to check for current userName and print out only their projects
    		if (currentProjects.get(i).getHDBManager().getNRIC().equals(username))
    		{
    			this.listRequiredProjects(currentProjects, i);
    			count += 1;
    		}
    	}
    	
		System.out.printf("Total Projects: %d\n", count);
		System.out.println("=============================== \n");
    }
    
    /**
	 * Toggles the visibility status of a specified project and updates both the runtime list and storage.
	 * <p>
	 * This method performs the following operations:
	 * <ol>
	 *   <li>Locates the project by name in the provided list</li>
	 *   <li>Inverts the current visibility status (ON to OFF or OFF to ON)</li>
	 *   <li>Updates the project's visibility in the ProjectList.txt file</li>
	 *   <li>Updates the runtime Project object</li>
	 * </ol>
	 * 
	 * @param currentProjects the list of projects to search through (must not be null)
	 * @param projectName the exact name of the project to modify (case-sensitive, must not be null or empty)
	 * 
	 * @see General
	 * @see Project
	 * 
	 */
    // return boolean visibility to check for Applicant & HDB Manager
	@Override
    public void toggleVisibility(ArrayList<Project> currentProjects, String projectName, String username){
    	
    	//We are only toggling the visibility of the project that the manager wants to toggle
    	for (int i=0; i<currentProjects.size(); i++) {
    		if(currentProjects.get(i).getProjectName().equals(projectName)) {
    			
    			if(currentProjects.get(i).getVisibility()) {
    				   				
    				// update project.java side
    				currentProjects.get(i).setVisibility(false);    
    				
    				// update the .txt file 
    				General.editProjectFile(currentProjects.get(i));
    				
    				System.out.printf("Updated Project Visibility: '%s' -- OFF.\n", currentProjects.get(i).getProjectName());    				
    			} 
    			else 
    			{   		    				   				
    				if (this.currentActiveProject(currentProjects, username, false) != null)
    				{
    					System.out.println("Only 1 active project allowed.\n");
    				}
    				
    				else
    				{
    					// update project.java side
        				currentProjects.get(i).setVisibility(true);
    					// update the .txt file 
    					General.editProjectFile(currentProjects.get(i));
    					
    					System.out.printf("Updated Project Visibility: '%s' -- ON.\n", currentProjects.get(i).getProjectName());
    				}
    			}    			    			
    		}
    	}
    	
    	
    }
    
    /**
	 * Prints detailed information about a specific project from the list of projects.
	 * This method displays a formatted output containing all relevant project details
	 * 
	 * @param currentProjects the list of projects to search through (must not be null)
	 * @param i the index of the project to display in the currentProjects list (must be a valid index)
	 * 
	 * @see Project
	 * @see FlatType
	 * 
	 * The output format:
	 * <pre>
	 * Project Name: [name] | Neighborhood: [neighborhood] | Visibility: [visibility]
	 *    - Flat Details:
	 *       - Flat Type: [type], Units: [units], Price: $[price]
	 *       - [additional flat types...]
	 *    - Application Period: [start date] to [end date]
	 *    - HDB Manager: [manager name]
	 *    - Available HDB Officer Slots: [number]
	 *    - HDB Officer In-Charge: [officer1] [officer2]... or "Not set"
	 * </pre>
	 */
    // General Function: print out all the required projects (Place inside loop, with index specified)
	private void listRequiredProjects(ArrayList<Project> currentProjects, int i) {
    	
		System.out.printf("Project Name: %s | Neighborhood: %s | Visibility: %s \n", currentProjects.get(i).getProjectName(), currentProjects.get(i).getNeiborhood(), currentProjects.get(i).getVisibility());  
		
		// CALL flat types
		System.out.println("   - Flat Details:");
		
		ArrayList<FlatType> flatDetails = currentProjects.get(i).getFlatTypes();
				
		for (FlatType flatD : flatDetails)
		{
			System.out.printf("      - Flat Type: %s, Units: %d, Price: $%.2f\n", flatD.getFlatTypeName(), flatD.getUnits(), flatD.getPrice());		
			System.out.printf("        - Eligible Group(s): %s\n", flatD.getAllowedGroups());
		}			
		
		System.out.printf("   - Application Period: %s to %s \n", currentProjects.get(i).getApplicationOpeningDate(), currentProjects.get(i).getApplicationClosingDate());
		System.out.printf("   - HDB Manager: %s \n", currentProjects.get(i).getHDBManager().getName());
		System.out.printf("   - Available HDB Officer Slots: %s \n", currentProjects.get(i).getAvailableOfficerSlots());
		
		// Officer in ArrayList, need a loop to get each name
		System.out.print("   - HDB Officer In-Charge:");
		for (int k=0; k < currentProjects.get(i).getHDBOfficer().size(); k++)
		{
			if (currentProjects.get(i).getHDBOfficer().get(k) != null)
			{
				System.out.printf(" %s ", currentProjects.get(i).getHDBOfficer().get(k).getName());  		    					
			}
			else
			{
				System.out.print(" Not set");
				break;
			}
		}    			   			
		System.out.println("\n");    		
    }
    
    
    /**
	 * Checks whether the specified project is within its application period.
	 * <p>
	 * The method searches for a project with the given name in the provided project list and verifies
	 * if the current system time falls between the project's application opening and closing dates.
	 * </p>
	 * 
	 * @param currentProjects the list of projects to search through (must not be null or contain null elements)
	 * @param projectName the name of the project to check (must not be null or empty)
	 * @return {@code true} if:
	 *         <ul>
	 *           <li>The project exists in the list</li>
	 *           <li>The current time is within application period</li>
	 *         </ul>
	 *         {@code false} if:
	 *         <ul>
	 *           <li>The project is not found in the list</li>
	 *           <li>The current time is not within the application period</li>
	 *         </ul>
	 * 
	 * @see Project#getProjectName()
	 * @see Project#getApplicationOpeningDate()
	 * @see Project#getApplicationClosingDate()
	 */
    // return Boolean to check if Project passed in is within the application period (ProjectName as indicator to which project)
    public boolean isWithinApplicationPeriod(ArrayList<Project> currentProjects, String projectName)
    {
    	Date currentTime = new Date();
    	
    	for(int i=0; i<currentProjects.size(); i++)
    	{
    		// find the project
    		if(currentProjects.get(i).getProjectName().equals(projectName))
    		{
    			// Check if the current time is within the application period
    			return !currentTime.before(currentProjects.get(i).getApplicationOpeningDate()) && !currentTime.after(currentProjects.get(i).getApplicationClosingDate());
    		}
    	}    	
    	return false; // project not found
    }
    
	/**
	 * Checks if the project is past the application closing date.
	 * <p>
	 * This method iterates through the list of projects and checks if the application opening and closing dates are both before the current date.
	 * If so, it sets the project's visibility to false and updates the project file.
	 * </p>
	 * 
	 * @param currentProjects the list of projects to check (must not be null)
	 * 
	 * @see Project#setVisibility(boolean)
	 */
    @Override
    public void PastDateChecker(ArrayList<Project> currentProjects)
    {
    	for(int i=0; i<currentProjects.size(); i++)
    	{
    		// check past closing date in project lists
    		if(currentProjects.get(i).getApplicationOpeningDate().before(currentDate) && 
    		   currentProjects.get(i).getApplicationClosingDate().before(currentDate))
    		{
    			// auto update visibility to false
    			// update project.java side
				currentProjects.get(i).setVisibility(false);    
				
				// update the .txt file 
				General.editProjectFile(currentProjects.get(i));
    		}
    	}
    }
    
    /**
	 * Checks if a specific project is past its application closing date.
	 * <p>
	 * This method iterates through the list of projects and checks if the application opening and closing dates are both before the current date.
	 * If so, it returns true, indicating that the project is past its application period.
	 * * </p>
	 * 
	 * @param currentProjects the list of projects to check (must not be null)
	 * @param projectName the name of the project to check (must not be null or empty)
	 * @return {@code true} if the project is past its application closing date, {@code false} otherwise
	 */
    // Runtime call to check past projects and auto toggle visibility false for that project
    @Override
    public boolean PastDateCheckerProject(ArrayList<Project> currentProjects, String projectName)
    {
    	for(int i=0; i<currentProjects.size(); i++)
    	{
    		if(currentProjects.get(i).getProjectName().equals(projectName))
    		{
    			// check past closing date in project lists
    			if(currentProjects.get(i).getApplicationOpeningDate().before(currentDate) && 
    			   currentProjects.get(i).getApplicationClosingDate().before(currentDate))
    			{    			
    				return true;
    			}    			
    		}
    	}
    	return false;
    }
    /**
	 * Updates the marital status for flat types in the current projects.
	 * <p>
	 * This method iterates through all projects and their flat types, checking the flat type name and units.
	 * If the flat type is "2-Room" and has units available, it allows both SINGLE and MARRIED marital statuses.
	 * If the flat type is "3-Room" and has units available, it allows only MARRIED marital status.
	 * </p>
	 * 
	 * @param currentProjects the list of projects to update (must not be null)
	 * @see FlatType#setAllowedGroups(ArrayList<MaritalStatus>)
	 * @see MaritalStatus
	 * @see FlatType
	 * @see Project
	 * 
	 */
    // Runtime call to update marital status for current flat types
    @Override
    public void updateMaritalStatus(ArrayList<Project> currentProjects) {
    	// To update if needed
        String twoRoom = "2-Room";
        String threeRoom = "3-Room";

        // Iterate through all projects
        for (Project project : currentProjects) {
            ArrayList<FlatType> flatDetails = project.getFlatTypes();

            // Iterate through all flat types in that specific project
            for (FlatType flatType : flatDetails) {
                ArrayList<MaritalStatus> allowedGroups = new ArrayList<>();

                // check the flat type name and units (in case, 0 unit)
                if (flatType.getFlatTypeName().equals(twoRoom)) {
                    if (flatType.getUnits() > 0) {
                        // 2-Room allows both SINGLE and MARRIED
                        allowedGroups.add(MaritalStatus.SINGLE);
                        allowedGroups.add(MaritalStatus.MARRIED);
                    }
                } else if (flatType.getFlatTypeName().equals(threeRoom)) {
                    if (flatType.getUnits() > 0) {
                        // 3-Room allows only MARRIED
                        allowedGroups.add(MaritalStatus.MARRIED);
                    }
                }
                // Update the allowed groups for that specific flat type
                flatType.setAllowedGroups(allowedGroups);
            }
        }
    }
}

