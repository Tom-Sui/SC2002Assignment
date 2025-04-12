import java.util.ArrayList;

public class ApplicationLogic {
	
    /*
    * @param filter applications by those that are not booked
    * @return ArrayList<Application>  
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
    
    /*
    * @param filter applications by those that are booked
    * @return ArrayList<Application>  
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
    
    /*
    * @param display application details 
    * @return void 
    */
    
    public static void displayApplications(ArrayList<Application> applicationList) {
        for (int i = 0; i < applicationList.size(); i++) {
            Application application = applicationList.get(i);
            System.out.println("Application ID: " + (i+1));  // index is i
            System.out.println(application.toString());
        }
        System.out.println();
    }

}
