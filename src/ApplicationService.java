import java.util.ArrayList;

/**
 * This class handles the application process for housing projects
 * <p>
 * It provides methods to apply for a project, book a flat, and process bookings based on the availability of flat types.
 * </p>
 * 
 * @see Application
 * @see Project
 * @see FlatType
 */
public class ApplicationService {
	/**
	 * Processes the booking of a flat for an applicant.
	 * <p>
	 * This method checks if the chosen flat type is available in the project and updates the application status accordingly.
	 * </p>
	 * 
	 * @param application the Application object representing the applicant's request
	 * @param project the Project object containing available flat types
	 * @return true if booking is successful, false otherwise
	 */
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
	/**
	 * Applies for a housing project with a preferred flat type.
	 * <p>
	 * This method creates an Application object and associates it with the applicant and project.
	 * </p>
	 * 
	 * @param applicant the Applicant object representing the person applying
	 * @param project the Project object representing the housing project
	 * @param flatType the preferred FlatType object for the application
	 */
	public static void applyProject(Applicant applicant, Project project, FlatType flatType) {
		Application application = new Application(applicant, project, flatType);
		project.addApplication(application);
		applicant.setCurrentApplication(application);
	}
	/**
	 * Books a flat for the applicant.
	 * <p>
	 * This method sets the booking status of the application to true and notifies the HDB officer.
	 * </p>
	 * 
	 * @param application the Application object representing the applicant's request
	 * @param officer the HDBOfficer object responsible for processing the booking
	 */
	public static void bookFlat(Application application, HDBOfficer officer) {
		application.setBookingRequested(true);
        officer.receiveBookFlatRequest(application);
	}
}
