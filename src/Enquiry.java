/**
 * Represents an enquiry made by an applicant about a specific housing project.
 * <p>
 * This class stores all details related to a housing enquiry including the applicant information,
 * project being enquired about, the enquiry message, and any official reply.
 * </p>
 * 
 * @see Applicant
 * @see Project
 */
public class Enquiry {
    private static int enquiryID = 0;
    private String userNric; 
    private String projectName;
    private String message;
    private Enquiry reply;

    // ========== CONSTRUCTOR ==========
    /**
     * Constructs an Enquiry object with the specified applicant, project, and message.
     * 
     * @param userNric the Applicant making the enquiry
     * @param projectName the Project being enquired about
     * @param message the enquiry message content String     */
    public Enquiry(String userNric, String projectName, String message) {
        enquiryID = enquiryID++;
        this.userNric = userNric;
        this.projectName = projectName;
        this.message = message;
    }

    /**
     * Sets the official reply to this enquiry.
     * 
     * @param reply the response text
     */
    public void setReply(Enquiry reply) {
        if (reply != null) {
            System.out.println("");
        }
        this.reply = reply;
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
     * @return the response text
     */
    public Enquiry getReply() {
        return this.reply;
    }
}