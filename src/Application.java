// File: Application.java

public class Application {

    //Static attribute
    private static int applicationId = 0;
    //Attributes
    private Applicant applicant;
    private Project project;
    private ApplicationStatus applicationStatus;
    private boolean isBooked;

    //Constructor
    public Application(Applicant applicant, Project project) {
        Application.applicationId = applicationId++;
        this.applicant = applicant;
        this.project = project;
        this.isBooked = false;
        this.applicationStatus = ApplicationStatus.PENDING;
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

    public void setIsBooked(boolean isBooked) {
        this.isBooked = isBooked;
    }
    
    
    
}
