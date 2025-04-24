import java.util.ArrayList;

/**
 * Provides utility methods for processing and filtering housing applications.
 * <p>
 * This class contains static methods for handling application-related operations
 * such as filtering applications by booking status and displaying application details.
 * </p>
 * 
 * @see Application
 */
public class ApplicationLogic {
    
    /**
     * Filters applications that have requested booking but are not yet approved.
     * 
     * @param applicationList the list of applications to filter
     * @return ArrayList containing only applications with booking requested but not yet approved
     */
    public static ArrayList<Application> filterByNotBooked(ArrayList<Application> applicationList) {
        ArrayList<Application> filteredApplications = new ArrayList<>();

        for (Application app : applicationList) {
            if (app.getBookingRequested() == true && app.getIsBooked() == false) {
                filteredApplications.add(app);
            }
        }
        return filteredApplications;
    }   
    
    /**
     * Filters applications that have been successfully booked and approved.
     * 
     * @param applicationList the list of applications to filter
     * @return ArrayList containing only applications that are booked and approved
     */
    public static ArrayList<Application> filterByBooked(ArrayList<Application> applicationList) {
        ArrayList<Application> filteredApplications = new ArrayList<>();

        for (Application app : applicationList) {
            if (app.getIsBooked() == true) {
                filteredApplications.add(app);
            }
        }
        return filteredApplications;
    }   
    
    /**
     * Displays a formatted list of applications with their details.
     * <p>
     * Prints each application's ID and their details.
     * </p>
     * 
     * @param applicationList the list of applications to display
     */
    public static void displayApplications(ArrayList<Application> applicationList) {
        for (int i = 0; i < applicationList.size(); i++) {
            Application application = applicationList.get(i);
            System.out.println("Application ID: " + (i+1));  // Shows 1-based index
            System.out.println(application.toString());
        }
        System.out.println();
    }


    /**
     * Filters applications that have been applied for in the past.
     * 
     * @param applicationList the list of applications to filter
     * @return ArrayList containing only past applied projects
     */
    public static ArrayList<Project> filterByPastAppliedProjects(ArrayList<Application> applicationList) {
        ArrayList<Project> pastAppliedProjects = new ArrayList<>();
        if (applicationList.size() == 0) return null;
        for (Application application : applicationList) {
            pastAppliedProjects.add(application.getProject());
        }
        return pastAppliedProjects;
    }
    /**
     * Retrieves the Applicant object that matches the given input NRIC.
     * 
     * @param applicantList the list of applicants to search
     * @param NRIC the NRIC of the applicant to search for
     * @return the matching Applicant object, or null if not found
     */
    public static Applicant getApplicant(ArrayList<Applicant> applicantList, String NRIC) {
    	for (Applicant applicant : applicantList) {
    		if (applicant.getNRIC().equals(NRIC)) {
    			return applicant;
    		}
    	}
		return null;
    }
    /**
     * Retrieves the Project object that matches the given input project name.
     * 
     * @param projectList the list of projects to search
     * @param projectName the name of the project to search for
     * @return the matching Project object, or null if not found
     */
    public static Project getProject(ArrayList<Project> projectList, String projectName) {
    	for (Project project : projectList) {
    		if (project.getProjectName().equals(projectName)) {
    			return project;
    		}
    	}
		return null;
    }
    /**
     * Retrieves the FlatType object that matches the given input flat type name.
     * 
     * @param project the project containing the flat types
     * @param inputFlatType the name of the flat type to search for
     * @return the matching FlatType object, or null if not found
     */
    public static FlatType getFlatType(Project project, String inputFlatType) {
        for (FlatType flatType : project.getFlatTypes()) {
            if (flatType.matchesTypeName(inputFlatType)) {
                return flatType;
            }
        }
        return null; // Not found
    }
}
        
    
