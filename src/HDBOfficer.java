import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.time.LocalDate;
import java.sql.Date;
import java.time.ZoneId;
/**
 * Represents various method that HDB officer access to.
 * <p>
 * This class extends {@link Applicant} and adds functionality specific to HDB officers,
 * including project management, application processing, and enquiry handling.
 * </p>
 * 
 */

public class HDBOfficer extends Applicant {
    private Project managedProject;
    private ArrayList<Project> upcomingProjects = new ArrayList<Project>();
    private Map<Project, OfficerRegistrationStatus> registrationStatusMap = new HashMap<>();
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
     * Checks if the officer is managing a project and if the current date is past the application closing date.
     * <p>
     * If the current date is past the application closing date, the officer is no longer managing the project.
     * </p>
     */
    public void checkCurrentProject() {
        if (managedProject == null) {
            return;
        }

        LocalDate currentDate = LocalDate.now();

        // Assuming getApplicationClosingDate() returns either java.util.Date or java.sql.Date
        java.util.Date closingDate = managedProject.getApplicationClosingDate(); 
        LocalDate applicationClosingDate = closingDate.toInstant()
                                                      .atZone(ZoneId.systemDefault())
                                                      .toLocalDate();

        if (currentDate.isAfter(applicationClosingDate)) {
            managedProject = null;
            isManagingOfficer = false;
            managedApplications.clear();
        }
    }

    /**
     * Checks if there are any new projects that the officer is managing.
     * <p>
     * If the current date matches the application opening date of any upcoming project,
     * the officer is set as the managing officer for that project.
     * </p>
     */

    public void checkNewProject() {
        LocalDate currentDate = LocalDate.now();

        for (Project project : upcomingProjects) {
            java.util.Date openingDate = project.getApplicationOpeningDate(); // Should return java.util.Date or java.sql.Date
            LocalDate applicationOpeningDate = openingDate.toInstant()
                                                           .atZone(ZoneId.systemDefault())
                                                           .toLocalDate();

            if (currentDate.equals(applicationOpeningDate)) {
                managedProject = project;
                isManagingOfficer = true;
                return;
            }
        }
    }

    /**
     * Gets the list of upcoming projects.
     *
     * @return list of upcoming projects
     */
    public Map<Project, OfficerRegistrationStatus> getRegistrationStatusMap(){
    	return registrationStatusMap;
    }
    
    // TODO: method to check if there are any approved projects in registrationStatusMap. if there are, transfer them to upcomingProjects
    /**
     * Checks if there are any approved projects in the registration status map.
     * <p>
     * If there are approved projects, they are added to the list of upcoming projects.
     * </p>
     */
    public void checkApprovedProjects() {
    	if (registrationStatusMap.size() == 0) {
    		return;
    	}
    	registrationStatusMap.forEach((project, status) -> {
    	    if (status == OfficerRegistrationStatus.APPROVED) {
    	    	upcomingProjects.add(project);
    	    }
    	});
    }
    
    // TODO: method to register for a new project. must do the checks
    /**
     * Registers the officer for a project.
     * <p>
     * Checks if the officer has already applied for the project as a applicant.
     * Checks if the officer is applying for a project where their application period is the same the current project and is managing the project.
     * </p>
     *
     * @param project the project to register for
     * @return true if the officer is registered for the project, false otherwise
     */
    public boolean registerForProject(Project project) {
        ArrayList<Project> pastAppliedProjects = new ArrayList<Project>();
        pastAppliedProjects = ApplicationLogic.filterByPastAppliedProjects(managedApplications);
        if (pastAppliedProjects != null && pastAppliedProjects.contains(project))  {
            return false;
        }

        if (registrationStatusMap.size() > 0) {
        	
            final boolean[] canApply = {true};
            registrationStatusMap.forEach((Project mappedProject, OfficerRegistrationStatus status) -> {
                // check if the application period of the project is the same as the current project
                boolean dateFallsWithinApplication = project.getApplicationOpeningDate().after(mappedProject.getApplicationOpeningDate()) && project.getApplicationClosingDate().before(mappedProject.getApplicationClosingDate());
                boolean dateEquals = project.getApplicationOpeningDate().equals(mappedProject.getApplicationOpeningDate()) 
                    && project.getApplicationClosingDate().equals(mappedProject.getApplicationClosingDate()) 
                    && project.getApplicationClosingDate().equals(mappedProject.getApplicationOpeningDate()) 
                    && project.getApplicationOpeningDate().equals(mappedProject.getApplicationClosingDate());
                if((dateFallsWithinApplication || dateEquals )&& status == OfficerRegistrationStatus.APPROVED) {
                    canApply[0] = false;

                }
            });

            if (!canApply[0]) {
                return false;
            }
        }
		registrationStatusMap.put(project, OfficerRegistrationStatus.PENDING);
		return true;   
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
     * add managed applications to the list of managed applications.
     * @param application the application to be added
     *
     */
    public void addManagedApplications(Application application) {
    	managedApplications.add(application);
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
     */
    
    public void viewOfficerRegistrationStatus() {
    	if (managedProject != null) {
    		System.out.printf("Current Project: %s",managedProject.getProjectName());
    		System.out.println();
    	}
    	registrationStatusMap.forEach((project, status) -> {
    		System.out.printf("Project: %s, Status: %s", project.getProjectName(), status);   	
    	});
    }
    
    /**
     * Displays the list of book requests for the managed applications.
     * <p>
     * If there are no managed applications, a message is displayed indicating that there are no requests.
     * </p>
     */
    public void viewBookRequests() {
        if (managedApplications.size() > 0) {
            for (Application app : managedApplications) {
            	if (app != null) {
            		System.out.println(app.getApplicant().getName() + " " + app.getApplicationStatus());
            	}   
            }
        }
    }

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
     * @return true if booking is successful, false otherwise
     */
    public boolean helpBookFlat(Application application) {
        if (ApplicationService.processBooking(application, managedProject)) {
        	System.out.println("Booking successful!");
        	return true;
        }            
        System.out.println("Booking unsuccessful. Chosen flat type has no more units.");
        return false;
    }

    /**
     * Generates a receipt for a flat booking.
     * <p>
     * Delegates to ApplicationService to generate a comprehensive receipt.
     * </p>
     *
     * @param application the application to generate receipt for
     */
    public void generateFlatSelectionReceipt(Application application) {
        String receipt = ApplicationService.generateFlatSelectionReceipt(application);
        if (receipt != null) {
            System.out.println(receipt);
        }
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
     * set officer registration status for a project.
     * <p>
     * This method updates the registration status of a project in the officer's registration status map.
     * </p>
     * 
     * @param project the project whose registration status is to be set
     * @param status the new registration status to be set for the project
     */
    public void setOfficerRegistrationStatus(Project project, OfficerRegistrationStatus status) {
    	if(registrationStatusMap.containsKey(project)) {
    		registrationStatusMap.put(project, status);
    	}
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