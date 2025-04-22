import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;
/**
 * InputValidator class implements I_InputValidator interface to handle input validation for various user inputs.
 * <p>
 * This class provides methods to validate project names, strings, integers, dates, and officer slots.
 * </p>
 * 
 * @see I_InputValidator
 * @see Project
 */
// Handles Input Validation functions
public class InputValidator implements I_InputValidator{
	
	DateFormat formatter = new SimpleDateFormat("d/M/yyyy");
    Date currentDate = new Date();
    
	/**
	 * Ensures a project name is unique by checking against existing projects
	 * <p>
	 * This method prompts the user for a project name and checks if it is unique among the current projects.
	 * If the name is not unique, it will prompt the user to enter a different name.
	 * </p>
	 * @param currentProjects The list of current projects in the system
	 * @param scanner The scanner object for user input
	 * @param prompt The prompt message for user input
	 * @return The verified unique project name
	 * 
	 */
    // verify unique Project Name
    public String uniqueProjectName(ArrayList<Project> currentProjects, Scanner scanner, String prompt)
    {
    	String verifiedName = null;
    	do {
    		boolean isUnique = true;
    		System.out.print(prompt);
    		String inputName = scanner.nextLine();
    		
    		// Handle stronger check as unique identifier
    		String inputPrjName = inputName.toLowerCase().replaceAll("[\\s\\-_/]+", "");
    		    		
    		for (int i=0; i<currentProjects.size(); i++)
    		{
    			// Handle stronger check as unique identifier    			
    			String projectName = currentProjects.get(i).getProjectName().toLowerCase().replaceAll("[\\s\\-_/]+", "");
    			
    			if(projectName.equals(inputPrjName))
    			{
    				System.out.println("Project Name has been used. Please try again! \n");
    				isUnique = false;    				
    			}
    		}
    		
    		if(inputName != null && !inputName.trim().isEmpty())
    		{
    			if(isUnique)
    			{
    				verifiedName = inputName; // set verified name if unique
    			}
    		}
    		else
    		{
    			System.out.println("Project Name cannot be empty. Please try again! \n");    			
    		}
    		
    	}while(verifiedName == null);

    	return verifiedName;    	
    }
    
    
    /**
	 * Validates and returns a string input from the user.
	 * <p>
	 * This method prompts the user for a string input and checks if it is valid (not empty, no digits).
	 * If the input is invalid, it will prompt the user to enter a different string.
	 * </p>
	 * @param scanner The scanner object for user input
	 * @param prompt The prompt message for user input
	 * @return The verified valid string input
	 */
    // verify valid string (no integer, no spaces)
    public String validateString(Scanner scanner, String prompt)
    {
    	String verifiedString = null;
    	do {
    		System.out.print(prompt);
    		String input = scanner.nextLine();
    		
    		// check for digits
    		String regex = ".*\\d.*";
    		
    		if(input == null || input.trim().isEmpty() || Pattern.matches(regex, input))
    		{
    			System.out.println("Invalid string. Please try again! \n");
    		}
    		else
    		{
    			verifiedString = input;
    		}
    		
    	}while(verifiedString == null);
    	
    	return verifiedString;    	
    }
    
    /**
	 * Validates and returns an integer input (as a string) from the user.
	 * <p>
	 * This method prompts the user for an integer input and checks if it is valid (not empty, non-negative).
	 * If the input is invalid, it will prompt the user to enter a different integer.
	 * </p>
	 * @param scanner The scanner object for user input
	 * @param prompt The prompt message for user input
	 * @return The verified valid integer input (as a string)
	 * 
	 */
    // verify only integer numbers
    public String validateInteger(Scanner scanner, String prompt)
    {
    	String verifiedInt = null;
    	do {
    		System.out.print(prompt);
    		String input = scanner.nextLine();
    		
    		try
    		{
    			int number = Integer.parseInt(input.trim());
    			
    			if (number >= 0)
    			{
    				verifiedInt = input;   		
    			}
    			else
    			{
    				System.out.println("Invalid number. Please try again! \n");
    			}
    			
    		}catch (NumberFormatException e)
    		{
    			System.out.println("Invalid input. Not an integer.\n");    			
    		}    		
    		
    	}while(verifiedInt == null);
    	
    	return verifiedInt;
    }
    
    
    /**
	 * Verifies and returns a valid date input from the user.
	 * <p>
	 * This method prompts the user for a date input and checks if it is valid (in the format dd/mm/yy).
	 * If the input is invalid, it will prompt the user to enter a different date.
	 * * </p>
	 * @param scanner The scanner object for user input
	 * @param prompt The prompt message for user input
	 * @return The verified valid date input (in the format dd/mm/yy)
	 * 
	 */
    // verify correct date input
    public String verifyDate(Scanner scanner, String prompt)
    {
    	String verifiedDate = null;    	
    	do 
		{
			System.out.print(prompt);
			String inputDate = scanner.nextLine();
			
			try {								
				// able to parse to Date format
    			Date newDate = formatter.parse(inputDate);    			
    			verifiedDate = formatter.format(newDate);
    			
    		}catch (ParseException e)
    		{
    			System.out.println("Incorrect date format. Please use the format: dd/mm/yy \n");
    		}

		}while(verifiedDate == null);
    	
    	return verifiedDate;
    }
    
    /**
	 * Verifies valid opening and closing dates for creating new project.
	 * <p>
	 * This method checks if the opening and closing dates are logically valid (not in the past, not overlapping).
	 * If the dates are invalid, it will return false.
	 * </p>
	 * @param openingDate The opening date of the project
	 * @param closingDate The closing date of the project
	 * @return true if the dates are valid, false otherwise
	 * 
	 */
    // Verify valid opening and closing dates for creating new project
    public boolean ValidDateChecker(String openingDate, String closingDate)
    {
    	try {	    		
    		Date convertedOpeningDate = formatter.parse(openingDate);
    		Date convertedClosingDate = formatter.parse(closingDate);    			
	    		
    		// Invalid test cases between before & after
    		if (convertedOpeningDate.after(convertedClosingDate) || 
    			convertedOpeningDate.before(currentDate) && convertedClosingDate.before(currentDate) ||
    			convertedOpeningDate.after(currentDate) && !convertedClosingDate.after(convertedOpeningDate))
    		{
    			// incorrect sequence of dates, project invalid
    			return false;
    		}    		
			
    		// either project ongoing or future project
    		return true;  
    		
		}catch (ParseException e)
		{
			System.err.println("Error parsing the date: " + e.getMessage());
		}    	
    	return false;    	
    }
    
	/**
	 * Verifies valid opening and closing dates for editing existing project.
	 * <p>
	 * This method checks if the opening and closing dates are logically valid (not in the past, not overlapping).
	 * If the dates are invalid, it will return false.
	 * </p>
	 * @param openingDate The opening date of the project
	 * @param closingDate The closing date of the project
	 * @return true if the dates are valid, false otherwise
	 * 
	 */
    
    // Verify valid opening and closing dates for editing existing project
    public boolean ValidDateCheckerforEditing(String openingDate, String closingDate, boolean forOpening)
    {
    	Date convertedOpeningDate;
    	Date convertedClosingDate;
    	
    	try {	    		
    		SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");	
    		
    		// convert string representation of date to 'd/M/yyy' (for editing)
    		if(forOpening)
    		{
    			convertedOpeningDate = formatter.parse(openingDate);
    			convertedClosingDate = inputFormat.parse(closingDate);
    		}
    		else // for closing date
    		{
    			// String to date
    			convertedOpeningDate = inputFormat.parse(openingDate);   
    			convertedClosingDate = formatter.parse(closingDate);
    		}    		    		
    		
    		// Invalid test cases between before & after
    		if (convertedOpeningDate.after(convertedClosingDate) || 
    			convertedOpeningDate.before(currentDate) && convertedClosingDate.before(currentDate) ||
    			convertedOpeningDate.after(currentDate) && !convertedClosingDate.after(convertedOpeningDate))
    		{
    			// incorrect sequence of dates, project invalid
    			return false;
    		}    		
			
    		// either project ongoing or future project
    		return true;  
    		
		}catch (ParseException e)
		{
			System.err.println("Error parsing the date: " + e.getMessage());
		}
    	
    	return false;    	
    }
    
    /**
	 * Verifies valid number of slots for officers.
	 * <p>
	 * This method checks if the number of slots is within the valid range (1 to 10).
	 * If the input is invalid, it will prompt the user to enter a different number of slots.
	 * </p>
	 * @param scanner The scanner object for user input
	 * @param prompt The prompt message for user input
	 * @return The verified valid number of slots (as a string)
	 *
	 */
    // Verify valid number of slots
    public String verifyOfficerSlots(Scanner scanner, String prompt)
    {
    	// Error checking if number within range
		String verifiedMaxSlot = null;
		do {
			String slotsInput = this.validateInteger(scanner, prompt);
			
			int slots = Integer.parseInt(slotsInput);
			
			// minimum 1 slot but maximum 10 slots
			if(slots > 10 || slots < 1)
			{
				System.out.println("Invalid slots. Please try again! \n");
			}
			else
			{
				verifiedMaxSlot = Integer.toString(slots);
			}
			
		}while(verifiedMaxSlot == null);
		
		return verifiedMaxSlot;
    }
}
