import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class extends the User class and implements various interfaces
 * for project management, input validation, and registration processes.
 * <p>
 * The HDBManager class provides methods to create, edit, delete projects, manage officer and applicant
 * registrations, validate inputs, and handle enquiries related to housing projects.
 * </p>
 * 
 * @see User
 * @see I_ProjectManager
 * @see I_ListOutProjects
 * @see I_InputValidator
 * @see I_ManagerOfficerRegistration
 * @see I_ManagerApplicantRegistration
 */
public class HDBManager extends User{

	// Depends on abstraction classes
	private final I_ProjectManager projectManager;
	private final I_ListOutProjects listOutProjects;
	private final I_InputValidator inputValidator;
	private final I_ManagerOfficerRegistration managerOfficerRegistration;
	private final I_ManagerApplicantRegistration managerApplicantRegistration;
	private final I_Enquiry enquiry;
    /**
     * Default constructor for HDBManager.
     *
     * @param projectManager object for managing projects
     * @param listOutProjects object for listing out projects
     * @param inputValidator object for handling input validation
     * @param managerOfficerRegistration object for handling officer registrations
     * @param managerApplicantRegistration object for handling officer registrations
     * @param enquiry enquiry object for handling enquiries
     */
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
    
    /**
     * Sets the managed projects for the HDB Manager.
     * 
     * @param project The project to manage
     */
    public void setManagedProjects(Project project)
    {
    	projectManager.setManagedProjects(project);
    }
    /**
     * Checks if a project has a past date.
     * 
     * @param currentProjects The list of current projects in the system
     */
    public void PastDateChecker(ArrayList<Project> currentProjects)
    {
    	projectManager.PastDateChecker(currentProjects);
    }    
    /**
     * Checks if a project has a past date.
     * 
     * @param currentProjects The list of current projects in the system
     * @param projectName The name of the project to check
     * @return true if the project has a past date, false otherwise
     */
    public boolean PastDateCheckerProject(ArrayList<Project> currentProjects, String projectName)
    {
    	return projectManager.PastDateCheckerProject(currentProjects, projectName);
    }    
    /**
     * Returns the current active project for a specific user.
     * 
     * @param currentProjects The list of current projects in the system
     * @param userName The NRIC of the HDB Manager or Project Officer managing the project
     * @param printCheck Flag to indicate whether to print the project details
     * @return The current active Project object for the specified user
     */
    public Project currentActiveProject(ArrayList<Project> currentProjects, String userName, boolean printCheck)
    {
    	return projectManager.currentActiveProject(currentProjects, userName, printCheck);
    }    
    /**
     * Returns a project object based on the project name.
     * 
     * @param currentProjects The list of current projects in the system
     * @param projectName The name of the project to return
     * @return The Project object associated with the given project name
     */
    public Project returnProject(ArrayList<Project> currentProjects, String projectName)
    {
    	return projectManager.returnProject(currentProjects, projectName);
    }
    /**
     * Lists all existing projects in the system.
     * 
     * @param currentProjects The list of current projects in the system
     */
    public void listAllExistingProjects(ArrayList<Project> currentProjects)
    {
    	listOutProjects.listAllExistingProjects(currentProjects);
    }
    /**
     * Lists all projects associated with a specific user.
     * 
     * @param currentProjects The list of current projects in the system
     * @param username The NRIC of the HDB Manager or Project Officer managing the project
     */
    public void listSpecificProjects(ArrayList<Project> currentProjects, String username)
    {
    	listOutProjects.listSpecificProjects(currentProjects, username);
    }    
    /**
     * Toggles the visibility of a project for a specific user.
     * 
     * @param currentProjects The list of current projects in the system
     * @param projectName The name of the project to toggle visibility for
     * @param username The NRIC of the HDB Manager or Project Officer managing the project
     */
    public void toggleVisibility(ArrayList<Project> currentProjects, String projectName, String username)
    {
    	projectManager.toggleVisibility(currentProjects, projectName, username);
    }
    /**
     * Updates the marital status of applicants in the system.
     * 
     * @param currentProjects The list of current projects in the system
     */
    public void updateMaritalStatus(ArrayList<Project> currentProjects)
    {
    	projectManager.updateMaritalStatus(currentProjects);
    }
    /**
     * Creates a new project in the system.
     * 
     * @param projectDetails The details of the project to create
     * @param hdbManager The list of HDB managers in the system
     * @param hdbOfficer The list of HDB officers in the system
     * @return The created Project object
     */
    public ArrayList<Project> createProject(String projectDetails,ArrayList<HDBManager> hdbManager, ArrayList<HDBOfficer> hdbOfficer)
    {
    	return projectManager.createProject(projectDetails, hdbManager, hdbOfficer);
    }
    /**
     * Edits a project in the system.
     * 
     * @param updateContent The content to update
     * @param target The target project to edit
     * @param currentProjects The list of current projects in the system
     * @param i The index of the project to edit
     * @return true if the project was successfully edited, false otherwise
     */
    public boolean editProject(String updateContent, String target, ArrayList<Project> currentProjects, int i)
    {
    	return projectManager.editProject(updateContent, target, currentProjects, i);
    }
    /**
     * Deletes a project from the system.
     * 
     * @param targetProject The project to delete
     * @return The deleted project object
     */
    public Project deletProject(Project targetProject)
    {
    	return projectManager.deletProject(targetProject);
    }
    /**
     * Ensures a project name is unique by checking against existing projects
     * 
     * @param currentProjects list of current projects (must not be null, used to verify project references)
     * @param scanner scanner object
     * @param prompt prompt message
     * @return returns a unique project name input from the user
     */
    // Input validation
    public String uniqueProjectName(ArrayList<Project> currentProjects, Scanner scanner, String prompt)
    {
    	return inputValidator.uniqueProjectName(currentProjects, scanner, prompt);
    }
    /**
     * Validates and returns a string input from the user
     * @param scanner scanner object
     * @param prompt prompt message
     * @return returns a string input from the user
     */
    public String validateString(Scanner scanner, String prompt)
    {
    	return inputValidator.validateString(scanner, prompt);
    }
    /**
     * Validates and returns an integer input (as a string) from the user
     * @param scanner scanner object
     * @param prompt prompt message
     * @return returns an integer input (as a string) from the user
     */
    public String validateInteger(Scanner scanner, String prompt)
    {
    	return inputValidator.validateInteger(scanner, prompt);
    }
    /**
     * Verifies and returns a valid date input from the user
     * @param scanner scanner object
     * @param prompt prompt message
     * @return returns a valid date input from the user
     */
    public String verifyDate(Scanner scanner, String prompt)
    {
    	return inputValidator.verifyDate(scanner, prompt);
    }
    /**
     * Checks if the given opening and closing dates are logically valid
     * @param openingDate opening date
     * @param closingDate closing date
     * @return returns true if the given opening and closing dates are logically valid, false otherwise
     */ 
    public boolean ValidDateChecker(String openingDate, String closingDate)
    {
    	return inputValidator.ValidDateChecker(openingDate, closingDate);
    }
    /**
     * Validates a date specifically for editing purposes
     * @param openingDate opening date
     * @param closingDate closing date
     * @param forOpening true if the date is for opening, false if for closing
     * @return returns true if the date is valid, false otherwise
     */
    public boolean ValidDateCheckerforEditing(String openingDate, String closingDate, boolean forOpening)
    {
    	return inputValidator.ValidDateCheckerforEditing(openingDate, closingDate, forOpening);
    }
    /**
     * Verifies and returns a valid number of officer slots input from the user
     * @param scanner scanner object
     * @param prompt prompt message
     * @return returns a valid number of officer slots input from the user
     */
    public String verifyOfficerSlots(Scanner scanner, String prompt)
    {
    	return inputValidator.verifyOfficerSlots(scanner, prompt);
    }
        
    
    // Manager Officer Registration
    /**
     * Views the list of officer registrations.
     */
    public void viewOfficerRegistrationList()
    {
    	managerOfficerRegistration.viewOfficerRegistrationList();
    }
    /**
     * Approves or rejects officer registration requests.
     * 
     * @param currentProjects The list of current projects in the system
     * @param applicants The list of applicants in the system
     * @param HDBOfficers The list of HDB officers in the system
     * @param userName The NRIC of the HDB Manager or Project Officer managing the project
     */
    public void approveOrRejectOfficerRegistration(ArrayList<Project> currentProjects, ArrayList<Applicant> applicants, ArrayList<HDBOfficer> HDBOfficers, String userName)
    {
    	managerOfficerRegistration.approveOrRejectOfficerRegistration(currentProjects, applicants, HDBOfficers, userName);
    }
    
    /**
     * Approves or rejects officer withdrawal requests.
     * @param currentProjects the list of current projects in the system
     * @param applicants the list of applicants in the system   
     * @param username  the username of the HDB Manager or Project Officer managing the project
     */
    // Manager Applicant Registration
    public void approveOrRejectApplication(ArrayList<Project> currentProjects, ArrayList<Applicant> applicants, String username)
    {
    	managerApplicantRegistration.approveOrRejectApplication(currentProjects, applicants, username);
    }
    /**
     * Processes an applicant's withdrawal request and updates the relevant records.
     * @param currentProjects the list of current projects in the system
     * @param applicants applicants to process (must not be null)
     */
    public void approveWithdrawal(ArrayList<Project> currentProjects, ArrayList<Applicant> applicants)
    {
    	managerApplicantRegistration.approveWithdrawal(currentProjects, applicants);
    }
    /** 
     * Generates a report of applicants based on specified filters.
     * <p>
     * This method allows a manager to generate a report of applicants by providing filters such as marital status and age range.
     * * </p>
     * 
     * @param applicants The list of applicants in the system
     * @param maritalStatusFilter The marital status filter for the report
     * @param minAge The minimum age filter for the report
     * @param maxAge The maximum age filter for the report
     * 
    */
    public void generateApplicantReport(ArrayList<Applicant> applicants, String maritalStatusFilter, Integer minAge, Integer maxAge)
    {
    	managerApplicantRegistration.generateApplicantReport(applicants, maritalStatusFilter, minAge, maxAge);
    }
    
    // Enquiries
    /**
     * Views the list of enquiries for a specific project.
     * 
     * @param currentProjects The list of current projects in the system
     */
    public void viewEnquiries(ArrayList<Project> currentProjects)
    {
    	managerOfficerRegistration.viewEnquiries(currentProjects);    	
    }
    /**
     * Replies to an enquiry for a specific project.
     * 
     * @param currentProjects The list of current projects in the system
     * @param NRIC The NRIC of the HDB Manager or Project Officer managing the project
     * @param scanner Scanner object for user input
     */
    public void replyToEnquiry(ArrayList<Project> currentProjects, String NRIC, Scanner scanner)
    {
    	enquiry.replyToEnquiry(currentProjects, NRIC, scanner);
    }    
}
