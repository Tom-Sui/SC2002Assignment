import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

// For Manager Interfaces
/**
 * Provides the command-line interface for HDB managers to interact with the system.
 * <p>
 * This class handles all manager operations including project management, application processing,
 * and report generation through a command line interface.
 * </p>
 * 
 */
public class ManagerInput {
	
	Scanner scanner = new Scanner(System.in);
	int choice;	
	
	Init init = new Init();	
	HDBManager manager = ManagerFactory.defaultManager(); 
	/**
     * Displays and processes the manager menu system.
     * <p>
     * This method presents a comprehensive menu system and handles all manager operations
     * including project creation, editing, deletion, and various approval workflows.
     * </p>
     *
	 * @param name The name of the manager
	 * @param managerID The ID of the manager
     * @param projects List of all projects in the system
     * @param hdbManagers List of all HDB managers
     * @param hdbOfficers List of all HDB officers
     * @param applicants List of all applicants
     *
     * The system provides these functions:
     * <ol start="0">
     *   <li>View Current Active Project</li>
     *   <li>Create a New BTO Project</li>
     *   <li>Edit an Existing BTO Project</li>
     *   <li>Delete a BTO Project</li>
     *   <li>Toggle Project Visibility</li>
     *   <li>View All Created Projects</li>
     *   <li>View Personal Projects</li>
     *   <li>View Officer Registrations</li>
     *   <li>Approve/Reject Officer Registrations</li>
     *   <li>Approve/Reject BTO Applications</li>
     *   <li>Approve/Reject Withdrawal Requests</li>
     *   <li>Generate Applicant Reports</li>
     *   <li>View All Enquiries</li>
     *   <li>View/Reply to Project Enquiries</li>
     *   <li>Quit</li>
     * </ol>
     *
     * @throws IllegalArgumentException if invalid inputs are provided
     * @throws NullPointerException if required parameters are null
     *
     * @see HDBManager list of all HDB managers
     * @see Project list of all projects
     * @see Applicant list of all applicants
     */
	public void switchFunction(User name, String managerID, ArrayList<Project> projects, ArrayList<HDBManager> hdbManagers, ArrayList<HDBOfficer> hdbOfficers, ArrayList<Applicant> applicants)
	{
		do {
			try
			{
				System.out.println("\nHere are the actions you can perform:");
				System.out.println("==================================== \n");
				
				System.out.println("0. View Current Active Project");
	            System.out.println("1. Create a New BTO Project");
	            System.out.println("2. Edit an Existing BTO Project");
	            System.out.println("3. Delete a BTO Project");
	            System.out.println("4. Toggle Project Visibility");
	            System.out.println("5. View All Created Projects");
	            System.out.println("6. View Personal Projects");
	            System.out.println("7. View Pending and Approved HDB Officer Registrations");
	            System.out.println("8. Approve or Reject HDB Officer Registrations");
	            System.out.println("9. Approve or Reject Applicant’s BTO Application");
	            System.out.println("10. Approve or Reject Applicant’s Withdrawal Request");
	            System.out.println("11. Generate Reports of Applicants");
	            System.out.println("12. View Enquiries for ALL Projects");
	            System.out.println("13. Reply to Enquiries for Your Projects");
	            System.out.println("14. Quit \n");
	            
	            System.out.println("Enter your choice:");
	            choice = scanner.nextInt();
	            scanner.nextLine();
	             
	            projects = init.LoadProjectInfo(hdbManagers, hdbOfficers); // Restore modified data, if any
	            manager.PastDateChecker(projects);  // Verify past projects and ensure visibility OFF
	            manager.updateMaritalStatus(projects); // Update marital status to check during runtime
	            
	            switch(choice) {
	            
	            // View Current Active project
	            case 0:
	            	manager.currentActiveProject(projects, managerID, true);            	
	            	break;
	            
	            //Create a New BTO Project
	            case 1:
	            	
	            	// check if any active projects
	            	if (manager.currentActiveProject(projects, managerID, false) == null)
	            	{
	            		// Error checking for project Name (Ensure no duplicated project name)
	            		String projectName = manager.uniqueProjectName(projects, scanner, "Enter a unique project name: \n");

	            		// Error checking for string
	            		String neighborhood = manager.validateString(scanner, "Enter Select Neighborhood (e.g., Yishun, Boon Lay): \n"); 
	            		
	            		// Error checking for valid integer
	            		String unitType1 = manager.validateInteger(scanner, "Enter the Number of units for 2-Room: \n");            		            		
	            		String sellPriceType1 = manager.validateInteger(scanner, "Enter the Selling price for 2-Room: \n");     		            		
	            		String unitType2 = manager.validateInteger(scanner, "Enter the Number of units for 3-Room: \n");
	            		String sellPriceType2 = manager.validateInteger(scanner, "Enter the Selling price for 3-Room: \n");
	            		            		
	            		// check valid dates
	            		boolean verifiedDates = false;
	            		String verifiedOpeningDate;
	            		String verifiedClosingDate;
	            		do
	            		{
	            			// Error checking for correct Date format
	            			verifiedOpeningDate = manager.verifyDate(scanner, "Set application opening date (dd/mm/yy): \n");
	            			// Error checking for correct Date format
	            			verifiedClosingDate = manager.verifyDate(scanner, "Set application closing date (dd/mm/yy): \n");            		
	            			
	            			boolean valid = manager.ValidDateChecker(verifiedOpeningDate, verifiedClosingDate);
	            			
	            			if (valid)
	            			{
	            				verifiedDates = true;
	            			}
	            			else
	            			{
	            				System.out.println("Invalid dates. Please try again!\n");
	            			}
	            			
	            		}while(verifiedDates == false);
	            		
	            		// Error checking for MAX slot
	            		String verifiedMaxSlot = manager.verifyOfficerSlots(scanner, "Set Available HDB Officer Slots (Max 10): \n");

	            		// Error checking for visibility (specific type case)
	            		String verifiedVisibility = null;            		
	            		do {
	            			System.out.println("Set visibility of project (on/off): ");
	            			String inputV = scanner.nextLine();                		
	            			
	            			switch(inputV)
	            			{
	            			case "on":
	            				verifiedVisibility = "true";
	            				break;
	            				
	            			case "off":
	            				verifiedVisibility = "false";
	            				break;
	            				
	            			default:
	            				System.out.println("Invalid input. Please try again! \n");
	            				break;
	            			}
	            			
	            		}while(verifiedVisibility == null);
	            		
	            		
	            		System.out.println("\nNOTE: You will be assigned to this created project.\n");
	            		
	            		// Convert inputs to a string to update the txt
	            		String createProjectString = String.join(",", projectName, neighborhood, "2-Room", unitType1, sellPriceType1, "3-Room", unitType2, sellPriceType2, verifiedOpeningDate, verifiedClosingDate, managerID, verifiedMaxSlot," ", verifiedVisibility);
	            		System.out.printf("Successfully Created! \n %s\n", createProjectString);

						//Load the created project to the list      
						projects = manager.createProject(createProjectString, hdbManagers, hdbOfficers); // WRITE to createProject function
	            	}
	            	else
	            	{
	            		System.out.println("You currently have an active project! Not allowed to create new project.");
	            	}
	            	
	            	break;                              	
	            	
	            // Edit an Existing BTO Project
	            case 2:
	            	System.out.println("Below are the existing projects under you");
	            	System.out.println("========================================== \n");
	            	
	            	// call all the project only the manager is handling
	            	manager.listSpecificProjects(projects, managerID);
	            	
	            	int checkE = 0;
	            	boolean found = false;
	            	
	            	// check for valid project to update AND within Application period in order to edit
	            	do 
	            	{            		
	            		System.out.println("Enter Project Name to Edit: ");
	            		String selectedProject = scanner.nextLine();    
	            		
	            		// Check which project to edit
	            		for (int i=0; i<projects.size(); i++)
	            		{
	            			if (projects.get(i).getHDBManager().getNRIC().equals(managerID) && projects.get(i).getProjectName().equals(selectedProject)) // found the projectID to update
	            			{            				
	            				found = true;    
	            				int choice2 = 0;
	            				
	            				// check which options to update 
	            				do {
	            					try
	            					{
	            						System.out.println("\n=== List of Options to Update ===");
		                        		System.out.println("0. Update Project Name");
		                        		System.out.println("1. Update Neighborhood");
		                        		System.out.println("2. Update Price");
		                        		System.out.println("3. Update Number of Units");
		                        		System.out.println("4. Change Application Opening Date");
		                        		System.out.println("5. Change Application Closing Date");
		                        		System.out.println("6. Toggle Visibility (On/Off)");
		                        		System.out.println("7. Update Number of Officer Slots");
		                        		System.out.println("8. Quit\n");
		                        		
		                        		System.out.println("Select the Choice: ");
		                        		choice2 = scanner.nextInt();
		                        		
		                        		scanner.nextLine();
		                        		
		                        		switch(choice2)
		                        		{
		                        		// Update Project Name
		                        		case 0:
		                        			String newProjectName = manager.uniqueProjectName(projects, scanner, "Enter a unique project name to update: \n");
		                        			                           	
		                                	manager.editProject(selectedProject, newProjectName, "0", projects, i);                                	
		                                	selectedProject = newProjectName;
		                                	break;
		                                	
		                                // Update Neighborhood
		                        		case 1:                                  	
		                                	String newNeighbourhood = manager.validateString(scanner, "Enter updated Neighbourhood: \n");      
		                                	
		                                	manager.editProject(selectedProject, newNeighbourhood, "1", projects, i);
		                                	break;
		                                	
		                                // Update price
		                        		case 2:
		                        			int flatType;                        			
		                        			do
		                        			{
		                        				System.out.println("1. 2-Room ");
		                        				System.out.println("2. 3-Room ");
		                        				System.out.println("Choose which Flat Type to Update: ");
		                        				flatType = scanner.nextInt();            		 
		                        				
		                        				scanner.nextLine();
		                        				
		                        				switch(flatType)
		                        				{
		                        				// 2 Room 
		                        				case 1:
		                        					// validate integer
		                        					String sellPriceType1 = manager.validateInteger(scanner, "Enter the updated selling price for 2-Room: \n");
		                        					                            				
		                            				String currentRoom1 = "2-Room,";                            				
		                            				currentRoom1 += sellPriceType1; // identifier
		                            				
		                            				manager.editProject(selectedProject, currentRoom1, "2", projects, i);
		                            				flatType = -1;
		                        					break;
		                        					
		                        				// 3 Room
		                        				case 2:
		                        					String sellPriceType2 = manager.validateInteger(scanner, "Enter the updated selling price for 3-Room: \n");
		                        					                            				
		                            				String currentRoom2 = "3-Room,";                            				
		                            				currentRoom2 += sellPriceType2; // identifier
		                            				
		                            				manager.editProject(selectedProject, currentRoom2, "2", projects, i);
		                            				flatType = -1;
		                        					break;
		                        					
		                        				default:
		                        					System.out.println("Invalid type. Please try again!");
		                        					break;
		                        				}            		             
		                        				
		                        			}while (flatType != -1);          				                    			                      			
		                        			break;
		                                	
		                                // Update Number of Units
		                        		case 3:
		                        			int unitType;                        			
		                        			do
		                        			{
		                        				System.out.println("1. 2-Room ");
		                        				System.out.println("2. 3-Room ");
		                        				System.out.println("Choose which Flat Type to Update: ");
		                        				unitType = scanner.nextInt();            		 
		                        				
		                        				scanner.nextLine();
		                        				
		                        				switch(unitType)
		                        				{
		                        				// 2 Room 
		                        				case 1:
		                        					String unitType1 = manager.validateInteger(scanner, "Enter updated number of units for 2-Room: \n");
		                        					                            				
		                            				String setRoom1 = "2-Room,";                            				
		                            				setRoom1 += unitType1; // identifier
		                            				
		                            				manager.editProject(selectedProject, setRoom1, "3", projects, i);
		                            				unitType = -1;
		                        					break;
		                        					
		                        				// 3 Room
		                        				case 2:
		                        					String unitType2 = manager.validateInteger(scanner, "Enter updated number of units for 3-Room: \n");
		                        					                            				
		                            				String setRoom2 = "3-Room,";                            				
		                            				setRoom2 += unitType2; // identifier
		                            				
		                            				manager.editProject(selectedProject, setRoom2, "3", projects, i);
		                            				unitType = -1;
		                        					break;
		                        					
		                        				default:
		                        					System.out.println("Invalid type. Please try again!");
		                        					break;
		                        				}            		             
		                        				
		                        			}while (unitType != -1);          				                    			
		                        			break;
		                        			
		                        		// Change Application Opening Date
		                        		case 4:  
		                        			// check for past projects (if yes, no updating application date)
		                        			if(manager.PastDateCheckerProject(projects, selectedProject))
		                        			{
		                        				System.out.println("Not allowed to change application period for past projects.");
		                        			}
		                        			else
		                        			{ 
		                        				// check valid dates
		                        				boolean verifiedOpeningDates = false;                            		
		                        				String updatedOpeningDate;                        			
		                        				do
		                        				{                            			         		
		                        					updatedOpeningDate = manager.verifyDate(scanner, "Update application opening date (dd/mm/yy): \n");
		                        					
		                        					boolean valid = manager.ValidDateCheckerforEditing(updatedOpeningDate, projects.get(i).getApplicationClosingDate().toString(), true);
		                        					
		                        					if (valid)
		                        					{
		                        						verifiedOpeningDates = true;
		                        						manager.editProject(selectedProject, updatedOpeningDate, "4", projects, i);
		                        					}
		                        					else
		                        					{
		                        						System.out.println("Invalid date. Please try again!\n");
		                        					}
		                        					
		                        				}while(verifiedOpeningDates == false);                          		
		                        			}                        			
		                        			break;
		                        			
		                        		// Change Application Closing Date
		                        		case 5:
		                        			// check for past projects (if yes, no updating application date)
		                        			if(manager.PastDateCheckerProject(projects, selectedProject))
		                        			{
		                        				System.out.println("Not allowed to change application period for past projects.");
		                        			}
		                        			else
		                        			{
		                        				// check valid dates
		                        				boolean verifiedClosingDates = false;                            		
		                        				String updatedClosingDate;                        			
		                        				do
		                        				{                            			         		
		                        					updatedClosingDate = manager.verifyDate(scanner, "Update application closing date (dd/mm/yy): \n");
		                        					
		                        					boolean valid = manager.ValidDateCheckerforEditing(projects.get(i).getApplicationOpeningDate().toString(), updatedClosingDate, false);
		                        					
		                        					if (valid)
		                        					{
		                        						verifiedClosingDates = true;
		                        					}
		                        					else
		                        					{
		                        						System.out.println("Invalid date. Please try again!\n");
		                        					}
		                        					
		                        				}while(verifiedClosingDates == false); 
		                        				
		                        				manager.editProject(selectedProject, updatedClosingDate, "5", projects, i);
		                        			}
		                        			break;
		                        			
		                        		// Toggle Visibility (On/Off)
		                        		case 6:
		                        			// check for past projects (if yes, no updating visibility)
		                        			if(manager.PastDateCheckerProject(projects, selectedProject))
		                        			{
		                        				System.out.println("Visibility for past projects are always OFF. Not allowed to change.");
		                        			}
		                        			else
		                        			{
		                        				// check if any active projects
		                        				if (manager.currentActiveProject(projects, managerID, false) == null) // no active project
		                        				{
		                        					// auto toggling to On/Off
		                        					if(projects.get(i).getVisibility())
		                        					{
		                        						// true = ON
		                        						System.out.printf("Current Project Visibility: '%s' -- ON\n", selectedProject);
		                        					}
		                        					else
		                        					{
		                        						// false = OFF
		                        						System.out.printf("Current Project Visibility '%s' -- OFF\n", selectedProject);
		                        					}                       			
		                        					manager.toggleVisibility(projects, projects.get(i).getProjectName(), managerID);
		                        				}
		                        				else
		                        				{
		                        					System.out.println("Only 1 active project allowed.\n");
		                        				}
		                        			}                        			
		                        			break;
		                        			
		                        		// update officer slots
		                        		case 7:
		                        			if(manager.PastDateCheckerProject(projects, selectedProject))
		                        			{
		                        				System.out.println("Not allowed to update officer slots for past projects.");
		                        			}
		                        			else
		                        			{
		                        				String verifiedMaxSlot = manager.verifyOfficerSlots(scanner, "Set Available HDB Officer Slots (Max 10): \n");                        				
		                        				manager.editProject(selectedProject, verifiedMaxSlot, "7", projects, i);                       			
		                        			}
		                        			break;
		                        			
		                        		case 8:
		                        			break;
		                        			
		                        		default:
		                        			System.out.println("Invalid choice. Please try again!\n");
		                        			break;                        		
		                        		}
	            						
	            					}catch (InputMismatchException e)
	            					{
	            						System.out.println("Invalid input. Please enter a valid integer choice.");
	            		                scanner.next(); // Clear invalid input
	            					}	

	            				}while(choice2 != 8);
	            				checkE = 1; // exit to manager main page
	            				break; // break out of for loop
	            			}            		
	            		}           		
	            	
	            		// Project name not found, loop again
	            		if (found == false)
	            		{
	            			System.out.println("Invalid input. Project Name not found.\n");           	
	            		}
	            		
	            	} while (checkE != 1);  //checkE == 1 when no more update to project
	            	break;            	
	            	
	            // Delete a BTO Project
	            case 3: 
	            	System.out.println("Below are the existing projects you can delete:");
	            	System.out.println("========================================== \n");
	            	
	            	manager.listSpecificProjects(projects, managerID);
	            	
	            	System.out.println("Enter Project Name to Delete: ");
	            	String deletedProject = scanner.nextLine();
	            	
	            	// check for valid Project
	            	Project currentProj = manager.returnProject(projects, deletedProject);
	            	
	            	if (currentProj != null)
	            	{
	            		int checkD = 0;
	            		do
	            		{
	            			// Confirmation of deletion check
	            			System.out.printf("Confirm Deletion of Project: '%s'? (Y/N): ", deletedProject);
	            			String confirm = scanner.nextLine();
	            			
	            			switch(confirm)
	            			{
	            			case "Y":
	            				manager.deletProject(currentProj);
	            				checkD = 1;    					
	            				break;
	            				
	            			case "N":
	            				System.out.printf("Cancelled deletion. Project: '%s' not removed.\n", deletedProject);
	            				checkD = 1;
	            				break;
	            				
	            			default:
	            				System.out.println("Invalid input. Please try again!\n");
	            				break;
	            			}            				
	            			
	            		}while (checkD != 1);    	
	            	}
	            	else
	            	{
	            		System.out.println("Invalid project to delete. Please try again!\n");
	            	}     				
	    			  			
	            	break;
	            	
	            // Toggle Project Visibility
	            case 4: 
	            	
	            	// call all the project only the manager is handling
	            	manager.listSpecificProjects(projects, managerID);            	
	            	
	            	System.out.println("Enter Project Name to Toggle: ");
	            	String toggleProject = scanner.nextLine();              	
	            	
	            	// check for valid Project
	            	Project checkTogglePrj = manager.returnProject(projects, toggleProject);
	            	
	            	if(checkTogglePrj != null)
	            	{
	            		// check for past projects (if yes, no updating visibility)
	        			if(manager.PastDateCheckerProject(projects, toggleProject))
	        			{
	        				System.out.println("Visibility for past projects are always OFF. Not allowed to change.");
	        			}       
	        			else
	        			{
	        				manager.toggleVisibility(projects, toggleProject, managerID);         		
	        			}
	            	}
	            	else
	            	{
	            		System.out.println("Invalid project. Please try again!\n");
	            	}            	
	            	break;
	            	
	            // View All Created Projects by all managers
	            case 5:            	
	            	manager.listAllExistingProjects(projects);
	            	break;
	            	
	            // Filter and View Personal Projects created by the manager only
	            case 6:            	
	            	manager.listSpecificProjects(projects, managerID);
	            	break;
	            	
	            // View Pending and Approved HDB Officer Registrations
	            case 7:            	
	            	System.out.println("_________Pending Officer Registration______");
	            	manager.viewOfficerRegistrationList();
	            	
	            	//Might need to create a txt file for officer registration 
	            	break;
	            	
	            // Approve or Reject HDB Officer Registrations
	            case 8:           
	            	manager.approveOrRejectOfficerRegistration(projects, applicants, hdbOfficers, managerID);
	            	break;
	            	
	            // Approve or Reject Applicant’s BTO Application
	            case 9:
	            	manager.approveOrRejectApplication(projects, applicants, managerID);
	            	break;
	            	
	            // Approve or Reject Applicant’s Withdrawal Request
	            case 10:       
	            	manager.approveWithdrawal(projects, applicants);
	            	break;
	            	
	            // Generate Reports of Applicants
	            case 11:  
	            	String maritalStatusFilter = null;
	            	Integer minAge = null, maxAge = null;
	            	int choice3;
	            	
	            	do {
	            		System.out.println("\n=== Report Filter Menu ===");
	            		System.out.println("1. Filter by Marital Status");
	            		System.out.println("2. Filter by Age Range");
	            		System.out.println("3. Filter by Marital Status and Age Range");
	            		System.out.println("4. Show All Applicants");
	            	    System.out.println("5. Exit");
	            	    
	            	    choice3 = scanner.nextInt();
	            	    scanner.nextLine();
	            	    
	            	    switch(choice3) {
	            	    	//Filter by marital status
	            	    	case 1:
	            	    		
	            	    		System.out.print("Enter marital status(SINGLE/MARRIED): ");
	            	    		maritalStatusFilter = scanner.nextLine().toUpperCase();
	            	    		
	            	    		if(maritalStatusFilter.equals("SINGLE") || maritalStatusFilter.equals("MARRIED")) {
	            	    			manager.generateApplicantReport(applicants, maritalStatusFilter,null,null);
	            	    			break;    
	            	    		} else {
	            	    			System.out.print("Invalid Marital Status");
	            	    			break;
	            	    		}
	            	    		
	            	    		
	            	          
	            	        //Filter by age range    
	            	    	case 2:
	            	    		System.out.print("Enter min age: ");
	            	            minAge = scanner.nextInt();
	            	            System.out.print("Enter max age: ");
	            	            maxAge = scanner.nextInt();
	            	            scanner.nextLine(); // consume newline
	            	            manager.generateApplicantReport(applicants, null, minAge, maxAge);
	            	            break;
	            	        
	            	    	//Filter by marital status and age range	
	            	    	case 3:
	            	    		System.out.print("Enter marital status (e.g. SINGLE, MARRIED): ");
	            	            maritalStatusFilter = scanner.nextLine().toUpperCase();
	            	            if(maritalStatusFilter.equals("SINGLE") || maritalStatusFilter.equals("MARRIED")) {
	            	            	System.out.print("Enter min age: ");
	                	            minAge = scanner.nextInt();
	                	            System.out.print("Enter max age: ");
	                	            maxAge = scanner.nextInt();
	                	            scanner.nextLine(); // consume newline
	                	            manager.generateApplicantReport(applicants, maritalStatusFilter, minAge, maxAge);
	                	            break;    
	                	    		} else {
	                	    			System.out.print("Invalid Marital Status");
	                	    			break;
	                	    		}
	  
	            	    	//Show all	
	            	    	case 4:
	            	    		manager.generateApplicantReport(applicants, null, null, null);
	            	    		break;
	            	    		
	            	    	case 5:            	    		
	            	    		break;
	            	    		
	            	    	default:
	            	    		System.out.println("Invalid option. Please try again!");
	            	    }
	            	    
	            	} while(choice3 != 5);
	            	break;
	            
	            // View Enquiries for ALL Projects
	            case 12:
	            	manager.viewEnquiries(projects);
	            	break;
	            
	            //  View and Reply to Enquiries for Your Projects
	            case 13:        
	            	EnquiryApp.start(name);
	            	break;
	            	
	            case 14:
	            	break;
	            
	            default:
	            	System.out.println("Invalid action. Please try again!");
	            	break;            	
	            }
				
			}catch (InputMismatchException e)
			{
				System.out.println("Invalid input. Please enter a valid integer choice.");
                scanner.next(); // Clear invalid input
			}			

		}while(choice != 14);
		
		System.out.println("Quit Successful!\n");
		
		return;
	}
}
