import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

// Handles Input Validation functions
public class InputValidator implements I_InputValidator{
	
	DateFormat formatter = new SimpleDateFormat("d/M/yyyy");
    Date currentDate = new Date();
        
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
