import java.util.ArrayList;

/**
 * Represents an applicant user in the system.
 * Extends the base {@link User} class and manages housing applications, projects, and enquiries.
 * @see FlatType
 * @see ApplicantApp
 */
public class Applicant extends User {
    private Application currentApplication; 
    private ArrayList<Project> pastAppliedProjects = new ArrayList<Project>();
    
    /**
     * Default constructor for Applicant.
     */
    public Applicant() {};
    
    /**
     * Constructs an Applicant with complete details.
     * 
     * @param name the applicant's name
     * @param NRIC the applicant's national identification number
     * @param userID the applicant's user ID
     * @param password the applicant's password
     * @param age the applicant's age
     * @param maritalStatus the applicant's marital status
     * @param appliedProject the project the applicant is applying to
     * @param pastAppliedProjects list of previously applied projects
     * @param applicationStatus current application status
     * @param bookedFlatType the type of flat booked
     */
    public Applicant(String name, String NRIC, int userID, String password, int age, 
                    MaritalStatus maritalStatus, Project appliedProject,  
                    ArrayList<Project> pastAppliedProjects, ApplicantStatus applicationStatus, 
                    FlatType bookedFlatType) {
        super(name, NRIC, userID, password, age, maritalStatus);
        //TODO FILL IN THE REST OF THE ATTRIBUTES
    }
    
    /**
     * Constructs an Applicant with basic details.
     * 
     * @param name the applicant's name
     * @param NRIC the applicant's national identification number
     * @param userID the applicant's user ID
     * @param password the applicant's password
     * @param age the applicant's age
     * @param maritalStatus the applicant's marital status
     */
    public Applicant(String name, String NRIC, int userID, String password, 
                    int age, MaritalStatus maritalStatus) {
        super(name, NRIC, userID, password, age, maritalStatus);
    }
    
    /**
     * Creates a new enquiry about a project.
     * 
     * @param project the project being enquired about
     * @param message the enquiry message
     * @return the created Enquiry object
     */
    public Enquiry createEnquiry(Project project, String message) {
        Enquiry[] enquiry = new Enquiry[10];
        return enquiry[0];
    }
    
    /**
     * Retrieves all enquiries made by the applicant.
     * 
     * @return array of Enquiry objects
     */
    public Enquiry[] viewEnquiries() {
        Enquiry[] enquiry = new Enquiry[10];
        return enquiry;
    }
    
    /**
     * Edits an existing enquiry with a new message.
     * 
     * @param enquiry the enquiry to modify
     * @param newMessage the updated message content
     */
    public void editEnquiry(Enquiry enquiry, String newMessage) {
        // Implementation goes here
    }
    
    /**
     * Deletes an existing enquiry.
     * 
     * @param enquiry the enquiry to delete
     */
    public void deletEnquiry(Enquiry enquiry) {
        // Implementation goes here
    }
    
    /**
     * Checks if the applicant is eligible to apply for a project.
     * 
     * @param project the project to check eligibility for
     * @return true if eligible to apply, false otherwise
     */
    public boolean canApply(Project project) {
        return false;
    }
    
    /**
     * Sets the preferred flat type for the applicant.
     * 
     * @param flatType the preferred flat type
     */
    public void setFlatType(FlatType flatType) {
        this.currentApplication.setFlatType(flatType);
    }
    
    /**
     * Gets the applicant's preferred flat type.
     * 
     * @return the booked flat type
     */
    public FlatType getFlatType() {
        return currentApplication.getFlatType();
    }

    /**
     * Displays all available projects that are visible to applicants.
     * 
     * @param projects list of all projects to display
     */
    public void viewAvailableProjects(ArrayList<Project> projects) {
        int size = projects.size();
        for (int i = 0; i < size; i++) {
            Project currentProject = projects.get(i);
            if (currentProject.getVisibility() == true) {
                System.out.println("Project ID: " + (i+1));
                System.out.println(projects.get(i).toString());
            }	
        } 	  
    }

    /**
     * Submits an application for a housing project with preferred flat type.
     * 
     * @param project the project to apply for
     * @param flatType the preferred flat type
     */
    public void applyForProject(Project project, FlatType flatType) {
        System.out.println("Applied for project: " + project.getProjectName());
        System.out.println("Flat type: " + flatType.toString());
        Application application = new Application(this, project, flatType);
        project.addApplication(application);
        currentApplication = application;
        System.out.println("Application ID: " + application.getApplicationId());
    }
    
    /**
     * Retrieves the status of the current application.
     * 
     * @return the current application status
     */
    public ApplicationStatus viewApplicationStatus() {
        return currentApplication.getApplicationStatus();
    }
    
    /**
     * Requests withdrawal of the current application.
     * Changes application status to PENDINGWITHDRAWAL.
     */
    public void requestWithdrawal() {
        currentApplication.setApplicationStatus(ApplicationStatus.PENDINGWITHDRAWAL);
    }
    
    /*
    * @param allow the user to book flat
    * @return void
    * @description - This function will send a booking request to a managing HDB Officer. Only valid if the applicant's application is successful (checked at ApplicantOfficerApp)   */

    public void bookFlat(HDBOfficer officer) {
        currentApplication.setBookingRequested(true);
        officer.receiveBookFlatRequest(currentApplication);
    }

    /**
     * Gets the current application.
     * 
     * @return the current Application object
     */
    public Application getCurrentApplication() {
        return currentApplication;
    }

    /**
     * Sets the current application.
     * 
     * @param currentApplication the Application object to set as current
     */
    public void setCurrentApplication(Application currentApplication) {
        this.currentApplication = currentApplication;
    }
}
