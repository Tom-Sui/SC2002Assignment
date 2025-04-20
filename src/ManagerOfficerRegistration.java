import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ManagerOfficerRegistration extends BaseManagerOfficerRegistration{
	
	private String DataFilePath = "./src/Data";   // TO ADD ./src FOR ECLIPSE	
	String filePath = DataFilePath + "/OfficerRegistrationList.txt"; // change this if your path is different	
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

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            int i =1;
            
            while ((line = br.readLine()) != null) {
                // Split the line by comma
            	
                String[] parts = line.split(",");

                if (parts.length == 3) {
                    String officerNRIC = parts[0].trim();
                    String projectName = parts[1].trim();
                    String registrationStatus = parts[2].trim();

                    // Do something with registrationStatus
                    System.out.printf("%d. %s|%s|%s\n", i,officerNRIC,projectName,registrationStatus); 
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
    	boolean hasOutput = false;

         try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
             String line;
             
             //Run through each officer registration
             while ((line = br.readLine()) != null) {
                 String[] parts = line.split(",");
                 
                 boolean approved = false;                 

                 if (parts.length == 3) {
                     String officerNRIC = parts[0].trim();
                     String projectName = parts[1].trim();
                     String registrationStatus = parts[2].trim();
                     status = OfficerRegistrationStatus.valueOf(registrationStatus.toUpperCase());
                     
                     //Find the index of project with projectName
                     Project index = General.findProject(currentProjects, projectName);
                     //Find the current officer that is being handled
                     HDBOfficer currentOfficer = General.findOfficer(HDBOfficers, officerNRIC);
                     
                     String officerName = currentOfficer.getName();
                     	
                    	 //Check if registrationStatus is pending & if manager is managing the current project
                    	 if(index != null && index.getHDBManager().getNRIC().equals(userName) && status == OfficerRegistrationStatus.PENDING) {

                    		 boolean officerAlreadyInProject = false;
                    		 
                    		 //Check if officer is currently handling that project
                    		 ArrayList<HDBOfficer> officersInProject = index.getHDBOfficer();
                    		 
                    		 for(HDBOfficer officerInProject: officersInProject) {
                    			 
                    			 if (officerInProject.getNRIC().equals(officerNRIC)){
                    				 rejectRegistration(filePath, officerNRIC, currentOfficer , index);
                    				 System.out.printf("Officer %s has been rejected from Project %s as he/she is already an officer for this project\n"
                        					 , officerName,projectName);
                    				 officerAlreadyInProject = true;   
                    				 hasOutput = true;
                    				 break;
                    			 }
                    		 }
                    		 
                    		 if(officerAlreadyInProject) continue;
                    	
                    		 
                    		 //Check if officer NRIC is in application list for the project                     		 
                    		 boolean officerApplied = false;
                    		 
                    		 for (Application application : index.getApplications()) {
                    		        if (application.getApplicant().getNRIC().equalsIgnoreCase(officerNRIC)) {
                    		            officerApplied = true; // Applicant found
                    		        }
                    		    }                    		    

                    		 //Officer is in application
                    		 if(officerApplied) {
                    			 rejectRegistration(filePath, officerNRIC, currentOfficer , index);
                    			 System.out.printf("Officer %s has been rejected from Project %s as he/she has applied for the project as an applicant\n"
                    					 , officerName,projectName);
                    			 hasOutput = true;
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
                    						 if(officer != null && officer.getNRIC().equals(officerNRIC)){
                    							 
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
                    								 rejectRegistration(filePath,officerNRIC, currentOfficer, index);
                    								 System.out.printf("Officer %s has been rejected from Project %s as he/she is already handling another project within the time period\n", officerName,projectName);
                    								 
                    								 hasOutput = true;
                    								 break;
                    							 }                    							 
                    					}
                    				 }
                    					 
                    				 if(conflict) break; //If conflict is set to true, it will go out of the projects loop
                    				}	 
                    			 }
                    		 
                    		if(conflict) continue;//If conflict is true, it will go to next officer in RegistrationList

                    		if(index.getAvailableOfficerSlots() == 0) {
                    				rejectRegistration(filePath, officerNRIC,currentOfficer,index);
                    				System.out.printf("Officer %s has been rejected from Project %s as there are no more available slots\n", officerName,projectName);
                    				hasOutput = true;
                    				
                    		} else {
                    			    approveRegistration(filePath, officerNRIC,currentOfficer,index);
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
                    				General.editProjectFile(index);
                    				System.out.printf("Officer %s has been approved for Project %s\n", officerName,projectName);
                    				hasOutput = true;
                    			 }                    			                     			 
                    		 } 
                    	 }
             }
             
             if (!hasOutput) {
            	 System.out.println("You have no officers to approve/reject.\n");
             }
             
         } catch (IOException e) {
             System.err.println("Error reading the file: " + e.getMessage());
         }
    }
}
