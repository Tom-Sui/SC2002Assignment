import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class HDBManager extends User{
    private String DataFilePath = "./Data";   // TO ADD /src/ FOR ECLIPSE
    private ArrayList<Project> managedProjects = new ArrayList<Project>();

    //set managed projects
    public void setManagedProjects(Project project){
        this.managedProjects.add(project);
    }

    public ArrayList<Project> getProject(){
        if (this.managedProjects == null) {
            System.out.println("Current manager has no managed projects");
            return null;
        }
        return this.managedProjects;
    }

    //Abstract function
    public Enquiry createEnquiry(Project project, String message){
        Enquiry[] enquiry = new Enquiry[10];
        return enquiry[0];
    }
    public Enquiry[] viewEnquiries(){
        Enquiry[] enquiry = new Enquiry[10];
        return enquiry;
    }
    public void editEnquiry(Enquiry enquiry, String newMessage){

    }
    public void deletEnquiry(Enquiry enquiry){

    }
    public boolean canApply(Project project){
        return false;
    }


    //HDB manager functions
    //Yet: update the program to track managed project dates
    public ArrayList<Project> createProject(String projectDetails, ArrayList<Project> projects,ArrayList<HDBManager> hdbManager, ArrayList<HDBOfficer> hdbOfficer){
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

    //If call this function
    //Do remember to run init.LoadProjectInfo() to restore the changed project info
    public boolean editProject(String projectName, String updateContent
							, String target, ArrayList<Project> currentProjects
							, int i, ArrayList<HDBManager> hdbManager
							,ArrayList<HDBOfficer> hdbOfficer){

        General general = new General();

        if (managedProjects == null) {
            System.out.println("\nNo managed projects");
            return false;
        }
        
        else
        {
			DateFormat formatter = new SimpleDateFormat("d/M/yyyy");
        	// checking is done in Manager Main
        	switch(target)
        	{
        	// Update Project Name
        	case "1":
        		general.editFile(DataFilePath + "/ProjectList.txt", updateContent,projectName,projectName);
        		break;
        		
        		// Update Neighborhood
        	case "2":
        		general.editFile(DataFilePath + "/ProjectList.txt", updateContent, currentProjects.get(i).getNeiborhood(), projectName);
        		System.out.println("Neiborhood changed to: " + updateContent);
        			break;
        		
        		// Update Number of Units
        	case "3":
        		
        		//YET
        		//Note that there are more than one flate type
        		//flate type is under a seperate class               
        		
        		
        		break;
        		
        		// Change Application Opening Date
        	case "4":
        		general.editFile(DataFilePath + "/ProjectList.txt", 
								updateContent, 
								formatter.format(currentProjects.get(i).getApplicationOpeningDate()).toString(),
								projectName);
        		System.out.println("Opending date changed to: " + updateContent);
        		break;
        		
        		// Change Application Closing Date
        	case "5":
				// System.out.println(formatter.format(currentProjects.get(i).getApplicationClosingDate()));
        		general.editFile(DataFilePath + "/ProjectList.txt", 
								updateContent, 
								formatter.format(currentProjects.get(i).getApplicationClosingDate()).toString(), 
								projectName);
        		System.out.println("Closing date changed to: " + updateContent);
        		break;
        		
        		
        	/*
        		// Change Manager in-Charge
        	case "7":
        		
        		//DONE: add the project to another manager
        		//DONE: delet project from current manager
        		if(general.findManager(hdbManager, updateContent)!= null){
        			general.findManager(hdbManager, updateContent).setManagedProjects(this.deletProject(general.findProject(this.managedProjects,projectName)));
        		}else{
        			System.out.println("New manager does not exist");
        			break;
        		}
        		
        		//DONE: change project manager name
        		general.editFile(DataFilePath + "/ProjectList.txt", updateContent,this.getName(),projectName);
        		break;
        		
        	*/
        		
        		// Not needed? officer can register themselves
        	case "officer":
        		
        		//YET: same as manager
        		//Note that there are more than one officer
        		
        		break;
        		
        		
        	default:
        		System.out.println("!!!Error input!!!");
        		break;        		
        	}        	
        	return true;
        }
    }
    
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
        return targetProject;
    }
    
    
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
    
    // General Function: print out all the required projects (Place inside loop, with index specified)
    public void listRequiredProjects(ArrayList<Project> currentProjects, int i) {
    	
		//** TO-DO: NEED CHECK FOR TOGGLE VISIBILITY
		System.out.printf("Project Name: %s | Neighborhood: %s | Visibility: %s \n", currentProjects.get(i).getProjectName(), currentProjects.get(i).getNeiborhood(), currentProjects.get(i).getVisibility());  
		
		// CALL flat types
		System.out.println("   - Flat Types: \n");
		
		// STILL FIXING
		//ArrayList<FlatType> flatTypes = currentProjects.get(i).getFlatTypes();
		//StringBuilder flatDetails = new StringBuilder();
		//for (FlatType flatType : flatTypes)
		//{
			//flatDetails.append(String.format("Flat Type: %s, Units: %d, Price: $%.2f\n",
                    //flatType.getClass().getSimpleName(), flatType.getUnits(), flatType.getPrice()));
		//}
		//System.out.println(flatDetails);
		
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

    // return boolean visibility to check for Applicant & HDB Manager
    public void toggleVisibility(ArrayList<Project> currentProjects, String projectName){
    	General general = new General();
    	
    	//We are only toggling the visibility of the project that the manager wants to toggle
    	for (int i=0; i<currentProjects.size(); i++) {
    		if(currentProjects.get(i).getProjectName().equals(projectName)) {
    			
    			if(currentProjects.get(i).getVisibility()) {

    				// update the .txt file 
    				general.editFile(DataFilePath + "/ProjectList.txt", "false", String.valueOf(currentProjects.get(i).getVisibility()), projectName);
    				
    				// update project.java side
    				currentProjects.get(i).setVisibility(false);    				
    				System.out.printf("Updated Project Visibility: '%s' -- OFF.\n", currentProjects.get(i).getProjectName());
    				
    			} 
    			else 
    			{   				    				
    				// update the .txt file 
    				general.editFile(DataFilePath + "/ProjectList.txt", "true", String.valueOf(currentProjects.get(i).getVisibility()), projectName);
    				
    				// update project.java side
    				currentProjects.get(i).setVisibility(true);
    				System.out.printf("Updated Project Visibility: '%s' -- ON.\n", currentProjects.get(i).getProjectName());
    			}    			    			
    		}
    	}    	
    }
    

    public void approveOfficerRegistration(HDBOfficer officer, Project project){

    }
    public void rejectOfficerRegistration(HDBOfficer officer, Project project){

    }
    public void approveApplication(Applicant applicant, Project project){

    }
    public void rejectApplication(Applicant applicant, Project project){

    }
    public void approveWithdrawal(Applicant applicant, Project project){
        // filter for applicant PENDINGWITHDRAWAL application 
        // set application status to WITHDRAWN
        // remove application from applicant
        // remove application from project
        // check if bookedflat 
        // minus from available units 


    }
    public void rejectWithdrawal(Applicant applicant, Project project){

    }
    public String generateApplicantReport(ReportFilter filters){
        return "report";
    }
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

}
