import java.util.ArrayList;
import java.util.Scanner;
/**
 * Interface for handling enquiries related to housing projects.
 * <p>
 * This interface defines the method for replying to an enquiry made by an applicant.
 * </p>
 * 
 * @see Enquiry
 * @see Project
 */
public interface I_InputValidator {
	/**
	 * Ensures a project name is unique by checking against existing projects
	 * 
	 * @param currentProjects list of current projects (must not be null, used to verify project references)
	 * @param scanner scanner object
	 * @param prompt prompt message
	 * @return returns a unique project name input from the user
	 */
	// Ensures a project name is unique by checking against existing projects
	String uniqueProjectName(ArrayList<Project> currentProjects, Scanner scanner, String prompt);
	/**
	 *  Validates and returns a string input from the user
	 * @param scanner scanner object
	 * @param prompt prompt message
	 * @return returns a string input from the user
	 */
	// Validates and returns a string input from the user
	String validateString(Scanner scanner, String prompt);
	/**
	 * Validates and returns an integer input (as a string) from the user
	 * @param scanner scanner object
	 * @param prompt prompt message
	 * @return returns an integer input (as a string) from the user
	 */
	// Validates and returns an integer input (as a string) from the user
	String validateInteger(Scanner scanner, String prompt);
	/**
	 * Verifies and returns a valid date input from the use
	 * @param scanner scanner object
	 * @param prompt prompt message
	 * @return returns a valid date input from the user
	 */
	// Verifies and returns a valid date input from the use
	String verifyDate(Scanner scanner, String prompt);
	/**
	 * @param closingDate closing date
	 * @param openingDate opening date
	 * @return returns true if the given opening and closing dates are logically valid, false otherwise
	 */
	// Checks if the given opening and closing dates are logically valid
	boolean ValidDateChecker(String openingDate, String closingDate);
	/**
	 * Validates a date specifically for editing purposes
	 * @param openingDate opening date
	 * @param closingDate closing date
	 * @param forOpening true if the date is for opening, false if for closing
	 * @return returns true if the date is valid, false otherwise
	 */
	// Validates a date specifically for editing purposes
	boolean ValidDateCheckerforEditing(String openingDate, String closingDate, boolean forOpening);
	/**
	 * Verifies and returns a valid number of officer slots input from the user
	 * @param scanner scanner object
	 * @param prompt prompt message
	 * @return returns a valid number of officer slots input from the user
	 */
	// Verifies and returns a valid number of officer slots input from the user
	String verifyOfficerSlots(Scanner scanner, String prompt);
}
