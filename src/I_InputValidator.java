import java.util.ArrayList;
import java.util.Scanner;

public interface I_InputValidator {
	
	// Ensures a project name is unique by checking against existing projects
	String uniqueProjectName(ArrayList<Project> currentProjects, Scanner scanner, String prompt);
	
	// Validates and returns a string input from the user
	String validateString(Scanner scanner, String prompt);
	
	// Validates and returns an integer input (as a string) from the user
	String validateInteger(Scanner scanner, String prompt);
	
	// Verifies and returns a valid date input from the use
	String verifyDate(Scanner scanner, String prompt);
	
	// Checks if the given opening and closing dates are logically valid
	boolean ValidDateChecker(String openingDate, String closingDate);
	
	// Validates a date specifically for editing purposes
	boolean ValidDateCheckerforEditing(String openingDate, String closingDate, boolean forOpening);
	
	// Verifies and returns a valid number of officer slots input from the user
	String verifyOfficerSlots(Scanner scanner, String prompt);
}
