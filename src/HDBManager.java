import java.util.ArrayList;
import java.util.Scanner;

/**
 * Provides functions for HDB Managers to perform operations.
 * 
 */
public class HDBManager extends User{

	// Depends on abstraction classes
	private final I_ProjectManager projectManager;
	private final I_ListOutProjects listOutProjects;
	private final I_InputValidator inputValidator;
	private final I_ManagerOfficerRegistration managerOfficerRegistration;
	private final I_ManagerApplicantRegistration managerApplicantRegistration;
	private final I_Enquiry enquiry;

    public HDBManager(I_ProjectManager projectManager, I_ListOutProjects listOutProjects, 
    				  I_InputValidator inputValidator, I_ManagerOfficerRegistration managerOfficerRegistration,
    				  I_ManagerApplicantRegistration managerApplicantRegistration, I_Enquiry enquiry)
    {
    	this.projectManager = projectManager;
    	this.listOutProjects = listOutProjects;
    	this.inputValidator = inputValidator;
    	this.managerOfficerRegistration = managerOfficerRegistration;
    	this.managerApplicantRegistration = managerApplicantRegistration;
    	this.enquiry = enquiry;
    }
    
    
    
    // Abstract methods that must be implemented by subclasses

    /**
     * Creates a new enquiry for a specific project.
     * 
     * @param project The project the enquiry relates to
     * @param message The enquiry message content
     * @return The created Enquiry object
     */
    public Enquiry createEnquiry(Project project, String message) {
    	Enquiry[] enquiry = new Enquiry[10];
        return enquiry[0];
    };

    /**
     * Retrieves all enquiries associated with this user.
     * @return An array of Enquiry objects
     */
    public Enquiry[] viewEnquiries() {
    	Enquiry[] enquiry = new Enquiry[10];
        return enquiry;
    };
    
    /**
     * Modifies an existing enquiry.
     * 
     * @param enquiry The enquiry to modify
     * @param newMessage The new message content
     */
    public void editEnquiry(Enquiry enquiry, String newMessage) {};

    /**
     * Deletes an existing enquiry.
     * @param enquiry The enquiry to delete
     */
    public void deletEnquiry(Enquiry enquiry) {};
    
    
    
    // Project management
    public void setManagedProjects(Project project)
    {
    	projectManager.setManagedProjects(project);
    }
    
    public void PastDateChecker(ArrayList<Project> currentProjects)
    {
    	projectManager.PastDateChecker(currentProjects);
    }    
    
    public boolean PastDateCheckerProject(ArrayList<Project> currentProjects, String projectName)
    {
    	return projectManager.PastDateCheckerProject(currentProjects, projectName);
    }    
    
    public Project currentActiveProject(ArrayList<Project> currentProjects, String userName, boolean printCheck)
    {
    	return projectManager.currentActiveProject(currentProjects, userName, printCheck);
    }    
    
    public Project returnProject(ArrayList<Project> currentProjects, String projectName)
    {
    	return projectManager.returnProject(currentProjects, projectName);
    }
    
    public void listAllExistingProjects(ArrayList<Project> currentProjects)
    {
    	listOutProjects.listAllExistingProjects(currentProjects);
    }
    
    public void listSpecificProjects(ArrayList<Project> currentProjects, String username)
    {
    	listOutProjects.listSpecificProjects(currentProjects, username);
    }    
    
    public void toggleVisibility(ArrayList<Project> currentProjects, String projectName, String username)
    {
    	projectManager.toggleVisibility(currentProjects, projectName, username);
    }
    
    public void updateMaritalStatus(ArrayList<Project> currentProjects)
    {
    	projectManager.updateMaritalStatus(currentProjects);
    }
    
    public ArrayList<Project> createProject(String projectDetails,ArrayList<HDBManager> hdbManager, ArrayList<HDBOfficer> hdbOfficer)
    {
    	return projectManager.createProject(projectDetails, hdbManager, hdbOfficer);
    }
    
    public boolean editProject(String projectName, String updateContent, String target, ArrayList<Project> currentProjects, int i)
    {
    	return projectManager.editProject(projectName, updateContent, target, currentProjects, i);
    }
    
    public Project deletProject(Project targetProject)
    {
    	return projectManager.deletProject(targetProject);
    }
        
    // Input validation
    public String uniqueProjectName(ArrayList<Project> currentProjects, Scanner scanner, String prompt)
    {
    	return inputValidator.uniqueProjectName(currentProjects, scanner, prompt);
    }
    
    public String validateString(Scanner scanner, String prompt)
    {
    	return inputValidator.validateString(scanner, prompt);
    }
    
    public String validateInteger(Scanner scanner, String prompt)
    {
    	return inputValidator.validateInteger(scanner, prompt);
    }
    
    public String verifyDate(Scanner scanner, String prompt)
    {
    	return inputValidator.verifyDate(scanner, prompt);
    }
        
    public boolean ValidDateChecker(String openingDate, String closingDate)
    {
    	return inputValidator.ValidDateChecker(openingDate, closingDate);
    }
    
    public boolean ValidDateCheckerforEditing(String openingDate, String closingDate, boolean forOpening)
    {
    	return inputValidator.ValidDateCheckerforEditing(openingDate, closingDate, forOpening);
    }
    
    public String verifyOfficerSlots(Scanner scanner, String prompt)
    {
    	return inputValidator.verifyOfficerSlots(scanner, prompt);
    }
        
    
    // Manager Officer Registration
    public void viewOfficerRegistrationList()
    {
    	managerOfficerRegistration.viewOfficerRegistrationList();
    }
    
    public void approveOrRejectOfficerRegistration(ArrayList<Project> currentProjects, ArrayList<Applicant> applicants, ArrayList<HDBOfficer> HDBOfficers, String userName)
    {
    	managerOfficerRegistration.approveOrRejectOfficerRegistration(currentProjects, applicants, HDBOfficers, userName);
    }
    
    
    // Manager Applicant Registration
    public void approveOrRejectApplication(ArrayList<Project> currentProjects, ArrayList<Applicant> applicants, String username)
    {
    	managerApplicantRegistration.approveOrRejectApplication(currentProjects, applicants, username);
    }
    
    public void approveWithdrawal(ArrayList<Project> currentProjects, ArrayList<Applicant> applicants)
    {
    	managerApplicantRegistration.approveWithdrawal(currentProjects, applicants);
    }
    
    public void generateApplicantReport(ArrayList<Applicant> applicants, String maritalStatusFilter, Integer minAge, Integer maxAge)
    {
    	managerApplicantRegistration.generateApplicantReport(applicants, maritalStatusFilter, minAge, maxAge);
    }
    
    public void viewEnquiries(ArrayList<Project> currentProjects) {
    	
    	for(Project project: currentProjects) {
    		System.out.printf("Enquiries for Project %s\n", project.getProjectName());
    		ArrayList<Enquiry> enquiries = project.getEnquiries();
    		for(Enquiry enquiry: enquiries) {

    			System.out.printf("Applicant ID: %s\n", enquiry.getUserNric());
    			System.out.printf("Message: %s\n", enquiry.getMessage());
    			if(enquiry.getReplyID() == 0) {
    			System.out.printf("There is no reply yet");
    			} else {
    				System.out.printf("Reply: %s\n", enquiry.getReplyID());
    			}
    		}
    	}
    }
    
    public void replyToEnquiry(ArrayList<Project> currentProjects, String NRIC, Scanner scanner)
    {
    	enquiry.replyToEnquiry(currentProjects, NRIC, scanner);
    }    
}
