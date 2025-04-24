import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
/**
 * EnquiryService.java
 * 
 * This class manages the enquiries related to housing projects.
 * It provides methods to add, delete, update, and view enquiries.
 * It also handles the reading and writing of enquiries to a text file.
 * 
 * @see Enquiry
 * @see EnquiryCreator
 * @see EnquiryService
 */

public class EnquiryService {
    private ArrayList<Enquiry> enquiries = new ArrayList<Enquiry>();
    private HashMap<String, ArrayList<Enquiry>> enquiriesByProject = new HashMap<String, ArrayList<Enquiry>>();
    private HashMap<String, ArrayList<Enquiry>> enquiriesByApplicant = new HashMap<String, ArrayList<Enquiry>>();
    private static final String ENQUIRIES_FILE = "Data/enquiries.txt";
    /**
     * Default constructor for the EnquiryService class.
     * Initializes the enquiries list and reads enquiries from a file.
     */
    // ========== CONSTRUCTOR ==========
    public EnquiryService() {
        try {
            readEnquiriesFromFile();
        } catch (IOException e) {
            System.err.println("Error reading enquiries from file: " + e.getMessage());
        }
    }

    /**
     * Returns a list of all enquiries made by applicants for a specific project.
     * <p>
     * This method filters the enquiries based on the provided project name.
     * </p>
     * 
     * @param projectName the name of the project
     * @return ArrayList of all enquiries for the specified project
     */

    public void refreshEnquiriesByProject(String projectName) {
        enquiriesByProject.clear();
        ArrayList<Enquiry> projectEnquiries = viewEnquiriesByProject(projectName);
        if (!projectEnquiries.isEmpty()) {
            enquiriesByProject.put(projectName, projectEnquiries);
        }
    }

    public void refreshEnquiriesByApplicant(String userNric) {
        enquiriesByApplicant.clear();
        ArrayList<Enquiry> applicantEnquiries = viewEnquiriesByApplicant(userNric);
        if (!applicantEnquiries.isEmpty()) {
            enquiriesByApplicant.put(userNric, applicantEnquiries);
        }
    }
    
    public ArrayList<Enquiry> viewEnquiriesByProject(String projectName) {
        return new ArrayList<>(enquiries.stream()
            .filter(enquiry -> enquiry.getProjectName().equals(projectName))
            .collect(Collectors.toList()));
    }
    /**
     * Returns a list of all enquiries made by applicants for specific projects.
     * <p>
     * This method filters the enquiries based on the provided project names.
     * </p>
     * 
     * @param projectNames the names of the projects
     * @return ArrayList of all enquiries for the specified projects
     */
    public ArrayList<Enquiry> viewEnquiriesByProjects(ArrayList<String> projectNames) {
        return new ArrayList<>(enquiries.stream()
            .filter(enquiry -> projectNames.contains(enquiry.getProjectName()))
            .collect(Collectors.toList()));
    }
    /**
     * Returns a list of all enquiries made by applicants.
     * <p>
     * This method returns the complete list of enquiries without any filtering.
     * </p>
     * 
     * @param userNric the NRIC of the applicant
     * @return ArrayList of all enquiries
     */
    public ArrayList<Enquiry> viewEnquiriesByApplicant(String userNric) {
        return new ArrayList<>(enquiries.stream()
            .filter(enquiry -> enquiry.getUserNric().equals(userNric))
            .collect(Collectors.toList()));
    }

    public ArrayList<Enquiry> viewEnquiriesManagedByOfficer( HDBOfficer officer) {
        ArrayList<Enquiry> enquiriesForOfficer = new ArrayList<>();
        System.out.println("Officer Managed: " + officer.getRegistrationStatusMap());
        ArrayList<String> projects = new ArrayList<>();
        for (Project project : officer.getRegistrationStatusMap().keySet()) {
            projects.add(project.getProjectName());
        }
        for (Enquiry enquiry : enquiries) {
            if (projects.contains(enquiry.getProjectName())) {
                enquiriesForOfficer.add(enquiry);
            }
        }
        return enquiriesForOfficer;
    }

    
    /**
     * Returns a list of all enquiries.
     * <p>
     * This method returns the complete list of enquiries without any filtering.
     * </p>
     * 
     * @return ArrayList of all enquiries
     */

    public ArrayList<Enquiry> viewAllEnquiries() {
        return enquiries;
    } 

    /**
     * Returns a list of all enquiries by projects.
     * <p>
     * This method returns the complete list of enquiries by projects.
     * </p>
     * 
     * @param projectName the name of the project
     * @return ArrayList of all enquiries by projects
     */
    public ArrayList<Enquiry> getEnquiriesByProject(String projectName) {
        refreshEnquiriesByProject(projectName);
        return enquiriesByProject.getOrDefault(projectName, new ArrayList<>());
    }

    /**
     * Returns a list of all enquiries by applicants.
     * <p>
     * This method returns the complete list of enquiries by applicants.
     * </p>
     * 
     * @param userNric the NRIC of the applicant
     * @return ArrayList of all enquiries by applicants
     */

    public ArrayList<Enquiry> getEnquiriesByApplicant(String userNric) {
        refreshEnquiriesByApplicant(userNric);
        return enquiriesByApplicant.getOrDefault(userNric, new ArrayList<>());
    }
    
    /**
     * Returns a list of all enquiries by projects.
     * <p>
     * This method returns the complete list of enquiries by projects.
     * </p>
     * 
     * @param projectNames the names of the projects
     * @return ArrayList of all enquiries by projects
     */


    public ArrayList<Enquiry> getEnquiriesByProjects(ArrayList<String> projectNames) {
        return viewEnquiriesByProjects(projectNames);
    }

    /**
     * Adds a new enquiry to the list.
     * <p>
     * This method checks if the enquiry is null before adding it to the list.
     * </p>
     * 
     * @param enquiry the Enquiry object to add
     * @return true if the addition was successful, false otherwise
     */
    public ArrayList<Enquiry> getEnquiriesByOfficer(HDBOfficer officer) {
        return viewEnquiriesManagedByOfficer(officer);
    }

    public boolean addEnquiry(Enquiry enquiry) {
        if (enquiry == null) {
            System.out.println("Enquiry cannot be null");
            return false;
        }
        enquiries.add(enquiry);
        return true;
    }

    /**
     * Deletes an enquiry from the list.
     * <p>
     * This method removes the specified enquiry from the enquiries list.
     * </p>
     * 
     * @param enquiry the Enquiry object to be deleted
     * @return true if the deletion was successful, false otherwise
     */

    public boolean deleteEnquiry(Enquiry enquiry) {
        if (enquiry == null) {
            System.out.println("Enquiry cannot be null");
            return false;
        }
        enquiries.remove(enquiry);
        return true;
    }

    /**
     * Retrieves an enquiry by its ID.
     * <p>
     * This method searches the enquiries list for an enquiry with the specified ID.
     * </p>
     * 
     * @param enquiryId the ID of the enquiry to retrieve
     * @return the Enquiry object if found, null otherwise
     */

    public Enquiry getEnquiryById(int enquiryId) {
        return enquiries.stream()
            .filter(enquiry -> enquiry.getEnquiryID() == enquiryId)
            .findFirst()
            .orElse(null);
    }

    /**
     * Updates an existing enquiry with new details.
     * <p>
     * This method replaces the existing enquiry in the list with the updated one.
     * </p>
     * 
     * @param enquiry the updated Enquiry object
     * @return true if the update was successful, false otherwise
     */

    public boolean updateEnquiry(Enquiry enquiry) {
        if (enquiry == null) {
            System.out.println("Enquiry cannot be null");
            return false;
        }

        for (int i = 0; i < enquiries.size(); i++) {
            if (enquiries.get(i).getEnquiryID() == enquiry.getEnquiryID()) {
                enquiries.set(i, enquiry);
                return true;
            }
        }

        return false;
    }

    /**
     * Reads enquiries from a text file named "enquiries.txt".
     * <p>
     * Each line in the file should be in the format:
     * enquiryID,projectName,userNric,message,replyID
     * where replyID is 0 if there is no reply, otherwise it's the actual reply ID.
     * </p>
     * 
     * @throws IOException if an error occurs while reading the file
     */
    public void readEnquiriesFromFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(ENQUIRIES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int enquiryID = Integer.parseInt(data[0]);
                String projectName = data[1];
                String userNric = data[2];
                String message = data[3];
                int replyID = Integer.parseInt(data[4]);
                Enquiry enquiry = new Enquiry(userNric, projectName, message, replyID);
                enquiries.add(enquiry);
            }
        }
    }

    /**
     * Writes all enquiries to a text file named "enquiries.txt".
     * <p>
     * Each enquiry is written in the format:
     * enquiryID,projectName,userNric,message,replyID
     * where replyID is 0 if there is no reply, otherwise it's the actual reply ID.
     * </p>
     */
    public void writeEnquiriesToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Data/enquiries.txt"))) {
            for (Enquiry enquiry : enquiries) {
                int replyId = (enquiry.getReplyID() == 0) ? 0 : enquiry.getReplyID();
                writer.printf("%d,%s,%s,%s,%d%n",
                    enquiry.getEnquiryID(),
                    enquiry.getProjectName(),
                    enquiry.getUserNric(),
                    enquiry.getMessage(),
                    replyId);
            }
            System.out.println("Enquiries have been written to enquiries.txt");
        } catch (IOException e) {
            System.err.println("Error writing enquiries to file: " + e.getMessage());
        }
    }
}

