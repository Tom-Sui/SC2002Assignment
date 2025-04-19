import java.util.ArrayList;

public class ApplicationService {
	public static boolean processBooking(Application application, Project project) {
		FlatType chosenFlatType = application.getFlatType();
        ArrayList<FlatType> flatTypes = project.getFlatTypes();
        int remainingUnits = FlatTypeLogic.returnFilteredFlatTypeUnits(flatTypes, chosenFlatType);
        if (remainingUnits >= 1) {
	        application.setIsBooked(true);
	        application.setBookingRequested(false);
	        application.getApplicant().setFlatType(chosenFlatType);
	        application.setApplicationStatus(ApplicationStatus.BOOKED);
	    
	        // Updating units in project
	        FlatTypeLogic.updateFilteredFlatTypeUnits(flatTypes, chosenFlatType);
	        return true;
        }
        return false;
	}
	
	public static void applyProject(Applicant applicant, Project project, FlatType flatType) {
		Application application = new Application(applicant, project, flatType);
		project.addApplication(application);
		applicant.setCurrentApplication(application);
	}
	
	public static void bookFlat(Application application, HDBOfficer officer) {
		application.setBookingRequested(true);
        officer.receiveBookFlatRequest(application);
	}
}
