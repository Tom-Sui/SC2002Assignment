import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

// Handles Project-related functions to txt
public class ProjectManager extends BaseProjectManager{
	
	private String DataFilePath = "./src/Data";   // TO ADD /src/ FOR ECLIPSE
	
	// Constructor
	public ProjectManager(General general, ArrayList<Project> managedProjects)
	{
		super(general, managedProjects);
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
	@Override
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
	@Override
    public boolean editProject(String projectName, String updateContent, String target, ArrayList<Project> currentProjects, int i){
		
		ArrayList<FlatType> flatDetail = currentProjects.get(i).getFlatTypes();	
		
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
        		General.editProjectFile(currentProjects.get(i),updateContent);

				// set project name in project.java
				currentProjects.get(i).setProjectName(updateContent);
				
        		System.out.println("Project Name changed to: " + updateContent);
        		break;
        		
        		// Update Neighborhood
        	case "1":
       		
        		// set neighborhood in project.java
        		currentProjects.get(i).setNeiborhood(updateContent);
        		// update to .txt
        		General.editProjectFile(currentProjects.get(i));
        		
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
        			if(flatD.getFlatTypeName().equals(flatPricing[0]))
        			{
        				if (flatD.getUnits() <= 0)
        				{        					
        					// set pricing in project.java        				
            				flatD.setPrice(0.0);
            				System.out.printf("This %s Type has 0 unit. Default pricing will be $0.\n", flatPricing[0]);
        				}
        				else if(Double.parseDouble(flatPricing[1]) <= 0.0 && flatD.getUnits() > 0)
        				{
        					System.out.println("Invalid price. Please try again! \n");
        				}
        				else
        				{
        					// set pricing in project.java        				
        					flatD.setPrice(Double.parseDouble(flatPricing[1]));        					
        					System.out.printf("Pricing of %s is updated to $%s.\n", flatPricing[0], flatD.getPrice());
        				}        				
        				// update to .txt
        				General.editProjectFile(currentProjects.get(i));        				
        			}
        		}                       		        		
        		break;
        		
        		// Update Number of Units
        	case "3":        		
        		
        		String[] flatStr = updateContent.split(",");
        		// 0 = flat Types
        		// 1 = number of updated units
        		        		
        		// function to identify which flat to update
        		for(FlatType flatD : flatDetail)
        		{        			
        			if(flatD.getFlatTypeName().equals(flatStr[0]))
        			{
        				if(Integer.parseInt(flatStr[1]) == 0)
        				{
        					// validation for 0 input
        					flatD.setUnits(Integer.parseInt(flatStr[1]));
        					flatD.setPrice(0.0);
        					System.out.printf("0 unit for %s is updated. Default pricing will be $0.\n", flatStr[0]);
        				}
        				else
        				{
        					// set pricing in project.java        				
        					flatD.setUnits(Integer.parseInt(flatStr[1]));
        					System.out.printf("%s is updated to %s units.\n", flatStr[0], flatD.getUnits());        					
        				}        				
        				// update to .txt
        				General.editProjectFile(currentProjects.get(i));        				
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
        			General.editProjectFile(currentProjects.get(i));
    				
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
        			General.editProjectFile(currentProjects.get(i));
        			
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
        		General.editProjectFile(currentProjects.get(i));
				
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
	@Override
    public Project deletProject(Project targetProject){
        // ArrayList<Project> tempProject = new Project[this.managedProjects.size() - 1];
        File projectFile = new File(DataFilePath + "/ProjectList.txt");
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
            FileWriter writer = new FileWriter(DataFilePath + "/ProjectList.txt");
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
    
}
