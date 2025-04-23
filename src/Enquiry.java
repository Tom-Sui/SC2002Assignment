/**
 * Enquiry.java
 * 
 * This class represents an enquiry made by an applicant regarding a housing project.
 * It contains details such as the applicant's NRIC, project name, message content, and a reply ID.
 * 
 * The class provides methods to set and get these details, as well as to check if the enquiry has a reply.
 * 
 */
public class Enquiry {
    private static int enquiryID = 1;
    private String userNric; 
    private String projectName;
    private String message;
    private int replyID;

    /**
     * Default constructor for the Enquiry class.
     * Initializes the enquiry ID, user NRIC, project name, message, and reply ID.
     */
    // ========== CONSTRUCTOR ==========
    public Enquiry() {
        enquiryID = enquiryID++;
        this.userNric = "";
        this.projectName = "";
        this.message = "";
        this.replyID = 0;
    }

    /**
     * Constructs an Enquiry object with the specified applicant, project, and message.
     * 
     * @param userNric the Applicant making the enquiry
     * @param projectName the Project being enquired about
     * @param message the enquiry message content
     */
    public Enquiry(String userNric, String projectName, String message) {
        enquiryID = enquiryID++;
        this.userNric = userNric;
        this.projectName = projectName;
        this.message = message;
        this.replyID = 0;
    }

    /**
     * Constructs an Enquiry object with the specified applicant, project, message, and reply ID.
     * 
     * @param userNric the Applicant making the enquiry
     * @param projectName the Project being enquired about
     * @param message the enquiry message content
     * @param replyID the ID of the reply to this enquiry
     */
    public Enquiry(String userNric, String projectName, String message, int replyID) {
        enquiryID = enquiryID++;
        this.userNric = userNric;
        this.projectName = projectName;
        this.message = message;
        this.replyID = replyID;
    }

    /**
     * Sets the official reply to this enquiry.
     * 
     * @param replyID the response text
     */
    public void setReplyID(int replyID) {
        if (replyID == 0) {
            System.out.println("Reply ID cannot be 0");
            return;
        }
        this.replyID = replyID;
    }

    // ========== GETTER METHODS ==========
    
    /**
     * Gets the enquiry's unique identifier.
     * 
     * @return the enquiry ID
     */
    public int getEnquiryID() {
        return enquiryID;
    }
    
    /**
     * Gets the applicant who made this enquiry.
     * 
     * @return the user nric
     */
    public String getUserNric() {
        return this.userNric;
    }
    
    /**
     * Gets the project being enquired about.
     * 
     * @return the project name
     */
    public String getProjectName() {
        return this.projectName;
    }
    
    /**
     * Gets the enquiry message content.
     * 
     * @return the enquiry text
     */
    public String getMessage() {
        return this.message;
    }
    
    /**
     * Gets the official reply to this enquiry.
     * 
     * @return the response id if it exists, otherwise null
     */
    public int getReplyID(){
        return this.replyID;
    }
    /**
     * Gets the applicant's ID from the NRIC.
     * 
     * @return the applicant ID
     */
    public int getApplicant() {
        return Integer.parseInt(this.userNric.substring(1));
    }

    /**
     * Collect details about the enquiry into string.
     * 
     * @return a string representation of the enquiry details
     */
    public String toString() {
        return "Enquiry ID: " + enquiryID + "\n" +
               "User Nric: " + userNric + "\n" +
               "Project Name: " + projectName + "\n" +
               "Message: " + message + "\n";
    }

    // ========== SETTER METHODS ==========
    /**
     * Sets the enquiry ID.
     * 
     * @param enquiryID the new enquiry ID
     */
    public void setEnquiryID(int enquiryID) {
        this.enquiryID = enquiryID;
    }

    /**
     * Sets the user nric.
     * 
     * @param userNric the new user nric
     */
    public void setUserNric(String userNric) {
        this.userNric = userNric;
    }

    /**
     * Sets the project name.
     * 
     * @param projectName the new project name
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Sets the message.
     * 
     * @param message the new message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Checks if the enquiry has a reply.
     * 
     * @return true if the enquiry has a reply, false otherwise
     */
    public boolean hasReply() {
        return replyID != 0;
    }
}