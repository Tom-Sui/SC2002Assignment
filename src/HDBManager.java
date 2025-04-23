import java.util.ArrayList;
import java.util.Scanner;

/**
 * HDBManager.java
 * 
 * This class represents a Housing Development Board (HDB) Manager who manages projects and applicants.
 * It extends the User class and implements various interfaces for project management, input validation,
 * and officer/applicant registration.
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
	private ArrayList<Project> managedProjects;

    /**
     * Constructor for HDBManager class.
     * Initializes the project manager, list out projects, input validator,
     * manager officer registration, and manager applicant registration.
     * 
     * @param projectManager the project manager instance
     * @param listOutProjects the list out projects instance
     * @param inputValidator the input validator instance
     * @param managerOfficerRegistration the manager officer registration instance
     * @param managerApplicantRegistration the manager applicant registration instance
     */
    public HDBManager(I_ProjectManager projectManager, I_ListOutProjects listOutProjects, 
    				  I_InputValidator inputValidator, I_ManagerOfficerRegistration managerOfficerRegistration,
    				  I_ManagerApplicantRegistration managerApplicantRegistration)
    {
    	this.projectManager = projectManager;
    	this.listOutProjects = listOutProjects;
    	this.inputValidator = inputValidator;
    	this.managerOfficerRegistration = managerOfficerRegistration;
    	this.managerApplicantRegistration = managerApplicantRegistration;
        this.managedProjects = new ArrayList<>();
    }
    
    /**
     * Retrieves the list of projects managed by this HDB Manager.
     * 
     * @return ArrayList of Project objects managed by this manager
     */
    public ArrayList<Project> getProject() {
        return managedProjects;
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
    /**
     * Adds a project to the list of managed projects.
     * 
     * @param project The project to add
     */
    public void setManagedProjects(Project project)
    {
    	this.managedProjects.add(project);
    }
    
    /**
     * Checks if a project is past its closing date.
     * 
     * @param currentProjects The list of current projects
     */
    public void PastDateChecker(ArrayList<Project> currentProjects)
    {
    	projectManager.PastDateChecker(currentProjects);
    }    
    
    /**
     * Checks if a project is past its closing date.
     * 
     * @param currentProjects The list of current projects
     * @param projectName The name of the project to check
     * @return true if the project is past its closing date, false otherwise
     */
    public boolean PastDateCheckerProject(ArrayList<Project> currentProjects, String projectName)
    {
    	return projectManager.PastDateCheckerProject(currentProjects, projectName);
    }    
    
    /**
     * Checks if a project is currently active for a specific user.
     * 
     * @param currentProjects The list of current projects
     * @param userName The username of the user
     * @param printCheck Whether to print the project details
     * @return The current active project for the user, or null if none found
     */
    public Project currentActiveProject(ArrayList<Project> currentProjects, String userName, boolean printCheck)
    {
    	return projectManager.currentActiveProject(currentProjects, userName, printCheck);
    }    


    /**
     * Return specfic project based on the project name.
     * @param currentProjects The list of current projects
     * @param projectName The name of the project to return
     * @return The Project object if found, null otherwise
     */
    public Project returnProject(ArrayList<Project> currentProjects, String projectName)
    {
    	return projectManager.returnProject(currentProjects, projectName);
    }
    
    /**
     * list all existing current projects.
     * 
     * @param currentProjects The list of current projects
     */
    public void listAllExistingProjects(ArrayList<Project> currentProjects)
    {
    	listOutProjects.listAllExistingProjects(currentProjects);
    }
    
    /**
     * list all existing current projects for a specific user.
     * 
     * @param currentProjects The list of current projects
     * @param username The username of the user
     */ 
    public void listSpecificProjects(ArrayList<Project> currentProjects, String username)
    {
    	listOutProjects.listSpecificProjects(currentProjects, username);
    }    
    
    /**
     * Toggles the visibility of a project for a specific user.
     * 
     * @param currentProjects The list of current projects
     * @param projectName The name of the project to toggle visibility for
     * @param username The username of the user
     */
    public void toggleVisibility(ArrayList<Project> currentProjects, String projectName, String username)
    {
    	projectManager.toggleVisibility(currentProjects, projectName, username);
    }
    
    /**
     * Updates the marital status of a project for a specific user.
     * 
     * @param currentProjects The list of current projects
     */
    public void updateMaritalStatus(ArrayList<Project> currentProjects)
    {
    	projectManager.updateMaritalStatus(currentProjects);
    }
    
    /**
     * Creates a new project.
     * 
     * @param projectDetails The details of the project to create
     * @param hdbManager The list of HDB managers
     * @param hdbOfficer The list of HDB officers
     * @return An ArrayList of Project objects created
     */
    public ArrayList<Project> createProject(String projectDetails,ArrayList<HDBManager> hdbManager, ArrayList<HDBOfficer> hdbOfficer)
    {
    	return projectManager.createProject(projectDetails, hdbManager, hdbOfficer);
    }
    
    /**
     * Edits an existing project.
     * 
     * @param projectName The name of the project to edit
     * @param updateContent The content to update
     * @param target The target project to edit
     * @param currentProjects The list of current projects
     * @param i The index of the project to edit
     * @return true if the project was successfully edited, false otherwise
     */
    public boolean editProject(String projectName, String updateContent, String target, ArrayList<Project> currentProjects, int i)
    {
    	return projectManager.editProject(projectName, updateContent, target, currentProjects, i);
    }
    
    /**
     * Deletes a project.
     * 
     * @param targetProject The project to delete
     * @return The deleted Project object
     */
    public Project deletProject(Project targetProject)
    {
    	return projectManager.deletProject(targetProject);
    }
        
    // Input validation
    /**
     * Validates a project name to ensure it is unique.
     * 
     * @param currentProjects The list of current projects
     * @param scanner The Scanner object for user input
     * @param prompt The prompt message for user input
     * @return A unique project name entered by the user
     */
    public String uniqueProjectName(ArrayList<Project> currentProjects, Scanner scanner, String prompt)
    {
    	return inputValidator.uniqueProjectName(currentProjects, scanner, prompt);
    }
    
    /**
     * Validates a string input from the user.
     * 
     * @param scanner The Scanner object for user input
     * @param prompt The prompt message for user input
     * @return A valid string entered by the user
     */
    public String validateString(Scanner scanner, String prompt)
    {
    	return inputValidator.validateString(scanner, prompt);
    }
    
    /**
     * Validates an integer input from the user.
     * 
     * @param scanner The Scanner object for user input
     * @param prompt The prompt message for user input
     * @return A valid integer entered by the user
     */
    public String validateInteger(Scanner scanner, String prompt)
    {
    	return inputValidator.validateInteger(scanner, prompt);
    }
    
    /**
     * Validates a date input from the user.
     * 
     * @param scanner The Scanner object for user input
     * @param prompt The prompt message for user input
     * @return A valid date entered by the user
     */
    public String verifyDate(Scanner scanner, String prompt)
    {
    	return inputValidator.verifyDate(scanner, prompt);
    }
    
    /**
     * Checkings if the date is valid for opening or closing a project.
     * 
     * @param openingDate The opening date of the project
     * @param closingDate The closing date of the project
     * @return A valid date entered by the user
     */
    public boolean ValidDateChecker(String openingDate, String closingDate)
    {
    	return inputValidator.ValidDateChecker(openingDate, closingDate);
    }
    /**
     * Checkings if the date is valid for opening or closing a project during editing.
     * 
     * @param openingDate The opening date of the project
     * @param closingDate The closing date of the project
     * @param forOpening Whether the date is for opening or closing
     * @return A valid date entered by the user
     */
    public boolean ValidDateCheckerforEditing(String openingDate, String closingDate, boolean forOpening)
    {
    	return inputValidator.ValidDateCheckerforEditing(openingDate, closingDate, forOpening);
    }
    
    /**
     * Validates the number of officer slots input from the user.
     * 
     * @param scanner The Scanner object for user input
     * @param prompt The prompt message for user input
     * @return A valid number of officer slots entered by the user
     */
    public String verifyOfficerSlots(Scanner scanner, String prompt)
    {
    	return inputValidator.verifyOfficerSlots(scanner, prompt);
    }
        
    // Manager Officer Registration
    /**
     * View officer registration list.
     */
    public void viewOfficerRegistrationList()
    {
    	managerOfficerRegistration.viewOfficerRegistrationList();
    }
    
    /**
     * Approve or reject officer registration.
     * 
     * @param currentProjects The list of current projects
     * @param applicants The list of applicants
     * @param HDBOfficers The list of HDB officers
     * @param userName The username of the officer
     */
    public void approveOrRejectOfficerRegistration(ArrayList<Project> currentProjects, ArrayList<Applicant> applicants, ArrayList<HDBOfficer> HDBOfficers, String userName)
    {
    	managerOfficerRegistration.approveOrRejectOfficerRegistration(currentProjects, applicants, HDBOfficers, userName);
    }
    
    // Manager Applicant Registration
    /**
     * Arrpove or reject application for a project.
     * 
     * @param currentProjects The list of current projects
     * @param applicants The list of applicants
     * @param username The username of the applicant
     */
    public void approveOrRejectApplication(ArrayList<Project> currentProjects, ArrayList<Applicant> applicants, String username)
    {
    	managerApplicantRegistration.approveOrRejectApplication(currentProjects, applicants, username);
    }
    
    /**
     * Approve withdrawal of an application for a project.
     * 
     * @param currentProjects The list of current projects
     * @param applicants arraylist of applicants
     */
    public void approveWithdrawal(ArrayList<Project> currentProjects, ArrayList<Applicant> applicants)
    {
    	managerApplicantRegistration.approveWithdrawal(currentProjects, applicants);
    }
    
    /**
     * Generate a report of applicants based on marital status and age range.
     * 
     * @param applicants The list of applicants
     * @param maritalStatusFilter The marital status filter
     * @param minAge The minimum age filter
     * @param maxAge The maximum age filter
     */
    public void generateApplicantReport(ArrayList<Applicant> applicants, String maritalStatusFilter, Integer minAge, Integer maxAge)
    {
    	managerApplicantRegistration.generateApplicantReport(applicants, maritalStatusFilter, minAge, maxAge);
    }
    /**
     * View enquiries related to a project.
     * 
     * @param currentProjects The list of current projects
     */
    public void viewEnquiries(ArrayList<Project> currentProjects) {
    	
    	managerOfficerRegistration.viewEnquiries(currentProjects);
    } 
}
