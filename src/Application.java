/**
 * Represents a housing application submitted by an applicant for a specific project and flat type.
 * <p>
 * This class tracks the application status, booking information, and maintains
 * references to the applicant, project, and flat type associated with the application.
 * </p>
 * 
 * @see Applicant
 * @see Project
 * @see FlatType
 * @see ApplicationStatus
 */
public class Application {

    /** Static counter for generating unique application IDs */
    private static int applicationId = 0;
    
    /** The applicant who submitted this application */
    private Applicant applicant;
    
    /** The project this application is for */
    private Project project;
    
    /** Current status of the application */
    private ApplicationStatus applicationStatus;
    
    /** Flag indicating if the flat has been booked */
    private boolean isBooked;
    
    /** Flag indicating if booking has been requested */
    private boolean bookingRequested;

    /** The type of flat being applied for */
    private FlatType flatType;

    //Constructor
    public Application() {
    	Application.applicationId = applicationId++;
    };
    /**
     * Constructs a new Application with the specified details.
     * 
     * @param applicant the applicant submitting the application
     * @param project the project being applied to
     * @param flatType the type of flat being requested
     * Application status is initially set to SUCCESSFUL for testing purposes,
     * but should normally be PENDING in production
     */
    public Application(Applicant applicant, Project project, FlatType flatType) {
        Application.applicationId = applicationId++;
        this.applicant = applicant;
        this.project = project;
        this.isBooked = false;
        this.bookingRequested = true;
        this.applicationStatus = ApplicationStatus.PENDING; 
        this.flatType = flatType;
    }

    // ========== GETTERS AND SETTERS ==========
    
    /**
     * Gets the applicant associated with this application.
     * 
     * @return the Applicant object
     */
    public Applicant getApplicant() {
        return applicant;
    }
    
    /**
     * Gets the project associated with this application.
     * 
     * @return the Project object
     */
    public Project getProject() {
        return project;
    }

    /**
     * Gets the current status of the application.
     * 
     * @return the ApplicationStatus
     */
    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    /**
     * Checks if the flat has been booked.
     * 
     * @return true if booked, false otherwise
     */
    public boolean getIsBooked() {
        return isBooked;
    }
    
    public void setProject(Project project) {
    	this.project = project;
    }
    /**
     * Checks if booking has been requested.
     * 
     * @return true if booking requested, false otherwise
     */
    public boolean getBookingRequested() {
        return bookingRequested;
    }
    
    /**
     * Sets the booked status of the application.
     * 
     * @param isBooked true to mark as booked, false otherwise
     */
    public void setIsBooked(boolean isBooked) {
        this.isBooked = isBooked;
    }

    /**
     * Sets the booking requested status.
     * 
     * @param bookingRequested true to indicate booking requested, false otherwise
     */
    public void setBookingRequested(boolean bookingRequested) {
        this.bookingRequested = bookingRequested;
    }
    
    /**
     * Updates the application status.
     * 
     * @param status the new ApplicationStatus
     */
    public void setApplicationStatus(ApplicationStatus status) {
        applicationStatus = status;
    }
    
    /**
     * Gets the flat type being applied for.
     * 
     * @return the FlatType object
     */
    public FlatType getFlatType() {
        return flatType;
    }

    /**
     * Sets the flat type being applied for.
     * 
     * @param flatType the new FlatType object
     */
    public void setFlatType(FlatType flatType) {
        this.flatType = flatType;
    }
    
    /**
     * Sets the applicant associated with this application.
     * 
     * @param applicant the new Applicant object
     */
    public void setApplicant(Applicant applicant) {
    	this.applicant = applicant;
    }

    /**
     * Gets the application ID.
     * 
     * @return the application ID number
     */
    public int getApplicationId() {
        return applicationId;
    }
    
    /**
     * Returns a string representation of the application details.
     * 
     * @return formatted string containing applicant and flat information
     */
    @Override
    public String toString() {
        String applicationDetails = String.format("%s, %s, %d, %s, %s", 
            applicant.getName(), 
            applicant.getNRIC(), 
            applicant.getAge(), 
            applicant.getMaritalStatus(), 
            flatType);
        return applicationDetails;
    }
}