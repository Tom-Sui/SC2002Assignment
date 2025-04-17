import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
import java.util.Map;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
/**
 * Provides functions for HDB Managers to perfrom operations.
 * 
 */
public class HDBManager extends User{
    private String DataFilePath = "./src/Data";   // TO ADD /src/ FOR ECLIPSE
    private ArrayList<Project> managedProjects = new ArrayList<Project>();
    General general = new General();
    DateFormat formatter = new SimpleDateFormat("d/M/yyyy");
    Date currentDate = new Date();

	/**
     * Update managed projects that are managed initialized manager
     * 
     * @param project the specific project that meant to be managed by particular manager
     */
    //set managed projects
    public void setManagedProjects(Project project){
        this.managedProjects.add(project);
    }

	/**
     * Update managed projects that are managed initialized manager
     * 
     * @return array list of projects that are managed by current manager/null if no managed projects
     */
    public ArrayList<Project> getProject(){
        if (this.managedProjects == null) {
            System.out.println("Current manager has no managed projects");
            return null;
        }
        return this.managedProjects;
    }

	/**
     * Create new enquiry over specific project
	 * 
	 * @param project Project object that assigned to new enquiry
	 * @param message String to store the enquiry details
     * 
     * @return list of enquiries created
     */
    //Abstract function
    public Enquiry createEnquiry(Project project, String message){
        Enquiry[] enquiry = new Enquiry[10];
        return enquiry[0];
    }

	/**
     * To return all the enquiries
	 * 
     * @return list of enquiries
     */
    public Enquiry[] viewEnquiries(){
        Enquiry[] enquiry = new Enquiry[10];
        return enquiry;
    }
	/**
     * To return all the enquiries
	 * 
	 * @param enquiry the enquiry to be editied
	 * @param newMessage the updated enquiry content
	 * 
     */
    public void editEnquiry(Enquiry enquiry, String newMessage){

    }	
	/**
	* Remove specific enquiry
	* 
	* @param enquiry the enquiry to be deleted
	* 
	*/
    public void deletEnquiry(Enquiry enquiry){

    }
	/**
	* To identify if specific projects can be applied
	* 
	* @param project the project that to be evaluated if can be applied
	* @return true of can be applied, false otherwise
	*/
    public boolean canApply(Project project){
        return false;
    }

	/**
	* Creates a new project and subsequently updates the txt file that stores all project details
	* 
	* @param projectDetails String that includes all the project details
	* @param hdbManager Array list of managers, used to assign manager with the project
	* @param hdbOfficer Array list of officiers, used to assign officers with the project
	* @return array list of all current existing projects
	*
	*/
    public ArrayList<Project> createProject(String projectDetails,ArrayList<HDBManager> hdbManager, ArrayList<HDBOfficer> hdbOfficer){
        String filecontent = "";

        File officerFile = new File(DataFilePath + "/ProjectList.txt");
        Init init = new Init();

        try{
            Scanner scanner = new Scanner(officerFile);

            while(scanner.hasNextLine()){
                filecontent = filecontent + scanner.nextLine() + "\n";
            }
            filecontent = filecontent + projectDetails;

            String[] buffer = projectDetails.split(",");

            Project project = init.setProject(buffer, hdbManager, hdbOfficer);

            if(project == null){
                scanner.close();
                return init.LoadProjectInfo(hdbManager,hdbOfficer);
            }

            this.managedProjects.add(project);

            FileWriter writer = new FileWriter(DataFilePath + "/ProjectList.txt");
            writer.write(filecontent);
            writer.close();
            scanner.close();
            return init.LoadProjectInfo(hdbManager,hdbOfficer);
        }catch (FileNotFoundException e){
            System.out.println("Error occured while reading ProjectList.txt");
            e.printStackTrace();
            return init.LoadProjectInfo(hdbManager,hdbOfficer);
        }catch(IOException e){
            System.out.println("Error occured while writing into ProjectList.txt");
            e.printStackTrace();
            return init.LoadProjectInfo(hdbManager,hdbOfficer);
        }
    }

	/**
	 * Edits various attributes of a housing project and updates both the data file and in-memory objects.
	 * 
	 * <p>This method handles different types of project modifications based on the target parameter,
	 * including project name, neighborhood, flat pricing, unit counts, and application dates.
	 * Changes are written to the project data file and reflected in the corresponding Project objects.</p>
	 *
	 * @param projectName The current name of the project to be edited
	 * @param updateContent The new value(s) for the attribute being updated (format depends on target)
	 * @param target The type of edit to perform (see details below)
	 * @param currentProjects List of all current projects
	 * @param i Index of the project to edit in currentProjects list
	 * 
	 * @return true if the edit was successful, false if there are no managed projects
	 * 
	 * @throws NumberFormatException if numeric conversion fails for pricing or unit updates
	 * @throws IndexOutOfBoundsException if the project index is invalid
	 * 
	 * <b>Target Options:</b>
	 * <ul>
	 *   <li>"0" - Update Project Name (updateContent = new project name)</li>
	 *   <li>"1" - Update Neighborhood (updateContent = new neighborhood name)</li>
	 *   <li>"2" - Update Flat Pricing (updateContent = "flatType,newPrice")</li>
	 *   <li>"3" - Update Unit Count (updateContent = "flatType,newUnitCount")</li>
	 *   <li>"4" - Change Application Opening Date (updateContent = new date as "d/M/yyyy")</li>
	 *   <li>"5" - Change Application Closing Date (updateContent = new date as "d/M/yyyy")</li>
	 * </ul>
	 * 
	 * <p><b>File Operations:</b>
	 * The method updates the project data in "ProjectList.txt" in the directory ./Data</p>
	 * 
	 * @see General#editFile(String, String, String, String)
	 * @see Project
	 * @see FlatType
	 */

    //If call this function
    //Do remember to run init.LoadProjectInfo() to restore the changed project info
    public boolean editProject(String projectName, 
							String updateContent,
							String target, 
							ArrayList<Project> currentProjects,
							int i){

        General general = new General();
        
        ArrayList<FlatType> flatDetail = currentProjects.get(i).getFlatTypes();	
        String flatTypeName;
        
        if (managedProjects == null) {
            System.out.println("\nNo managed projects");
            return false;
        }
        
        else
        {			
        	// checking is done in Manager Main
        	switch(target)
        	{
        	// Update Project Name
        	case "0":
        		// update to .txt
        		general.editProjectFile(currentProjects.get(i),updateContent);

				// set project name in project.java
				currentProjects.get(i).setProjectName(updateContent);
				
        		System.out.println("Project Name changed to: " + updateContent);
        		break;
        		
        		// Update Neighborhood
        	case "1":
       		
        		// set neighborhood in project.java
        		currentProjects.get(i).setNeiborhood(updateContent);
        		// update to .txt
        		general.editProjectFile(currentProjects.get(i));
        		
        		System.out.println("Neighbour changed to: " + updateContent);
        		break;
        		
        		// update pricing
        	case "2":
        		        		
        		String[] flatPricing = updateContent.split(",");
        		// 0 = flat Types
        		// 1 = pricing
        		
        		// function to identify which flat to update                
                for(FlatType flatD : flatDetail)
        		{
        			if(flatD instanceof TwoRoom)
        			{
        				flatTypeName = "2-Room";
        				
        			}
        			else if(flatD instanceof ThreeRoom)
        			{
        				flatTypeName = "3-Room";
        			}
        			else
        			{
        				flatTypeName = "Unknown Flat Type";
        			}
        			
        			if(flatTypeName.equals(flatPricing[0]))
        			{
        				// set pricing in project.java        				
        				flatD.setPrice(Double.parseDouble(flatPricing[1]));
        				
        				// update to .txt
        				general.editProjectFile(currentProjects.get(i));
        				
        				System.out.printf("Pricing of %s is updated to $%s.\n", flatPricing[0], flatD.getPrice());
        			}
        		}                       		        		
        		break;
        		
        		// Update Number of Units
        	case "3":        		        		
        		ArrayList<FlatType> flatDetails = currentProjects.get(i).getFlatTypes();		        		
        		String[] flatStr = updateContent.split(",");
        		// 0 = flat Types
        		// 1 = number of updated units
        		        		
        		// function to identify which flat to update
        		for(FlatType flatD : flatDetail)
        		{
        			if(flatD instanceof TwoRoom)
        			{
        				flatTypeName = "2-Room";
        				
        			}
        			else if(flatD instanceof ThreeRoom)
        			{
        				flatTypeName = "3-Room";
        			}
        			else
        			{
        				flatTypeName = "Unknown Flat Type";
        			}
        			
        			if(flatTypeName.equals(flatStr[0]))
        			{
        				// set pricing in project.java        				
        				flatD.setUnits(Integer.parseInt(flatStr[1]));
        				
        				// update to .txt
        				general.editProjectFile(currentProjects.get(i));
        				
        				System.out.printf("%s is updated to %s units.\n", flatStr[0], flatD.getUnits());
        			}
        		}       		
        		break;
        		
        		// Change Application Opening Date
        	case "4":
        		
        		// catch error for Date formatting 
        		try {
        			Date newDate = formatter.parse(updateContent);
        			currentProjects.get(i).setApplicationOpeningDate(newDate);
        			
        			// update to .txt 
    				general.editProjectFile(currentProjects.get(i));
    				
        			System.out.println("Opening date changed to: " + updateContent);        			
        			
        		}catch (ParseException e)
        		{
        			System.out.println("Incorrect date format: " + e.getMessage() + "\nDate NOT updated.");
        		}     		       		
        		break;
        		
        		// Change Application Closing Date
        	case "5":
				
        		// catch error for Date formatting 
        		try {
        			Date newDate = formatter.parse(updateContent);
        			currentProjects.get(i).setApplicationClosingDate(newDate);
        			
        			// update to .txt 
    				general.editProjectFile(currentProjects.get(i));
        			
        			System.out.println("Closing date changed to: " + updateContent);
        			
        		}catch (ParseException e)
        		{
        			System.out.println("Incorrect date format: " + e.getMessage() + "\nDate NOT updated.");
        		}
        		break;   
        		
        	case "7":
        		
        		int updatedSlots = Integer.parseInt(updateContent);
        		
        		// set officer slots in project.java
        		currentProjects.get(i).setAvailableOfficerSlots(updatedSlots);
        		
        		// update to .txt
        		general.editProjectFile(currentProjects.get(i));
				
        		System.out.println("HDB Officer Slots updated to: " + updateContent);
        		break;
        		
        	default:
        		System.out.println("!!!Error input!!!");
        		break;        		
        	}        	
        	return true;
        }
    }
	/**
	 * Deletes a project object storage and runtime.
	 * 
	 * This method performs two main operations:
	 * <ol>
	 *   <li>Removes the project from the ProjectList.txt data file under ./Data</li>
	 *   <li>Removes the project from the managedProjects list</li>
	 * </ol>
	 *
	 * @param targetProject The project to be deleted (must not be null)
	 * @return The deleted project if no projects are managed or if an error occurs, 
	 *         null if deletion was successful
	 * 
	 * @see Project
	 * @see FileWriter
	 * @see Scanner
	 */
    public Project deletProject(Project targetProject){
        // ArrayList<Project> tempProject = new Project[this.managedProjects.size() - 1];
        File projectFile = new File("./src/Data/ProjectList.txt");
        String fileContent = "";
        String buffer;
        try{
            Scanner scanner = new Scanner(projectFile);
            while(scanner.hasNextLine()){
                buffer = scanner.nextLine();
                String[] data = buffer.split(",");

                if(data[0].equals(targetProject.getProjectName())){
                    continue;
                }
                fileContent = fileContent + buffer + "\n";
            }
            FileWriter writer = new FileWriter("./src/Data/ProjectList.txt");
            writer.write(fileContent);
            writer.close();
            scanner.close();
        }catch (FileNotFoundException e){
            System.out.println("Error occured while reading ProjectLists.txt");
            e.printStackTrace();
            return null;
        }catch(IOException e){
            System.out.println("Error occured when writing ProjectLists.txt");
            e.printStackTrace();
        }

        if(this.managedProjects == null){
            System.out.println("No project managed");
            return targetProject;
        }

        this.managedProjects.remove(targetProject);
        System.out.println("Project " + targetProject.getProjectName() + " removed");
        return null;
    }
    
    // verify unique Project Name
    public String uniqueProjectName(ArrayList<Project> currentProjects, Scanner scanner, String prompt)
    {
    	String verifiedName = null;
    	do {
    		boolean isUnique = true;
    		System.out.print(prompt);
    		String inputName = scanner.nextLine();
    		
    		// Handle stronger check as unique identifier
    		String inputPrjName = inputName.toLowerCase().replaceAll("[\\s\\-_/]+", "");
    		    		
    		for (int i=0; i<currentProjects.size(); i++)
    		{
    			// Handle stronger check as unique identifier    			
    			String projectName = currentProjects.get(i).getProjectName().toLowerCase().replaceAll("[\\s\\-_/]+", "");
    			
    			if(projectName.equals(inputPrjName))
    			{
    				System.out.println("Project Name has been used. Please try again! \n");
    				isUnique = false;    				
    			}
    		}

    		if(isUnique)
    		{
    			verifiedName = inputName; // set verified name if unique
    		}
    		
    	}while(verifiedName == null);

    	return verifiedName;    	
    }
    
    // verify only integer numbers
    public String validateInteger(Scanner scanner, String prompt)
    {
    	String verifiedInt = null;
    	do {
    		System.out.print(prompt);
    		String input = scanner.nextLine();
    		
    		try
    		{
    			Integer.parseInt(input.trim());
    			verifiedInt = input;    			
    			
    		}catch (NumberFormatException e)
    		{
    			System.out.println("Invalid input. Not an integer.\n");    			
    		}    		
    		
    	}while(verifiedInt == null);
    	
    	return verifiedInt;
    }
    
    // verify correct date input
    public String verifyDate(Scanner scanner, String prompt)
    {
    	String verifiedDate = null;    	
    	do 
		{
			System.out.print(prompt);
			String inputDate = scanner.nextLine();
			
			try {								
				// able to parse to Date format
    			Date newDate = formatter.parse(inputDate);    			
    			verifiedDate = formatter.format(newDate);
    			
    		}catch (ParseException e)
    		{
    			System.out.println("Incorrect date format. Please use the format: dd/mm/yy \n");
    		}

		}while(verifiedDate == null);
    	
    	return verifiedDate;
    }
       
    // Runtime call to check past projects and auto toggle visibility false for that project
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
				general.editProjectFile(currentProjects.get(i));
    		}
    	}
    }
    
    // Runtime call to check past projects and auto toggle visibility false for that project
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
    
    // Verify valid opening and closing dates for creating new project
    public boolean ValidDateChecker(String openingDate, String closingDate)
    {
    	try {	    		
    		Date convertedOpeningDate = formatter.parse(openingDate);
    		Date convertedClosingDate = formatter.parse(closingDate);    			
	    		
    		// Invalid test cases between before & after
    		if (convertedOpeningDate.after(convertedClosingDate) || 
    			convertedOpeningDate.before(currentDate) && convertedClosingDate.before(currentDate) ||
    			convertedOpeningDate.after(currentDate) && !convertedClosingDate.after(convertedOpeningDate))
    		{
    			// incorrect sequence of dates, project invalid
    			return false;
    		}    		
			
    		// either project ongoing or future project
    		return true;  
    		
		}catch (ParseException e)
		{
			System.err.println("Error parsing the date: " + e.getMessage());
		}    	
    	return false;    	
    }
    
    // Verify valid opening and closing dates for editing existing project
    public boolean ValidDateCheckerforEditing(String openingDate, String closingDate, boolean forOpening)
    {
    	Date convertedOpeningDate;
    	Date convertedClosingDate;
    	
    	try {	    		
    		SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");	
    		
    		// convert string representation of date to 'd/M/yyy' (for editing)
    		if(forOpening)
    		{
    			convertedOpeningDate = formatter.parse(openingDate);
    			convertedClosingDate = inputFormat.parse(closingDate);
    		}
    		else // for closing date
    		{
    			// String to date
    			convertedOpeningDate = inputFormat.parse(openingDate);   
    			convertedClosingDate = formatter.parse(closingDate);
    		}    		    		
    		
    		// Invalid test cases between before & after
    		if (convertedOpeningDate.after(convertedClosingDate) || 
    			convertedOpeningDate.before(currentDate) && convertedClosingDate.before(currentDate) ||
    			convertedOpeningDate.after(currentDate) && !convertedClosingDate.after(convertedOpeningDate))
    		{
    			// incorrect sequence of dates, project invalid
    			return false;
    		}    		
			
    		// either project ongoing or future project
    		return true;  
    		
		}catch (ParseException e)
		{
			System.err.println("Error parsing the date: " + e.getMessage());
		}
    	
    	return false;    	
    }
    
    // Verify valid number of slots
    public String verifyOfficerSlots(Scanner scanner, String prompt)
    {
    	// Error checking if number within range
		String verifiedMaxSlot = null;
		do {
			String slotsInput = this.validateInteger(scanner, prompt);
			
			int slots = Integer.parseInt(slotsInput);
			
			// minimum 1 slot but maximum 10 slots
			if(slots > 10 || slots < 1)
			{
				System.out.println("Invalid slots. Please try again! \n");
			}
			else
			{
				verifiedMaxSlot = Integer.toString(slots);
			}
			
		}while(verifiedMaxSlot == null);
		
		return verifiedMaxSlot;
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
    public Project currentActiveProject(ArrayList<Project> currentProjects, String userName, boolean printCheck)
    {
    	Date currentTime = new Date();
    	
    	for (int i=0; i<currentProjects.size(); i++)
    	{
    		// check for current manager projects only
    		if (currentProjects.get(i).getHDBManager().getName().equals(userName))
    		{
    			// run through to return project within application period 
    			// if within application period and visibility is ON
    			if (!currentTime.before(currentProjects.get(i).getApplicationOpeningDate()) && !currentTime.after(currentProjects.get(i).getApplicationClosingDate()) && currentProjects.get(i).getVisibility() == true)
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
    public void listRequiredProjects(ArrayList<Project> currentProjects, int i) {
    	
		System.out.printf("Project Name: %s | Neighborhood: %s | Visibility: %s \n", currentProjects.get(i).getProjectName(), currentProjects.get(i).getNeiborhood(), currentProjects.get(i).getVisibility());  
		
		// CALL flat types
		System.out.println("   - Flat Details:");
		
		ArrayList<FlatType> flatDetails = currentProjects.get(i).getFlatTypes();
		
		for(FlatType flatD : flatDetails)
		{
			String flatTypeName;
			
			if(flatD instanceof TwoRoom)
			{
				flatTypeName = "2-Room";
			}
			else if(flatD instanceof ThreeRoom)
			{
				flatTypeName = "3-Room";
			}
			else
			{
				flatTypeName = "Unknown Flat Type";
			}
			
			System.out.printf("      - Flat Type: %s, Units: %d, Price: $%.2f\n", flatTypeName, flatD.getUnits(), flatD.getPrice());	
		}
		
		//for (FlatType flatD : flatDetails)
		//{
			//System.out.printf("      - Flat Type: %s, Units: %d, Price: $%.2f\n", flatD.getFlatTypeName(), flatD.getUnits(), flatD.getPrice());			
		//}			
		
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
    public void listSpecificProjects(ArrayList<Project> currentProjects, String username)
    {
    	int count=0;
    	System.out.println("=== List of All Your Existing Projects ===\n");
    	
    	for (int i=0; i<currentProjects.size(); i++)
    	{
    		// to check for current userName and print out only their projects
    		if (currentProjects.get(i).getHDBManager().getName().equals(username))
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
    public void toggleVisibility(ArrayList<Project> currentProjects, String projectName){
    	
    	//We are only toggling the visibility of the project that the manager wants to toggle
    	for (int i=0; i<currentProjects.size(); i++) {
    		if(currentProjects.get(i).getProjectName().equals(projectName)) {
    			
    			if(currentProjects.get(i).getVisibility()) {
    				   				
    				// update project.java side
    				currentProjects.get(i).setVisibility(false);    
    				
    				// update the .txt file 
    				general.editProjectFile(currentProjects.get(i));
    				
    				System.out.printf("Updated Project Visibility: '%s' -- OFF.\n", currentProjects.get(i).getProjectName());    				
    			} 
    			else 
    			{   			    				
    				// update project.java side
    				currentProjects.get(i).setVisibility(true);
    				
    				// update the .txt file 
    				general.editProjectFile(currentProjects.get(i));
    				
    				System.out.printf("Updated Project Visibility: '%s' -- ON.\n", currentProjects.get(i).getProjectName());
    			}    			    			
    		}
    	}    	
    }
    
	/**
	 * Displays the officer registration list.
	 * 
	 * The method expects the file to have the following format per line:
	 * <pre>
	 * officerName,officerNRIC,projectName,registrationStatus
	 * </pre>
	 * 
	 * Example output:
	 * <pre>
	 * 1. John Tan|S1234567A|Sunshine Residences|Approved
	 * 2. Sarah Lim|T9876543B|Riverfront Residences|Pending
	 * </pre>
	 * 
	 * The default file path is "./src/Data/OfficerRegistrationList.txt"
	 * (Note: Developers should ensure this path is correct for their environment)
	 */

    public void viewOfficerRegistrationList() {
    	
    	String filePath = "./src/Data/OfficerRegistrationList.txt"; // change this if your path is different

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            int i =1;
            
            while ((line = br.readLine()) != null) {
                // Split the line by comma
            	
                String[] parts = line.split(",");

                if (parts.length == 4) {
                    String officerName = parts[0].trim();
                    String officerNRIC = parts[1].trim();
                    String projectName = parts[2].trim();
                    String registrationStatus = parts[3].trim();

                    // Do something with registrationStatus
                    System.out.printf("%d. %s|%s|%s|%s\n", i,officerName,officerNRIC,projectName,registrationStatus); 
                } 
                i++;
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
    
    

	/**
	 * Processes pending officer registrations by approving or rejecting them based on various validation checks.
	 * <p>
	 * This method performs the following operations for each pending registration:
 	 * When function called, we will run through officerRegistration list, first for loop will be size of officer list
	 * Will check if status is pending
	 * 		If no, go next officer
	 * 		If yes, get officer NRIC and projectName
	 * 
	 * 
	 * Check if manager is managing project
	 * Check if request is pending
	 * Check if officer is in applicants /If yes need reject
	 * Check if officer is handling another project within date
	 * 		Check if officer is handling another project in projectlist
	 * 			If no, approve
	 * 			If yes, get project endDate check currentProject's startDate or get project startDate and currentProject's endDate
	 * 			Check if startDate of registeredProject is before endDate of alreadyRegisteredProject
	 * 			Check if startDate or endDate of RegisteringProject is in between startDate and endDate of alreadyRegisteredProject
	 * Check if there is slot for the project
	 * 
	 * @param currentProjects List of all current projects (must not be null)
	 * @param applicants List of all applicants (must not be null)
	 * @param HDBOfficers List of all HDB officers (must not be null)
	 * @param userName Name of the manager processing the requests (must not be null or empty)
	 * 
	 * @throws NullPointerException If any required parameter is null
	 * @throws IllegalArgumentException If userName is empty
	 * 
	 * The method enforces these business rules:
	 * <ol>
	 *   <li>Automatic rejection if officer is in applicant list</li>
	 *   <li>Date conflict check with other assigned projects</li>
	 *   <li>Slot availability verification</li>
	 *   <li>Manager authorization check</li>
	 * </ol>
	 * 
	 * FileOperations
	 * <ul>
	 *   <li>Reads from ./src/Data/OfficerRegistrationList.txt</li>
	 *   <li>Writes to DataFilePath + "/ProjectList.txt"</li>
	 *   <li>Updates registration status in OfficerRegistrationList.txt</li>
	 * </ul>
	 * 
	 * ExampleOutput
	 * <pre>
	 * Officer John Doe has been rejected from Project Sunshine as he/she is in applicants list
	 * Officer Jane Smith has been approved for Project Riverfront
	 * </pre>
	 */
    public void approveOrRejectOfficerRegistration(ArrayList<Project> currentProjects, ArrayList<Applicant> applicants, ArrayList<HDBOfficer> HDBOfficers, String userName){

    	OfficerRegistrationStatus status;
    	General general = new General();
    	Date currentTime = new Date();
    	
    	 String filePath = "./src/Data/OfficerRegistrationList.txt"; // change this if your path is different

         try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
             String line;
             
             //Run through each officer registration
             while ((line = br.readLine()) != null) {
                 String[] parts = line.split(",");
                 
                 boolean approved = false;
                 

                 if (parts.length == 4) {
                     String officerName = parts[0].trim(); 
                     String officerNRIC = parts[1].trim();
                     String projectName = parts[2].trim();
                     String registrationStatus = parts[3].trim();
                     status = OfficerRegistrationStatus.valueOf(registrationStatus.toUpperCase());
                     
                     //Find the index of project with projectName
                     Project index = general.findProject(currentProjects, projectName);
                     
                     
                     //Check if manager is managing the current project
 
                    	 //Check if registrationStatus is pending
                    	 if(index != null && index.getHDBManager().getName().equals(userName) && status == OfficerRegistrationStatus.PENDING) {
                    	
                    		 
                    		 //Check if officer NRIC is in application list for the project 
                    		 
                    		 boolean officerApplied = false;
                    		 
                    		 for (Application application : index.getApplications()) {
                    		        if (application.getApplicant().getNRIC().equalsIgnoreCase(officerNRIC)) {
                    		            officerApplied = true; // Applicant found
                    		        }
                    		    }
                    		    
                    		 
                    		 
                    		 //Officer is in application
                    		 if(officerApplied) {
                    			 rejectRegistration(filePath, officerNRIC);
                    			 System.out.printf("Officer %s has been rejected from Project %s as he/she has applied for the project as an applicant\n"
                    					 , officerName,projectName);
                    			 continue;
                    		 }
                    		 
                    		 //To indicate when to break loop to go to next officerRegistration if officer is rejected
                    		 boolean conflict = false;
                    		 
                    		 
                    		 
                    			 
                    		 //Loop through projects and get managers 
                    		 for(Project project : currentProjects) {
                    			 
                    				//Check that the current project is not the project they are registering for 
                    				 if(!project.getProjectName().equals(projectName)) {
                    					 //Get officers for the current project
                    					 ArrayList<HDBOfficer> officers = project.getHDBOfficer();
                    					 //Go through each officer in the project to check if the officer is handling the current project
                    					 for(HDBOfficer officer : officers) {
                    						 //If officer is in this project, check date
                    						 if(officer != null && officer.getName().equals(officerName)){
                    							 
                    							 //Checks if start date of registeringProject is in between start and end date of current project they are already handling 
                    							 boolean startDateConflict = ((index.getApplicationOpeningDate().after(project.getApplicationOpeningDate()) 
                    									 && index.getApplicationOpeningDate().before(project.getApplicationClosingDate())) || index.getApplicationOpeningDate().equals(project.getApplicationClosingDate()));
                    							//Checks if end date date of registeringProject is in between start and end date of current project they are already handling
                    							 boolean endDateConflict = ((index.getApplicationClosingDate().after(project.getApplicationOpeningDate())
                    									 && index.getApplicationClosingDate().before(project.getApplicationClosingDate())) || index.getApplicationClosingDate().equals(project.getApplicationOpeningDate()));
                    							 
                    							 boolean anotherDateConflict = project.getApplicationOpeningDate().after(index.getApplicationOpeningDate())
                    									 && project.getApplicationClosingDate().before(index.getApplicationClosingDate())
                    									 && !(currentTime.before(index.getApplicationClosingDate())
                    							 		 && currentTime.after(project.getApplicationClosingDate()));
                    							 //If start date or end date has a conflict, reject registration
                    							 if( startDateConflict || endDateConflict || anotherDateConflict) {
                    								 conflict = true; //Set conflict to true
                    								 rejectRegistration(filePath,officerNRIC);
                    								 System.out.printf("Officer %s has been rejected from Project %s as he/she is already handling another project within the time period\n", officerName,projectName);
                    								 break;
                    							 }
                    							 
                    					}
                    				 }
                    					 
                    				 if(conflict) break; //If conflict is set to true, it will go out of the projects loop
                    				 
                    					
                    	
                    				}	 
                    				 
                    			 }
                    		 
                    		if(conflict) continue;//If conflict is true, it will go to next officer in RegistrationList
                    		
                    		HDBOfficer currentOfficer = general.findOfficer(HDBOfficers, officerName);
                    		
                    		ArrayList<HDBOfficer> currentOfficers = index.getHDBOfficer();
                    		String officerNames = "";
                    		
                    		for (int i = 0; i < currentOfficers.size(); i++) {
                    		    HDBOfficer officer = currentOfficers.get(i);
                    		    if (officer != null) {
                    		        officerNames += officer.getName();
                    		        // Add "&" if it's not the last non-null officer
                    		        // Look ahead to see if there is another non-null officer
                    		        for (int j = i + 1; j < currentOfficers.size(); j++) {
                    		            if (currentOfficers.get(j) != null) {
                    		                officerNames += "&";
                    		                break;
                    		            }
                    		        }
                    		    }
                    		}
                    	
                    		
                    		
                    			 
                    		if(index.getAvailableOfficerSlots() == 0) {
                    				rejectRegistration(filePath, officerNRIC);
                    				System.out.printf("Officer %s has been rejected from Project %s as there are no more available slots\n", officerName,projectName);
                    		} else {
                    			    approveRegistration(filePath, officerNRIC,currentOfficer);
                    				int slots = index.getAvailableOfficerSlots();
                    				if(index.getHDBOfficer().get(0) == null) {
                    					ArrayList<HDBOfficer> newOfficers = new ArrayList<>();
                    					newOfficers.add(currentOfficer);
                    					index.setHDBOfficer(newOfficers);
                    				} else {
                    					index.addHDBOfficer(currentOfficer);
                    				}
                    				index.setAvailableOfficerSlots(slots-1);
                    				approved = true;
                    				currentOfficer.setManagingOfficer(true);
                    				general.editProjectFile(index);
                    				System.out.printf("Officer %s has been approved for Project %s\n", officerName,projectName);
                    			 }
                    			 
                    			 
                    		 } 
                    	 }
                     

                 
             }
         } catch (IOException e) {
             System.err.println("Error reading the file: " + e.getMessage());
         }

    }

	/**
	 * Rejects an officer's registration.
	 * <p>
	 * This helper method updates the officer's registration status from "PENDING" to "REJECTED"
	 * in the officer registration file. The update is performed by locating the record matching
	 * the specified officer NRIC.
	 * </p>
	 *
	 * @param filePath The path to the directory containing the registration file (must not be null)
	 * @param officerNRIC The NRIC of the officer to reject (must not be null or empty, used as unique identifier)
	 *
	 * @throws NullPointerException if either parameter is null
	 * @throws IllegalArgumentException if officerNRIC is empty
	 * @throws IOException if there is an error updating the registration file
	 *
	 * @see General#editOtherFile(String, String, String, String, String)
	 *
	 */

    private void rejectRegistration(String filePath, String officerNRIC) {
        general.editOtherFile(filePath,"/OfficerRegistrationList.txt", "REJECTED", "PENDING", officerNRIC);
    }
	/**
	 * Approve an officer's registration.
	 * <p>
	 * This helper method updates the officer's registration status from "PENDING" to "APPROVED"
	 * in the officer registration file. The update is performed by locating the record matching
	 * the specified officer NRIC.
	 * </p>
	 *
	 * @param filePath The path to the directory containing the registration file (must not be null)
	 * @param officerNRIC The NRIC of the officer to reject (must not be null or empty, used as unique identifier)
	 *
	 * @throws NullPointerException if either parameter is null
	 * @throws IllegalArgumentException if officerNRIC is empty
	 * @throws IOException if there is an error updating the registration file
	 *
	 * @see General#editOtherFile(String, String, String, String, String)
	 *
	 */
    // Approve the officer registration
    private void approveRegistration(String filePath, String officerNRIC, HDBOfficer officer ) {
        general.editOtherFile(filePath,"/OfficerRegistrationList.txt", "APPROVED", "PENDING", officerNRIC);
        //officer.setOfficerRegistrationStatus(OfficerRegistrationStatus.APPROVED);
    }
    
    /**
	 * To approve or reject application to certain project
	 * @param applicant Application to be approved or rejected
	 * @param project Target project
	 */
    public void approveOrRejectApplication(ArrayList<Project> currentProjects, ArrayList<Applicant> applicants, String username){
    	
    	/*
    	 *
    	 */
    	
    	Date currentTime = new Date();
    			
    	for (Project project : currentProjects) {
            // Check if the manager is managing the project
    		 if (project.getHDBManager().getName().equals(username)) {

    		        // Check if today's date is within the application opening and closing dates
    		        if (project.getApplicationOpeningDate().compareTo(currentTime) > 0 || project.getApplicationClosingDate().compareTo(currentTime) < 0) {
    		            continue; // Skip this project if it's not within the application period
    		        }

            // Process applications for each FlatType separately
            for (FlatType flatType : project.getFlatTypes()) {
                // Retrieve the current number of available units for the flat type
                int availableUnits = flatType.getUnits();
                int unitCount = availableUnits;
                

                // Iterate through the applications for the project
                for (Application application : project.getApplications()) {
                    // Check if the application's flat type matches the current flat type
                    if (application.getFlatType() == flatType) {
                        // Only process applications with PENDING status
                        if (application.getApplicationStatus() == ApplicationStatus.PENDING) {
                            if (availableUnits == 0) {
                                //No units available
                                application.setApplicationStatus(ApplicationStatus.UNSUCCESSFUL);
                                System.out.printf("ApplicationID %d for project %s was UNSUCCESSFUL", application.getApplicationId(),application.getProject().getProjectName());
                            } else if (unitCount > 0){
                                // Approves x number of applications with x being the number of available units
                                application.setApplicationStatus(ApplicationStatus.SUCCESSFUL);
                                System.out.printf("ApplicationID %d for project %s was SUCCESSFUL", application.getApplicationId(),application.getProject().getProjectName());
                                unitCount -=1;
                            }
                        }
                    
                }

          
            }
        }
    		 }
    	}
    }
	/**
	 * Processes all pending withdrawal requests from applicants.
	 * <p>
	 * This method performs the following operations for each applicant with a pending withdrawal:
	 * <ol>
	 *   <li>Changes application status from PENDINGWITHDRAWAL to WITHDRAWN</li>
	 *   <li>Removes the application from the project's application list</li>
	 *   <li>Clears the application reference from the applicant</li>
	 *   <li>If a flat was booked, increases available units for that flat type</li>
	 *   <li>Prints confirmation of each processed withdrawal</li>
	 * </ol>
	 * 
	 * @param currentProjects List of all current projects (must not be null, used to verify project references)
	 * @param applicants List of all applicants to process (must not be null)
	 * 
	 * @throws NullPointerException if either parameter is null
	 * 
	 * Eample output
	 * <pre>
	 * Withdrawn application for applicant John Tan (NRIC: S1234567A)
	 * Withdrawn application for applicant Sarah Lim (NRIC: T9876543B)
	 * </pre>
	 * 
	 * @see ApplicationStatus
	 * @see FlatTypeLogic#updateIncreaseFilteredFlatTypeUnits(ArrayList, FlatType)
	 */

    public void approveWithdrawal(ArrayList<Project> currentProjects, ArrayList<Applicant> applicants){
        // filter for applicant PENDINGWITHDRAWAL application 
        // set application status to WITHDRAWN
        // remove application from applicant
        // remove application from project
        // check if bookedflat 
        // minus from available units 
    	
    	//Run through each applicant
    	for(Applicant applicant : applicants) {
    		
    		//Get the application of the current applicant
    		Application application = applicant.getCurrentApplication();
    		
    		//Check if applicant has an application and if application is PENDINGWITHDRAWAL
    		if(application != null && application.getApplicationStatus() == ApplicationStatus.PENDINGWITHDRAWAL) {
    			Project project = application.getProject(); //Get the project of the application
    			FlatType flatType = application.getFlatType(); //Get flatType that was applied for
    			
    			
    			//Set application status to withdrawn
    			application.setApplicationStatus(ApplicationStatus.WITHDRAWN);
    			
    			//Remove from project's application list
    			project.getApplications().remove(application);
    			
    			
    			//Clear from applicant
    			applicant.setCurrentApplication(null);
    	
    			ArrayList<FlatType> flatTypes = project.getFlatTypes(); // changes to .getFlatTypes from .getFlatTypesList
    			
    			
    			//Check if a flat was booked
    			if(application.getIsBooked()) {
    				FlatTypeLogic.updateIncreaseFilteredFlatTypeUnits(flatTypes, flatType);
    			}
    			
    			System.out.printf("Withdrawn application for applicant %s (NRIC: %s)\\n", 
                        applicant.getName(), applicant.getNRIC());
    		}	
    	}
    }
    
	/**
	 * Generates and prints a filtered report of applicants with their application details.
	 * <p>
	 * The report includes applicants matching all specified filter criteria (marital status and age range),
	 * showing their name, applied project, flat type, age, and marital status.
	 * </p>
	 *
	 * @param applicants List of applicants to generate report from (must not be null)
	 * @param maritalStatusFilter Marital status to filter by (case-insensitive, null to skip this filter)
	 * @param minAge Minimum age for filtering (inclusive, null for no minimum)
	 * @param maxAge Maximum age for filtering (inclusive, null for no maximum)
	 *
	 * @throws NullPointerException if applicants parameter is null
	 *
	 * The report follows this format:
	 * <p>
	 * === Applicant report ===
	 * Name | Project | Flat Type | Age | Marital Status
	 * [Applicant 1 details]
	 * [Applicant 2 details]
	 * ...
	 * </p>
	 *
	 */
    public void generateApplicantReport(ArrayList<Applicant> applicants, String maritalStatusFilter, Integer minAge, Integer maxAge){
    	
    	System.out.println("=== Applicant report ===");
    	System.out.println("Name | Project | Flat Type | Age | Marital Status");
    	
    	for(Applicant applicant : applicants) {
    		
    		//Filter by marital status
    		if(maritalStatusFilter != null && !applicant.getMaritalStatus().toString().equalsIgnoreCase(maritalStatusFilter)) {
    			continue;
    		}
    		
    		//Filter by age
    		int age = applicant.getAge();
    		if((minAge != null && age<minAge) || (maxAge != null && age>maxAge)) {
    			continue;
    		}
    		
    		
    		Application application = applicant.getCurrentApplication();
    		if(application ==null) {
    			continue;}
    			
    		String projectName = application.getProject().getProjectName();
    		String flatTypeStr = application.getFlatType().toString(); // Assuming FlatType has a toString method
            int age2 = applicant.getAge();
            String maritalStatus = applicant.getMaritalStatus().toString();
                
            System.out.printf("%s | %s | %s | %d | %s\n", 
                        applicant.getName(), projectName, flatTypeStr, age2, maritalStatus);
    			
    		}
    	}
    /**
	 * Method used to add reply to the enquiry by the manager
	 * @param enquiry the enquiry to be replied
	 * @param reply the reply message to the enquiry
	 */
    public void replyToEnquiry(Enquiry enquiry, String reply){

    }

    // private Project findProject(String projectName){
    //     if(this.managedProjects != null){
    //         for(Project project: this.managedProjects){
    //             if(project.getProjectName().equals(projectName)){
    //                 return project;
    //             }
    //         }
    //     }
    //     return null;
    // }
    
    public void viewEnquiries(ArrayList<Project> currentProjects) {
    	
    	for(Project project: currentProjects) {
    		System.out.printf("Enquiries for Project %s\n", project.getProjectName());
    		ArrayList<Enquiry> enquiries = project.getEnquiries();
    		for(Enquiry enquiry: enquiries) {
    			
    			System.out.printf("Applicant ID: %d\n", enquiry.getApplicant());
    			System.out.printf("Message: %s\n", enquiry.getMessage());
    			if(enquiry.getReplies().equals("")) {
    			System.out.printf("There is no reply yet");
    			} else {
    				System.out.printf("Reply: %s\n", enquiry.getReplies());
    			}
    		}
    	}
    }

}
