// File: Application.java

public class Application {

    //Static attribute
    private static int applicationId = 0;
    //Attributes
    private Applicant applicant;
    private Project project;
    private ApplicationStatus applicationStatus;
    private boolean isBooked;
    private boolean bookingRequested;
    private FlatType flatType;
    //Constructor
    public Application(Applicant applicant, Project project, FlatType flatType) {
        Application.applicationId = applicationId++;
        this.applicant = applicant;
        this.project = project;
        this.isBooked = false;
        this.bookingRequested = false;
        this.applicationStatus = ApplicationStatus.SUCCESSFUL; //SHOULD BE PENDING. CHANGED TO SUCCESSFUL TO TEST OTHER FEATURES
        this.flatType = flatType;
    }

    //Getters and Setters
    public Applicant getApplicant() {
        return applicant;
    }
    
    public Project getProject() {
        return project;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public boolean getIsBooked() {
        return isBooked;
    }
    
    public boolean getBookingRequested() {
    	return bookingRequested;
    }
    
    public void setIsBooked(boolean isBooked) {
        this.isBooked = isBooked;
    }

    public void setBookingRequested(boolean bookingRequested) {
    	this.bookingRequested = bookingRequested;
    }
    public FlatType getFlatType() {
        return flatType;
    }

    public int getApplicationId() {
        return applicationId;
    }

}
