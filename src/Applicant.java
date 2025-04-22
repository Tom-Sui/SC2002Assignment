import java.util.ArrayList;
import java.util.List;

/**
 * Represents an applicant user in the system.
 * Extends the base {@link User} class and manages housing applications, projects, and enquiries.
 * @see FlatType
 */
public class Applicant extends User {
    private Application currentApplication; 
    private List<Application> pastApplications = new ArrayList<>();
    
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
     * @param currentApplication the application the applicant is applying to
     * @param pastApplications list of previous applications
     */
    public Applicant(String name, String NRIC, int userID, String password, int age, 
                    MaritalStatus maritalStatus, Application currentApplication,  
                    List<Application> pastApplications) {
        super(name, NRIC, userID, password, age, maritalStatus);
        this.currentApplication = currentApplication;
        this.pastApplications = pastApplications;
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
     * Sets the preferred flat type for the applicant.
     * 
     * @param flatType the preferred flat type
     */
    public void setFlatType(FlatType flatType) {
	   if (currentApplication != null) {
	        currentApplication.setFlatType(flatType);
	    }
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
     * Gets the applicant's past applications.
     * 
     * @return list of past applied applications
     */

    public List<Application> getPastApplications() {
        return pastApplications;
    }

    /**
     * Submits an application for a housing project with preferred flat type.
     * 
     * @param project the project to apply for
     * @param flatType the preferred flat type
     */
    public void applyForProject(Project project, FlatType flatType) {
        ApplicationService.applyProject(this, project, flatType);
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
    
    /**
     * books a flat for the current application.
     * 
     * @param officer the HDB officer processing the cancellation
     */
    public void bookFlat(HDBOfficer officer) {
    	ApplicationService.bookFlat(currentApplication, officer);
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
