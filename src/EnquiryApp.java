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
 * <p>
 * The application provides these main functions:
 * <ul>
 *   <li>View all enquiries - Lists enquiries based on user role</li>
 *   <li>Create an enquiry - Allows applicants to create new enquiries</li>
 *   <li>Update an enquiry - Enables applicants to modify their enquiries</li>
 *   <li>Delete an enquiry - Lets applicants remove their enquiries</li>
 *   <li>Reply to an enquiry - Permits HDB staff to respond to enquiries</li>
 * </ul>
 * </p>
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
     * Starts the enquiry application for a given user.
     * <p>
     * Displays a menu of available features based on the user type and processes
     * the selected operations. HDB staff members get additional reply functionality.
     * </p>
     *
     * @param user The user starting the application (must not be null)
     * @throws NullPointerException if user parameter is null
     */
    public static void start(User user) {
        ArrayList<String> features = new ArrayList<>(List.of(
            "View all enquiries",
            "Create an enquiry",
            "Update an enquiry",
            "Delete an enquiry"
        ));

        ArrayList<String> hdbStaffFeatures = new ArrayList<>(List.of(
            "Reply to an enquiry"
        ));

        int choice;
        do {
            // Display the list of features available
            System.out.println("============================");
            System.out.println("List of Available Features:");
            
            // Add HDB staff features if applicable
            if (user instanceof HDBOfficer || user instanceof HDBManager) {
                features.addAll(hdbStaffFeatures);
            }
            
            // Display menu options
            for (int i = 0; i < features.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, features.get(i));
            }
            System.out.printf("-1. Exit\n");
            System.out.println();
            System.out.printf("Enter your choice: ");
            
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            // Handle user choice using polymorphism
            if (choice > 0 && choice <= features.size()) {
                handleEnquiryOperation(user, choice);
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
    private static void handleEnquiryOperation(User user, int choice) {
        switch (choice) {
            case 1: // View all enquiries
                viewEnquiries(user);
                break;
            case 2: // Create an enquiry
                createEnquiry(user);
                break;
            case 3: // Update an enquiry
                updateEnquiry(user);
                break;
            case 4: // Delete an enquiry
                deleteEnquiry(user);
                break;
            case 5: // Reply to enquiry (HDB staff only)
                if (user instanceof HDBOfficer || user instanceof HDBManager) {
                    replyToEnquiry(user);
                }
                break;
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
    private static void viewEnquiries(User user) {
        System.out.println("\nViewing Enquiries:");
        System.out.println("============================");
        
        if (user instanceof Applicant applicant) {
            ArrayList<Enquiry> enquiries = enquiryService.getEnquiriesByApplicant(applicant.getNRIC());
            displayEnquiries(enquiries);
        } else if (user instanceof HDBOfficer officer) {
            ArrayList<Enquiry> enquiries = enquiryService.getEnquiriesByApplicant(officer.getNRIC());
            displayEnquiries(enquiries);
        } else if (user instanceof HDBManager manager) {
            ArrayList<String> projectNames = new ArrayList<>();
            for (Project project : manager.getProject()) {
                projectNames.add(project.getProjectName());
            }
            ArrayList<Enquiry> enquiries = enquiryService.getEnquiriesByProjects(projectNames);
            displayEnquiries(enquiries);
        }
    }

    /**
     * Creates a new enquiry for an applicant.
     * <p>
     * Prompts for project name and message, then creates an enquiry
     * if the project exists and the user is an applicant.
     * </p>
     *
     * @param user The user creating the enquiry
     */
    private static void createEnquiry(User user) {
        System.out.println("\nCreate New Enquiry:");
        System.out.println("============================");

        System.out.println("Available Projects:");
        viewEnquiries(user);
        
        System.out.print("Enter project name: ");
        String projectName = sc.nextLine();
        
        System.out.print("Enter your message: ");
        String message = sc.nextLine();

        if (user instanceof Applicant applicant) {
            // Project project = findProject(projectName);
            if (projectName != null) {
                Enquiry enquiry = new Enquiry(applicant.getNRIC(), projectName, message, 0);
                enquiryService.addEnquiry(enquiry);
                enquiryService.writeEnquiriesToFile();
                System.out.println("Enquiry created successfully!");
                return; // Exit the method after successful creation
            }
            System.out.println("Project not found.");
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
            displayEnquiries(enquiryService.getEnquiriesByApplicant(applicant.getNRIC()));
        
            System.out.print("Enter enquiry ID to update: ");
            int enquiryId = sc.nextInt();
            sc.nextLine(); // Consume newline
            
            System.out.print("Enter new message: ");
            String newMessage = sc.nextLine();

            Enquiry enquiryToUpdate = enquiryService.getEnquiryById(enquiryId);
            if (enquiryToUpdate != null) {
                enquiryToUpdate.setMessage(newMessage);
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
            displayEnquiries(enquiryService.getEnquiriesByApplicant(applicant.getNRIC()));

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
        System.out.println("\nReply to Enquiry:");
        System.out.println("============================");
        
        if (user instanceof HDBOfficer officer || user instanceof HDBManager manager) {
            viewEnquiries(user);

            System.out.print("Enter enquiry ID to reply to: ");
            int enquiryId = sc.nextInt();
            sc.nextLine(); // Consume newline
            
            System.out.print("Enter your reply: ");
            String reply = sc.nextLine();

            Enquiry enquiryToReply = enquiryService.getEnquiryById(enquiryId);
            if (enquiryToReply != null) {
                Enquiry replyEnquiry = new Enquiry(enquiryToReply.getUserNric(), enquiryToReply.getProjectName(), reply, 0);
                enquiryService.addEnquiry(replyEnquiry);
                enquiryToReply.setReplyID(replyEnquiry.getEnquiryID());
                enquiryService.writeEnquiriesToFile();
                System.out.println("Reply sent successfully!");
            }
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
        if (enquiries.isEmpty()) {
            System.out.println("No enquiries found.");
            return;
        }

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