import java.util.ArrayList;
import java.util.Scanner;

/**
 * This interface defines methods for validating user inputs in a housing project application system.
 * It includes methods for ensuring unique project names, validating strings and integers, checking dates,
 * and verifying officer slots.
 * 
 * @see Project
 */
public interface I_InputValidator {
	
	// Ensures a project name is unique by checking against existing projects
	/**
	 * Ensures a project name is unique by checking against existing projects.
	 * @param currentProjects List of current projects in the system
	 * @param scanner Scanner object for user input 
	 * @param prompt Prompt message
	 * @return Unique project name
	 */
	String uniqueProjectName(ArrayList<Project> currentProjects, Scanner scanner, String prompt);

	// Validates and returns a string input from the user
	/**
	 * Validates and returns a string input from the user.
	 * @param scanner Scanner object for user input 
	 * @param prompt Prompt message
	 * @return String representation of the input
	 */
	String validateString(Scanner scanner, String prompt);

	// Validates and returns an integer input (as a string) from the user
	/**
	 * Validates and returns an integer input (as a string) from the user.
	 * @param scanner Scanner object for user input 
	 * @param prompt Prompt message
	 * @return String representation of the integer
	 */
	String validateInteger(Scanner scanner, String prompt);

	// Verifies and returns a valid date input from the user
	/**
	 * Verifies and returns a valid date input from the user.
	 * @param scanner Scanner object for user input 
	 * @param prompt Prompt message
	 * @return String representation of the date
	 */
	String verifyDate(Scanner scanner, String prompt);

	// Checks if the given opening and closing dates are logically valid
	/**
	 * Validates the opening and closing dates for a project.
	 * @param openingDate opening date of the project
	 * @param closingDate closing date of the project
	 * @return return true if the date is valid, false otherwise
	 */
	boolean ValidDateChecker(String openingDate, String closingDate);
	
	// Validates a date specifically for editing purposes
	/**
	 * Validates the opening and closing dates for a project during editing.
	 * @param openingDate opening date of the project
	 * @param closingDate closing date of the project
	 * @param forOpening true if checking for opening date, false for closing date
	 * @return return true if the date is valid, false otherwise
	 */
	boolean ValidDateCheckerforEditing(String openingDate, String closingDate, boolean forOpening);
	
	// Verifies and returns a valid number of officer slots input from the user
	/**
	 * Verifies the number of officer slots available for a project.
	 * 
	 * @param scanner Scanner object for user input 
	 * @param prompt Prompt message
	 * @return String representation of the number of officer slots
	 */
	String verifyOfficerSlots(Scanner scanner, String prompt);
}
