import java.util.Scanner;

public class ManagerInput {
	
	Scanner scanner = new Scanner(System.in);
	int choice;
	
	public void switchFunction(String userName)
	{
		
		do {
			System.out.println("Here are the actions you can perform:");
			System.out.println("==================================== \n");
			
            System.out.println("1. Create a New BTO Project");
            System.out.println("2. Edit an Existing BTO Project");
            System.out.println("3. Delete a BTO Project");
            System.out.println("4. Toggle Project Visibility");
            System.out.println("5. View All Created Projects");
            System.out.println("6. Filter and View Personal Projects");
            System.out.println("7. View Pending and Approved HDB Officer Registrations");
            System.out.println("8. Approve or Reject HDB Officer Registrations");
            System.out.println("9. Approve or Reject Applicant’s BTO Application");
            System.out.println("10. Approve or Reject Applicant’s Withdrawal Request");
            System.out.println("11. Generate Reports of Applicants");
            System.out.println("12. View Enquiries for ALL Projects");
            System.out.println("13. View and Reply to Enquiries for Your Projects");
            System.out.println("14. Quit \n");
            
            System.out.println("Enter your choice: \n");
            choice = scanner.nextInt();
            
            HDBManager manager = new HDBManager();                  
            
            switch(choice) {
            
            //Create a New BTO Project
            case 1:
            	System.out.println("Enter Project Name: ");
            	String projectName = scanner.nextLine();   
            	
            	System.out.println("Enter Select Neighborhood (e.g., Yishun, Boon Lay): ");
            	String neighborhood = scanner.nextLine(); 
            	
            	System.out.println("Enter the Number of units for Type 1 (2-Room): ");
            	String unitType1 = scanner.nextLine();
            	
            	System.out.println("Enter the Selling price for Type 1 (2-Room): ");
            	String sellPriceType1 = scanner.nextLine();
            	
            	System.out.println("Enter the Number of units for Type 2 (3-Room): ");
            	String unitType2 = scanner.nextLine();
            	
            	System.out.println("Enter the Selling price for Type 2 (3-Room): ");
            	String sellPriceType2 = scanner.nextLine();
            	
            	System.out.println("Set application opening date (dd/mm/yy): ");
            	String openingDate = scanner.nextLine();
            	
            	System.out.println("Set application closing date (dd/mm/yy): ");
            	String endingDate = scanner.nextLine();
            	
            	System.out.println(" Set Available HDB Officer Slots (Max 10): ");
            	String slots = scanner.nextLine();
            	
            	String createProjectString = String.join(",", projectName, neighborhood, "2-Room", unitType1, sellPriceType1, "3-Room", unitType2, sellPriceType2, openingDate, endingDate, userName, slots);
            	
            	manager.createProject(createProjectString);  // WRITE to file function
            	break;                              	
            	
            // Edit an Existing BTO Project
            case 2:
            	System.out.println("Enter Project Name to Edit: ");
            	String selectedProject = scanner.nextLine();  
            	
            	// TO-DO:
            	// LIST OUT ALL PROJECTS
            	// read from excel, loop through projects and find corresponding project, get index
            	// from index, we update the others
            	// **TO COMPLETE..
            	
            	
            	int count = 0;
            	do {
            		System.out.println("Select the Choice: ");
            		System.out.println("1. Update Project Name");
            		System.out.println("2. Update Neighborhood");
            		System.out.println("3. Update Number of Units"); // Add IF statements -> if 2 room / 3 room
            		System.out.println("4. Change Application Opening Date");
            		System.out.println("5. Change Application Closing Date");
            		System.out.println("6. Toggle Visibility (On/Off)");
            		System.out.println("7. Quit");
            		
            		int choice2 = scanner.nextInt();
            		
            		switch(choice2)
            		{
            		
            		// Update Project Name
            		case 1:
            			System.out.println("Enter Updated Project Name: ");
                    	String newProjectName = scanner.nextLine();                                 			
                    	manager.editProject(selectedProject, newProjectName, 0);
                    	break;
                    	
                    // Update Neighborhood
            		case 2:                                        	
                    	System.out.println("Enter Updated Neighbourhood: ");                                     	
                    	String newNeighbourhood = scanner.nextLine();                                 			
                    	manager.editProject(selectedProject, newNeighbourhood, 1);
                    	break;
                    	
                    // Update Number of Units
            		case 3:
            			int unitType;
            			
            			do
            			{
            				System.out.println("Choose which Unit Type to Update: ");
            				System.out.println("1. Type 1 (2-Room): ");
            				System.out.println("2. Type 2 (3-Room): ");
            				unitType = scanner.nextInt();            		 
            				
            				switch(unitType)
            				{
            				// 2 Room 
            				case 1:
            					System.out.printf("Enter Updated Number of Units for Type %d: ", unitType);  
                				String newNoUnits1 = scanner.nextLine(); 
                				
            					manager.editProject(selectedProject, newNoUnits1, 3); 
            					break;
            					
            				// 3 Room
            				case 2:
            					System.out.printf("Enter Updated Number of Units for Type %d: ", unitType);  
                				String newNoUnits2 = scanner.nextLine(); 
                				
            					manager.editProject(selectedProject, newNoUnits2, 6); 
            					break;
            					
            				default:
            					System.out.println("Invalid type. Please try again!");
            					break;
            				}            		             
            				
            			}while (unitType != -1);            				
            			                                			
            			break;
            			
            		// Change Application Opening Date
            		case 4:
            			System.out.println("Enter Updated Application Opening Date (dd/mm/yy): "); // ** NEED VALIDATE CORRECT/WRONG DATE FORMAT 
                    	String updatedOpeningDate = scanner.nextLine();
            			
                    	manager.editProject(selectedProject, updatedOpeningDate, 8); 
            			break;
            			
            		// Change Application Closing Date
            		case 5:
            			System.out.println("Enter Updated Application Closing Date (dd/mm/yy): "); // ** NEED VALIDATE CORRECT/WRONG DATE FORMAT 
                    	String updatedClosingDate = scanner.nextLine();
            			
                    	manager.editProject(selectedProject, updatedClosingDate, 9); 
            			break;
            			
            		// Toggle Visibility (On/Off)
            		case 6:
            			int check = 0;
            			
            			do
            			{
            				System.out.println("Update Toggle Visibility (On/Off): ");            			
            				String toggle = scanner.nextLine();
            				
            				switch(toggle)
            				{
            				case "On":
            					manager.toggleVisibility(selectedProject, true); 
            					check = 1;
            					break;
            					
            				case "Off":
            					manager.toggleVisibility(selectedProject, false);
            					check = 1;
            					break;
            					
            				default:
            					System.out.println("Invalid input. Please try again!");
            					break;
            				}            				
            				
            			}while (check != 1);
          			
            			break;
            			
            		default:
            			System.out.println("Invalid choice. Please try again!");
            			break;
            		}
            		
            	} while (count != 7);
            	
            	break;
            	
            // Delete a BTO Project
            case 3: 
            	System.out.println("Enter Project Name to Delete: \n");
            	String deletedProject = scanner.nextLine();
            	
            	// TO-DO:
            	// LIST OUT ALL PROJECTS
            	// read from excel, loop through projects and find corresponding project, get index
            	// take that index to delete
            	// **TO COMPLETE..
            	
            	int check = 0;
            	
    			do
    			{
    				System.out.println("Confirm Deletion? (Y/N)");
                	String confirm = scanner.nextLine();
    				
    				switch(confirm)
    				{
    				case "Y":
    					manager.deletProject(deletedProject);
    					check = 1;    					
    					break;
    					
    				case "N":
    					check = 1;
    					break;
    					
    				default:
    					System.out.println("Invalid input. Please try again!");
    					break;
    				}            				
    				
    			}while (check != 1);    			
    			  			
            	break;
            	
            // Toggle Project Visibility
            case 4:            	   
            	System.out.println("Enter Project Name to Toggle: ");
            	String toggleProject = scanner.nextLine();  

            	// TO-DO:
            	// FIND THE PROJECT TO TOGGLE
            	// read from excel, loop through projects and find corresponding project, get index
            	// from index, we get the current toggle
            	// **TO COMPLETE..
            	
            	int checkB = 0;
    			
    			do
    			{
    				System.out.println("Update Toggle Visibility (On/Off): ");            			
    				String toggle = scanner.nextLine();
    				
    				switch(toggle)
    				{
    				case "On":
    					manager.toggleVisibility(toggleProject, true); 
    					checkB = 1;
    					break;
    					
    				case "Off":
    					manager.toggleVisibility(toggleProject, false);
    					checkB = 1;
    					break;
    					
    				default:
    					System.out.println("Invalid input. Please try again!");
    					break;
    				}            				
    				
    			}while (checkB != 1);
  			            	
            	break;
            	
            // View All Created Projects
            case 5:            	
            	break;
            	
            // Filter and View Personal Projects
            case 6:            	
            	break;
            	
            // View Pending and Approved HDB Officer Registrations
            case 7:            	
            	break;
            	
            // Approve or Reject HDB Officer Registrations
            case 8:            	
            	break;
            	
            // Approve or Reject Applicant’s BTO Application
            case 9:            	
            	break;
            	
            // Approve or Reject Applicant’s Withdrawal Request
            case 10:            	
            	break;
            	
            // Generate Reports of Applicants
            case 11:            	
            	break;
            
            // View Enquiries for ALL Projects
            case 12:            	
            	break;
            
            //  View and Reply to Enquiries for Your Projects
            case 13:            	
            	break;
            	
            case 14:
            	break;
            
            default:
            	System.out.println("Invalid action. Please try again!");
            	break;            	
            }
            
		}while(choice != 14);
		
		System.out.println("Quit Successful!");
		
		return;
	}
}
