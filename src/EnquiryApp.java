import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Provides a command-line interface for managing enquiries in the HDB system.
 * <p>
 * This class handles all enquiry-related operations including viewing, creating,
 * updating, and deleting enquiries. It supports different user types (Applicants,
 * HDB Officers, and HDB Managers) with role-specific functionality.
 * </p>
 * 
 * The application provides these main functions:
 * <ul>
 *   <li>View all enquiries - Lists enquiries based on user role</li>
 *   <li>Create an enquiry - Allows applicants to create new enquiries</li>
 *   <li>Update an enquiry - Enables applicants to modify their enquiries</li>
 *   <li>Delete an enquiry - Lets applicants remove their enquiries</li>
 *   <li>Reply to an enquiry - Permits HDB staff to respond to enquiries</li>
 * </ul>
 *
 * @see User
 * @see Applicant
 * @see HDBOfficer
 * @see HDBManager
 * @see Enquiry
 * @see EnquiryService
 */
public class EnquiryApp {
    private static final Scanner sc = new Scanner(System.in);
    private static final EnquiryService enquiryService = new EnquiryService();
    private static final String ENQUIRIES_FILE = "Data/enquiries.txt";


    /**
     * Main method to start the enquiry application.
     * <p>
     * This method initializes the application and starts the main menu loop.
     * </p>
     *
     * @param user The user currently logged in
     * @param projectList List of all projects available in the system
     */
    public static void start(User user, ArrayList<Project> projectList) {
        int choice;
        do {
            
            // Display the list of features available
            System.out.println("List of Available Features:");

            if (user instanceof Applicant || (user instanceof HDBOfficer)) {
                System.out.println("1. View all enquiries");
                System.out.println("2. Create an enquiry");
                System.out.println("3. Update an enquiry");
                System.out.println("4. Delete an enquiry");
                System.out.println("5. Reply to an enquiry");
            }



            if ( user instanceof HDBManager) {
                System.out.println("1. View all enquiries");
                System.out.println("5. Reply to an enquiry");
            }
            
            System.out.printf("-1. Exit\n");
            System.out.println();
            System.out.printf("Enter your choice: ");
            
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            // Handle user choice using polymorphism
            if (choice > 0 && choice <= 5) {
                handleEnquiryOperation(user, choice, projectList);
            } else if (choice != -1) {
                System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != -1);
    }

    /**
     * Handles the selected enquiry operation based on user choice.
     * <p>
     * Routes the operation to the appropriate handler method based on the menu selection.
     * </p>
     *
     * @param user The user performing the operation
     * @param choice The menu choice selected by the user
     */
    private static void handleEnquiryOperation(User user, int choice, ArrayList<Project> projectList) {
        switch (choice) {
            case 1: // View all enquiries
                viewEnquiries(user);
                break;
            case 2: // Create an enquiry
                if( user instanceof Applicant) {
                    createEnquiry(user, projectList);
                } else {
                    System.out.println("You do not have permission to create an enquiry.");
                }
                break;
            case 3:
                if (user instanceof Applicant) {
                    updateEnquiry(user);
                } else {
                    System.out.println("You do not have permission to update an enquiry.");
                }
                break;
            case 4:
                if( user instanceof Applicant) {
                    deleteEnquiry(user);
                } else {
                    System.out.println("You do not have permission to delete an enquiry.");
                }
                break;
            case 5: // Reply to an enquiry
                if (user instanceof HDBOfficer || user instanceof HDBManager) {
                    replyToEnquiry(user);
                } else {
                    System.out.println("You do not have permission to reply to enquiries.");
                }
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    /**
     * Displays all enquiries relevant to the user's role.
     * <p>
     * Shows different sets of enquiries based on user type:
     * - Applicants see their own enquiries
     * - HDB Officers see enquiries they can reply to
     * - HDB Managers see all projects enquiries
     * </p>
     *
     * @param user The user viewing the enquiries
     */
    public static void viewEnquiries(User user) {
        System.out.println("Viewing Enquiries:");
        System.out.println("============================");
        if (user instanceof Applicant && !(user instanceof HDBOfficer)) {
            viewEnquiriesAsApplicant((Applicant) user);
        } else if (user instanceof HDBOfficer) {
            viewEnquiriesAsHDBOfficer((HDBOfficer) user);
        } else if (user instanceof HDBManager) {
            viewEnquiriesAsHDBManager((HDBManager) user);
        } else {
            System.out.println("Invalid user type.");
        }
    }
    /**
     * Displays all enquiries relevant to the user's role.
     * <p>
     * Shows different sets of enquiries based on user type:
     * - Applicants see their own enquiries
     * - HDB Officers see enquiries they can reply to
     * - HDB Managers see all enquiries
     * </p>
     *
     * @param user The user viewing the enquiries
     */
    private static boolean viewEnquiriesAsApplicant(Applicant applicant) {
        if (applicant != null) {
            ArrayList<Enquiry> enquiries = enquiryService.getEnquiriesByApplicant(applicant.getNRIC());
            if (enquiries.isEmpty()) {
                System.out.println("No enquiries found.");
                return false;
            }
            displayEnquiries(enquiries);
            return true;
        } 
        return false;
    }

    private static boolean viewEnquiriesAsHDBOfficer(HDBOfficer officer) {
        if (officer != null) {
            ArrayList<Enquiry> enquiries = enquiryService.getEnquiriesByOfficer(officer);
            if (enquiries.isEmpty()) {
                System.out.println("No enquiries found.");
                return false;
            }
            displayEnquiries(enquiries);
            return true;
        }
        return false;
    }

    private static boolean viewEnquiriesAsHDBManager(HDBManager manager) {
        
        if (manager != null) {
            ArrayList<Enquiry> enquiries = enquiryService.viewAllEnquiries();
            if (enquiries.isEmpty()) {
                System.out.println("No enquiries found.");
                return false;
            }
            displayEnquiries(enquiries);
            return true;
        }
        return false;
    }
    

    /**
     * Creates a new enquiry for a user.
     * <p>
     * Shows available projects based on user type:
     * - Applicants see all visible projects
     * - HDB Officers see projects they manage
     * - HDB Managers see all projects
     * </p>
     *
     * @param user The user creating the enquiry
     * @param projectList List of all projects
     */
    private static void createEnquiry(User user, ArrayList<Project> projectList) {
        System.out.println("\nCreate New Enquiry:");
        System.out.println("============================");

        ArrayList<String> projectNames = ProjectLogic.getProjectNames(projectList);

        // Show available projects based on user type
        System.out.println("Available Projects:");
        if (user instanceof Applicant) {
            // Applicants see all visible projects
            ArrayList<Project> applicantProjects = ProjectLogic.getApplicantProjects(projectList, (Applicant) user);
            for (Project project : applicantProjects) {
                System.out.println(project.toString());
            }
        } 
        System.out.print("\nEnter project name: ");
        String projectName = sc.nextLine();
        
        System.out.print("Enter your message: ");
        String message = sc.nextLine();

        if (projectName != null && message != null && projectNames.contains(projectName)) {
            Enquiry enquiry = new Enquiry(user.getNRIC(), projectName, message, 0);
            enquiryService.addEnquiry(enquiry);
            enquiryService.writeEnquiriesToFile();
            System.out.println("Enquiry created successfully!");
        } else {
            System.out.println("Invalid project name or message.");
        }
    }

    /**
     * Updates an existing enquiry.
     * <p>
     * Allows applicants to modify their enquiries by providing a new message.
     * The enquiry must exist and belong to the applicant.
     * </p>
     *
     * @param user The user updating the enquiry
     */
    private static void updateEnquiry(User user) {
        System.out.println("\nUpdate Enquiry:");
        System.out.println("============================");

        if (user instanceof Applicant applicant) {
            if (!viewEnquiriesAsApplicant(applicant)) {
                return;
            }
        
            System.out.print("Enter enquiry ID to update: ");
            int enquiryId = sc.nextInt();
            sc.nextLine(); // Consume newline
            
            System.out.print("Enter new message: ");
            String newMessage = sc.nextLine();

            Enquiry enquiryToUpdate = enquiryService.getEnquiryById(enquiryId);
            if (enquiryToUpdate != null) {
                enquiryToUpdate.setMessage(newMessage);
                enquiryService.updateEnquiry(enquiryToUpdate);
                enquiryService.writeEnquiriesToFile();
                System.out.println("Enquiry updated successfully!");
            }
        }
    }

    /**
     * Deletes an existing enquiry.
     * <p>
     * Allows applicants to remove their enquiries. The enquiry must exist
     * and belong to the applicant.
     * </p>
     *
     * @param user The user deleting the enquiry
     */
    private static void deleteEnquiry(User user) {
        System.out.println("\nDelete Enquiry:");
        System.out.println("============================");
        
        if (user instanceof Applicant applicant) {
            if (!viewEnquiriesAsApplicant(applicant)) {
                return;
            }

            System.out.print("Enter enquiry ID to delete: ");
            int enquiryId = sc.nextInt();
            sc.nextLine(); // Consume newline
            
            Enquiry enquiryToDelete = enquiryService.getEnquiryById(enquiryId);
            if (enquiryToDelete != null) {
                enquiryService.deleteEnquiry(enquiryToDelete);
                enquiryService.writeEnquiriesToFile();
                System.out.println("Enquiry deleted successfully!");
            }
        }
    }

    /**
     * Handles the reply to an enquiry by HDB staff.
     * <p>
     * Allows HDB officers and managers to respond to enquiries with a message.
     * </p>
     *
     * @param user The HDB staff member replying to the enquiry
     */
    private static void replyToEnquiry(User user) {
        
        if (user instanceof HDBOfficer officer) {
            if (!viewEnquiriesAsHDBOfficer(officer)) {
                return;
            }
        } else if (user instanceof HDBManager manager) {
            if (!viewEnquiriesAsHDBManager(manager)) {
                return;
            }
        } else {
            System.out.println("You do not have permission to reply to enquiries.");
            return;
        }
                    
        System.out.print("Enter enquiry ID to reply to: ");
        int enquiryId = sc.nextInt();
        sc.nextLine(); // Consume newline
        
        System.out.print("Enter your reply: ");
        String reply = sc.nextLine();

        Enquiry enquiryToReply = enquiryService.getEnquiryById(enquiryId);
        if (enquiryToReply != null && enquiryToReply.getReplyID() == 0) {
            Enquiry replyEnquiry = new Enquiry(enquiryToReply.getUserNric(), enquiryToReply.getProjectName(), reply, 0);
            enquiryService.addEnquiry(replyEnquiry);
            enquiryToReply.setReplyID(replyEnquiry.getEnquiryID());
            enquiryService.writeEnquiriesToFile();
            System.out.println("Reply sent successfully!");
        } else {
            System.out.println("Enquiry already replied to.");
        }
    }

    /**
     * Displays a formatted list of enquiries.
     * <p>
     * Shows enquiry details including ID, project name, message, and reply (if any).
     * </p>
     *
     * @param enquiries The list of enquiries to display
     */
    private static void displayEnquiries(List<Enquiry> enquiries) {
        for (Enquiry enquiry : enquiries) {
            System.out.println("\nEnquiry ID: " + enquiry.getEnquiryID());
            System.out.println("Project: " + enquiry.getProjectName());
            System.out.println("Message: " + enquiry.getMessage());
            if (enquiry.getReplyID() != 0) {
                System.out.println("Reply: " + enquiry.getReplyID());
            }
            System.out.println("------------------------");
        }
    }
}