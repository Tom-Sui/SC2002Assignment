import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;

public class ApplicationService {
	
	public static void updateApplications(Applicant applicant) {
	    ArrayList<Application> applications = applicant.getPastApplications();
	    Iterator<Application> iterator = applications.iterator();

	    while (iterator.hasNext()) {
	        Application app = iterator.next();
	        ApplicationStatus appStatus = app.getApplicationStatus();

	        // Use AND instead of OR here
	        if (appStatus != ApplicationStatus.WITHDRAWN || appStatus != ApplicationStatus.UNSUCCESSFUL) {
	            applicant.setCurrentApplication(app);
	            iterator.remove(); 
	            break; 
	        }
	    }
	}
	
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

	public static void withdrawApplication(Application application) {
		application.setApplicationStatus(ApplicationStatus.WITHDRAWN);
	}

	/**
	 * Generates a comprehensive receipt for a flat booking.
	 * <p>
	 * The receipt includes:
	 * - Applicant's personal details (name, NRIC, age, marital status)
	 * - Flat booking details (flat type, price)
	 * - Project details (name, neighborhood, application period)
	 * </p>
	 *
	 * @param application the application to generate receipt for
	 * @return a formatted string containing the receipt details, or null if the application is invalid
	 */
	public static String generateFlatSelectionReceipt(Application application) {
		if (application == null || application.getApplicationStatus() != ApplicationStatus.BOOKED) {
			System.out.println("No valid booking found for this application.");
			return null;
		}

		Applicant applicant = application.getApplicant();
		Project project = application.getProject();
		FlatType flatType = application.getFlatType();

		StringBuilder receipt = new StringBuilder();
		receipt.append("\n=== FLAT SELECTION RECEIPT ===\n");
		receipt.append("Date: ").append(new Date()).append("\n");
		receipt.append("\nApplicant Details:\n");
		receipt.append("------------------\n");
		receipt.append(String.format("Name: %s\n", applicant.getName()));
		receipt.append(String.format("NRIC: %s\n", applicant.getNRIC()));
		receipt.append(String.format("Age: %d\n", applicant.getAge()));
		receipt.append(String.format("Marital Status: %s\n", applicant.getMaritalStatus()));

		receipt.append("\nFlat Details:\n");
		receipt.append("-------------\n");
		receipt.append(String.format("Flat Type: %s\n", flatType.getFlatTypeName()));
		receipt.append(String.format("Price: $%.2f\n", flatType.getPrice()));

		receipt.append("\nProject Details:\n");
		receipt.append("----------------\n");
		receipt.append(String.format("Project Name: %s\n", project.getProjectName()));
		receipt.append(String.format("Neighborhood: %s\n", project.getNeiborhood()));
		receipt.append(String.format("Application Period: %s to %s\n", 
			project.getApplicationOpeningDate(), 
			project.getApplicationClosingDate()));

		receipt.append("\nBooking Status: " + application.getApplicationStatus() + "\n");
		receipt.append("=========================\n");
		
		return receipt.toString();
	}

	/**
	 * Writes a flat selection receipt to a file.
	 * 
	 * @param application the application to generate receipt for
	 * @return true if the receipt was successfully written to file, false otherwise
	 */
	public static boolean writeReceiptToFile(Application application) {
		try {
			String receiptContent = generateFlatSelectionReceipt(application);
			if (receiptContent == null) {
				return false;
			}
			
			// Create a directory for receipts if it doesn't exist
			File receiptDir = new File("receipts");
			if (!receiptDir.exists()) {
				receiptDir.mkdir();
			}
			
			// Create a unique filename using applicant NRIC and timestamp
			String fileName = "receipts/receipt_" + application.getApplicant().getNRIC() + "_" + 
				System.currentTimeMillis() + ".txt";
			File receiptFile = new File(fileName);
			
			// Write the receipt to the file
			try (PrintWriter writer = new PrintWriter(receiptFile)) {
				writer.println(receiptContent);
				System.out.println("Receipt has been saved to: " + fileName);
				return true;
			}
		} catch (IOException e) {
			System.err.println("Error writing receipt to file: " + e.getMessage());
			return false;
		}
	}
}
