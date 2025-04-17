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


    /*
     * Filters applications that have been applied to past projects.
     * 
     * @param applicationList the list of applications to filter
     * @return ArrayList containing only Projects that have been applied to 
     */
    public static ArrayList<Project> filterByPastAppliedProjects(ArrayList<Application> applicationList) {
        ArrayList<Project> pastAppliedProjects = new ArrayList<>();
        for (Application application : applicationList) {
            pastAppliedProjects.add(application.getProject());
        }
        return pastAppliedProjects;
    }
}