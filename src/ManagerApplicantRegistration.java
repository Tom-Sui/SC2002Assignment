import java.util.ArrayList;
import java.util.Date;
/**
 * This class implements the I_ManagerApplicantRegistration interface and provides methods for managing applicant registrations.
 * <p>
 * It includes functionality to approve or reject applications, process withdrawal requests, and generate reports of applicants based on various criteria.
 * </p>
 * 
 * @see I_ManagerApplicantRegistration
 * @see Project
 * @see Applicant
 */
public class ManagerApplicantRegistration implements I_ManagerApplicantRegistration{
    /**
	 * To approve or reject application to certain project
	 * @param currentProjects List of all current projects (must not be null, used to verify project references)
	 * @param applicants List of all applicants to process (must not be null)
	 * @param username NRIC of the HDB Manager or Project Officer managing the project
	 */
	
	String DataFilePath = "./Data";   // TO ADD ./src FOR ECLIPSE	
	String filePath = DataFilePath + "/ApplicationList.txt";
	
    public void approveOrRejectApplication(ArrayList<Project> currentProjects, ArrayList<Applicant> applicants, String username){
    	
    	Date currentTime = new Date();
    			
    	for (Project project : currentProjects) {
            // Check if the manager is managing the project
    		 if (project.getHDBManager().getNRIC().equals(username)) {

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
                                General.editOtherFile(filePath, "/ApplicationList.txt", "UNSUCCESSFUL" ,"PENDING", application.getApplicant().getNRIC());
                                System.out.printf("ApplicationID %d for project %s was UNSUCCESSFUL", application.getApplicationId(),application.getProject().getProjectName());
                            } else if (unitCount > 0){
                                // Approves x number of applications with x being the number of available units
                                application.setApplicationStatus(ApplicationStatus.SUCCESSFUL);
                                General.editOtherFile(filePath, "/ApplicationList.txt", "SUCCESSFUL" ,"PENDING", application.getApplicant().getNRIC());
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
    			General.editOtherFile(filePath, "/ApplicationList.txt","WITHDRAWN", "PENDINGWITHDRAWAL", application.getApplicant().getNRIC());
    			//Remove from project's application list
    			project.getApplications().remove(application);
    			
    			
    			//Clear from applicant
    			applicant.setCurrentApplication(null);
    	
    			ArrayList<FlatType> flatTypes = project.getFlatTypes(); // changes to .getFlatTypes from .getFlatTypesList
    			
    			
    			//Check if a flat was booked
    			if(application.getIsBooked()) {
    				FlatTypeLogic.updateIncreaseFilteredFlatTypeUnits(flatTypes, flatType);
    				General.editProjectFile(project, project.getProjectName());
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
}
