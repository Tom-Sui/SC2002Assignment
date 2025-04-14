import java.util.ArrayList;

public class ApplicationLogic {
	
    public static ArrayList<Application> filterByNotBooked(ArrayList<Application> applicationList) {
        ArrayList<Application> filteredApplications = new ArrayList<>();

        for (Application app : applicationList) {
            if (app.getBookingRequested() == true && app.getIsBooked() == false) {
            	filteredApplications.add(app);
            }
        }
        return filteredApplications;
    }   
    
    
    public static ArrayList<Application> filterByBooked(ArrayList<Application> applicationList) {
        ArrayList<Application> filteredApplications = new ArrayList<>();

        for (Application app : applicationList) {
            if (app.getIsBooked() == true) {
            	filteredApplications.add(app);
            }
        }
        return filteredApplications;
    }   
    
    public static void displayApplications(ArrayList<Application> applicationList) {
        for (int i = 0; i < applicationList.size(); i++) {
            Application application = applicationList.get(i);
            System.out.println("Application ID: " + (i+1));  // index is i
            System.out.println(application.toString());
        }
        System.out.println();
    }

}
