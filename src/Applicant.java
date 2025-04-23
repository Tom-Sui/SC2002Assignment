import java.util.ArrayList;
import java.util.List;

/**
 * Represents an applicant user in the system.
 * Extends the base {@link User} class and manages housing applications, projects, and enquiries.
 * @see FlatType
 * @see ApplicantApp
 */
public class Applicant extends User{
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
    
    /*
    * @param allow the user to book flat
    * @return void
    * @description - This function will send a booking request to a managing HDB Officer. Only valid if the applicant's application is successful (checked at ApplicantOfficerApp)   */

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

    /**
     * Appends a past application to the applicant's past applications list.
     * 
     * @param pastApplication the Application object to append as a past application
     */
    public void appendPastApplications(Application pastApplication) {
        this.pastApplications.add(pastApplication);
    }

}

