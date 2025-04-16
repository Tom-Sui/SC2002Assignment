import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Represents various method that HDB officer access to.
 * <p>
 * This class extends {@link Applicant} and adds functionality specific to HDB officers,
 * including project management, application processing, and enquiry handling.
 * </p>
 * 
 */
public class HDBOfficer extends Applicant{
    private Project managedProject;
    private OfficerRegistrationStatus officerRegistrationStatus = OfficerRegistrationStatus.PENDING;
    private ArrayList<Application> managedApplications = new ArrayList<Application>();
    private boolean isManagingOfficer = false;

    /**
     * Default constructor for HDBOfficer.
     */
    public HDBOfficer() {}

    /**
     * Constructs an HDBOfficer with specified details and managed project.
     *
     * @param name the officer's name
     * @param NRIC the officer's NRIC
     * @param userID the officer's user ID
     * @param password the officer's password
     * @param age the officer's age
     * @param maritalStatus the officer's marital status
     * @param managedProject the project assigned under this officer
     */
    public HDBOfficer(String name, String NRIC, int userID, String password, int age, 
                     MaritalStatus maritalStatus, Project managedProject) {
        super(name, NRIC, userID, password, age, maritalStatus);
        this.managedProject = managedProject;
    }

    /**
     * Creates a new enquiry for a project.
     *
     * @param project the project to enquire about
     * @param message the enquiry message
     * @return the created enquiry
     */
    public Enquiry createEnquiry(Project project, String message) {
        Enquiry[] enquiry = new Enquiry[10];
        return enquiry[0];
    }

    /**
     * Retrieves all enquiries.
     *
     * @return an array of enquiries
     */
    public Enquiry[] viewEnquiries() {
        Enquiry[] enquiry = new Enquiry[10];
        return enquiry;
    }

    /**
     * Edits an existing enquiry.
     *
     * @param enquiry the enquiry to edit
     * @param newMessage the new message content
     */
    public void editEnquiry(Enquiry enquiry, String newMessage) {}

    /**
     * Deletes an existing enquiry.
     *
     * @param enquiry the enquiry to delete
     */
    public void deletEnquiry(Enquiry enquiry) {}

    /**
     * Checks if the officer can apply for a project.
     *
     * @param project the project to check
     * @return false (officers cannot apply for projects)
     */
    public boolean canApply(Project project) {
        return false;
    }

    /**
     * Sets the managed project for this officer.
     *
     * @param project the project to be managed
     */
    public void setManagedProject(Project project) {
        managedProject = project;
    }

    /**
     * Registers the officer for a project.
     *
     * @param project the project to register for
     */
    public void registerForProject(Project project) {

    }
    
    /**
     * Displays details of the managed project.
     */
    public void viewProjectDetails(){
        System.out.println("Manager Name: " + managedProject.getHDBManager().getName());
        System.out.println(managedProject.toString());
    }


    /**
     * Gets the officer's registration status.
     *
     * @return the current registration status
     */
    public OfficerRegistrationStatus getOfficerRegistrationStatus() {
        return officerRegistrationStatus;
    }
    
    /*
    * @param allow HDBOfficer to view all booking requests from applicants.
    * @return void  
    * @description - The HDBOfficer can only receive and view requests of applicants that applied to the project he/she is part of */
    
    public void viewBookRequests() {
        if (managedApplications.size() > 0) {
            for (Application app : managedApplications) {
                System.out.println(app.getApplicant().getName() + " " + app.getApplicationStatus());
            }
        }
    }

    /**
     * Replies to an enquiry.
     *
     * @param enquiry the enquiry to reply to
     * @param reply the reply message
     */
    public void replyToEnquiry(Enquiry enquiry, String reply) {}

    /**
     * Gets all managed applications.
     *
     * @return list of managed applications
     */
    public ArrayList<Application> getApplications() {
        return managedApplications;
    }

    /**
     * Receives a book flat request from an applicant.
     *
     * @param application the application containing the request
     */
    public void receiveBookFlatRequest(Application application) {
        managedApplications.add(application);
    }

    /**
     * Processes a flat booking request.
     * <p>
     * Attempts to book a flat of the chosen type if units are available.
     * Updates both the application status and project flat availability.
     * </p>
     *
     * @param application the application to be processed
     */
    public void helpBookFlat(Application application) {
        FlatType chosenFlatType = application.getFlatType();
        ArrayList<FlatType> flatTypes = managedProject.getFlatTypes();
        int remainingUnits = FlatTypeLogic.returnFilteredFlatTypeUnits(flatTypes, chosenFlatType);
        if (remainingUnits >= 1) {
            // Updating applicant and his application process
            application.setIsBooked(true);
            application.setBookingRequested(false);
            application.getApplicant().setFlatType(chosenFlatType);
            application.setApplicationStatus(ApplicationStatus.BOOKED);
        
            // Updating units in project
            FlatTypeLogic.updateFilteredFlatTypeUnits(flatTypes, chosenFlatType);
            System.out.println("Booking successful!");
        }
        else {
            System.out.println("Booking unsuccessful. Chosen flat type has no more units.");
        }
    }

    /**
     * Generates a receipt for a flat application.
     *
     * @param application the application to generate receipt for
     */
    public void generateFlatReceipt(Application application) {
        System.out.println(application.toString());
    }

    /**
     * Checks if the officer is currently managing a project.
     *
     * @return true if managing a project, false otherwise
     */
    public boolean isManagingOfficer() {
        return isManagingOfficer;
    }

    /**
     * Sets the managing officer status.
     *
     * @param isManagingOfficer true to set as managing officer, false otherwise
     */
    public void setManagingOfficer(boolean isManagingOfficer) {
        this.isManagingOfficer = isManagingOfficer;
    }

    /**
     * Returns a string representation of the officer.
     *
     * @return string containing officer's name and age
     */
    public String toString() {
        String officerDetails = String.format("%s, %d years old", super.getName(), super.getAge());
        return officerDetails;
    }
}