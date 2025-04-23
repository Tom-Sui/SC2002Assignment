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
    
    public void checkCurrentProject() {
    	// Return if the officer is not managing any project.
    	if (managedProject == null) {
    		return;
    	}
    	LocalDate currentDate = LocalDate.now();
    	//Convert date type to LocalDate so that we can compare
        LocalDate applicationClosingDate = ((Date) managedProject.getApplicationClosingDate()).toLocalDate();
    	if (currentDate.isAfter(applicationClosingDate)) {
    		managedProject = null;
    		isManagingOfficer = false;
    		managedApplications.clear();
    	}
    }
    
    public void checkNewProject() {
    	LocalDate currentDate = LocalDate.now();
    	for (Project project : upcomingProjects) {
    		LocalDate applicationOpeningDate = ((Date) managedProject.getApplicationClosingDate()).toLocalDate();
    		if (currentDate == applicationOpeningDate) {
    			managedProject = project;
    			isManagingOfficer = true;
    			return;
    		}
    	}
    }
 
    // TODO: method to check if there are any approved projects in registrationStatusMap. if there are, transfer them to upcomingProjects
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
        if (pastAppliedProjects.contains(project))  {
            return false;
        }

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

        registrationStatusMap.put(project, OfficerRegistrationStatus.PENDING);
        return true;
    }
    
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
    
    public void viewOfficerRegistrationStatus() {
    	registrationStatusMap.forEach((project, status) -> {
    		System.out.printf("Project: %s, Status: %s", project.getProjectName(), status);   	
    	});
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
        if (ApplicationService.processBooking(application, managedProject)) {
        	System.out.println("Booking successful!");
        	return;
        }            
        System.out.println("Booking unsuccessful. Chosen flat type has no more units.");
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