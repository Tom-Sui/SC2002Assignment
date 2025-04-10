import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class HDBManager extends User{
    private String DataFilePath = "./src/Data";
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
    public boolean editProject(String projectName, String updateContent, String target,ArrayList<HDBManager> hdbManager,ArrayList<HDBOfficer> hdbOfficer){
        General general = new General();
        Scanner scanner = new Scanner(System.in);
        System.out.println("here");
        if (managedProjects == null) {
            System.out.println("No managed projects");
            scanner.close();
            return false;
        }
        for(Project managedProject: managedProjects){
            if(managedProject.getProjectName().equals(projectName)){
                switch (target.replace(" ","").toLowerCase()) {
                    //note that if changing managed project name
                    //pass the new manager's name
                    case "projectname","1":

                        general.editFile(DataFilePath + "/ProjectList.txt", updateContent,projectName,projectName);
                        break;
                    case "neiborhood","2":
                        if(general.findProject(this.managedProjects, projectName) != null){
                            general.editFile(DataFilePath + "/ProjectList.txt", 
                                            updateContent,
                                            general.findProject(this.managedProjects, projectName).getNeiborhood(),
                                            projectName);
                            System.out.println("Neiborhood changed to: " + updateContent);
                            break;
                        }
                        System.out.println("Not within managed projects");
                        break;
                    case "flatetype":


                        //YET
                        //Note that there are more than one flate type
                        //flate type is under a seperate class
                        //remeber to edit each of the content


                        break;
                    case "openingdate":
                        if(general.findProject(this.managedProjects, projectName) != null){
                            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            general.editFile(DataFilePath + "/ProjectList.txt", 
                                            updateContent,
                                            general.findProject(this.managedProjects, projectName).getApplicationOpeningDate().toString(),
                                            projectName);
                            System.out.println("Opending date changed to: " + updateContent);
                            break;
                        }
                        System.out.println("Not within managed projects");
                        break;
                    case "closingdate":
                        if(general.findProject(this.managedProjects, projectName) != null){
                            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            general.editFile(DataFilePath + "/ProjectList.txt", 
                                            updateContent,
                                            general.findProject(this.managedProjects, projectName).getApplicationClosingDate().toString(),
                                            projectName);
                            System.out.println("Closing date changed to: " + updateContent);
                            break;
                        }
                    System.out.println("Not within managed projects");
                        break;
                    case "manager":
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
                    case "officer":



                        //YET: same as manager
                        //Note that there are more than one officer

        
                        break;
                    default:
                        System.out.println("!!!Error input!!!");
                        break;
                }
                scanner.close();
                return true;
            }else{
                System.out.println("Not within managed projects");
                scanner.close();
                return false;
            }
        }
        System.out.println("There is no managed projects");
        scanner.close();
        return false;
    }
    
    // public void deletProject(Project targetProject){
    //     Project[] tempProject = new Project[this.managedProjects.size() - 1];
    //     int count = 0;
    //     for(Project project : this.managedProjects){
    //         if(project.getProjectName().equals(targetProject.getProjectName())){
    //             continue;
    //         }else{
    //             tempProject[count] = project;
    //             count++;
    //         }
    //     }
    //     this.managedProjects = tempProject;
    // }
    // public Project deletProject(String targetProject){
    //     // ArrayList<Project> tempProject = new Project[this.managedProjects.size() - 1];
    //     Project deletedProject = new Project();
    //     int count = 0;
    //     for(Project project : this.managedProjects){
    //         if(project.getProjectName().equals(targetProject)){
    //             deletedProject = project;
    //             continue;
    //         }else{
    //             tempProject[count] = project;
    //             count++;
    //         }
    //     }
    //     this.managedProjects = tempProject;
    //     return deletedProject;
    // }
    public Project deletProject(Project targetProject){
        // ArrayList<Project> tempProject = new Project[this.managedProjects.size() - 1];
        if(this.managedProjects == null){
            System.out.println("No project managed");
            return targetProject;
        }

        this.managedProjects.remove(targetProject);
        System.out.println("Project " + targetProject.getProjectName() + " removed");
        return targetProject;
    }
 
    
    // General Function: list out all the required projects (Place inside loop, with index specified)
    public void listRequiredProjects(ArrayList<Project> currentProjects, int i) {
    	
		//** TO-DO: NEED CHECK FOR TOGGLE VISIBILITY
		System.out.printf("Project Name: %s | Neighborhood: %s | Visibility: %s \n", currentProjects.get(i).getProjectName(), currentProjects.get(i).getNeiborhood(), currentProjects.get(i).getVisibility());  
		
		// CALL flat types
		System.out.println("   - Flat Types: \n");
		
		for (int x=0; x < currentProjects.get(i).getFlatTypes().size(); x++)
		{
			// STILL FIXING
			//System.out.printf("      - 2 Room | Units: %d | Price: $%.2f\n", currentProjects.get(i).getFlatTypes().get(x).getUnits(), currentProjects.get(i).getFlatTypes().get(x).getPrice());
			//System.out.printf("      - 3 Room | Units: %d | Price: $%.2f\n", currentProjects.get(i).getFlatTypes().get(x).getUnits(), currentProjects.get(i).getFlatTypes().get(x).getPrice());
			String flatTypes = FlatTypeLogic.displayFlatTypes(currentProjects.get(x).getFlatTypes());
			System.out.printf("   - %s", flatTypes);
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
    
    
    // list out all the existing projects
    public void listAllExistingProjects(ArrayList<Project> currentProjects) {
    	
    	System.out.println("=== List of All Existing Projects ===\n");
    	for (int i=0; i<currentProjects.size(); i++)
    	{
    		this.listRequiredProjects(currentProjects, i);
    	}
    	
    	System.out.printf("Total Projects: %d\n", currentProjects.size());
    	System.out.println("=============================== \n");
    }
    
    
    // list out only manager-in-charge projects
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
    	
    	//We are only toggling the visibility of the project that the manager wants to toggle
    	for (int i=0; i<currentProjects.size(); i++) {
    		if(currentProjects.get(i).getProjectName().equals(projectName)) {
    			if(currentProjects.get(i).getVisibility()) {
    				currentProjects.get(i).setVisibility(false);
    				System.out.printf("Visibility of project %s has been toggled off", currentProjects.get(i).getProjectName());
    			} else {
    				currentProjects.get(i).setVisibility(true);
    				System.out.printf("Visibility of project %s has been toggled on", currentProjects.get(i).getProjectName());
    			}
    			
    			
    		}
    	}
    	
    	//PROBLEM: Are we toggling every project or a specific project?
    	
    	
    }
    public void approveOrRejectOfficerRegistration(HDBOfficer officer, Project project){
    	//Ask others where they storing
    	//Get officerName and projectName (as a String) from officer/manager?/read from a possible created file of officer registration?
    	//Should this be in the manager UI?
    	/*
    	 * Check if they(current logged in manager) are managing that project
    	 * 	If no, print out saying manager not managing the project
    	 * 	If approving a certain officer, go through applicant list to check if they apply as an applicant
    	 * 		If yes, then reject
    	 * 		If no, check if they are handling another project within the date
    	 * 			If yes, reject
    	 * 			If no, check the slot of the project ( Since we using & to split when writing in, I was thinking
    	 * 			can use that to count and +1 will be the number of officers
    	 * 				If no slot, reject
    	 * 				If yes slot, approve and add in officer to project(using edit file(edit project)/ update number of available slots
    	 */

    }
   // public void rejectOfficerRegistration(HDBOfficer officer, Project project){

    //}
    public void approveOrRejectApplication(Applicant applicant, Project project){
    	
    	/*
    	 * Get applicant name and projectName (as a String) from applicant/manager??
    	 * 
    	 * Check if they have applied for other projects
    	 * 		If yes, reject
    	 * 		If no, Check applicant age, marital status and room type they apply for
    	 * 			If no, reject
    	 * 			If yes, check slot of the room type for that project,
    	 * 				If no slot, reject
    	 * 				If yes slot, approve and update application status to successful
    	 */

    }
    //public void rejectApplication(Applicant applicant, Project project){

   // }
    public void approveOrRejectWithdrawal(Applicant applicant, Project project){
    	
    	//Usually successful

    }
    //public void rejectWithdrawal(Applicant applicant, Project project){

    //}
    public String generateApplicantReport(ReportFilter filters){
    	
    	/*
    	 * Get the category they want to filter
    	 * Do I print out everything in the filter order or just print out the filter?
    	 * Eg. If they say they want married people, do I also print out single people?
    	 * 
    	 * Get category and do switch case maybe
    	 */
        return "report";
    }
    
    public void viewAllEnquiries(Enquiry enquiry) {
    	
    	//Print each enquiry
    	
    }
    public void replyToEnquiry(Enquiry enquiry, String reply){
    	
    	/*
    	 * Get specific enquiry and then in this function ask for the reply, then return the reply and
    	 * then in the manager UI, save the string to a variable and update it to the enquiry.
    	 * 
    	 * Need check if manager is managing the project
    	 */

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
