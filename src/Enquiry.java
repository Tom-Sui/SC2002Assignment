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
    // /** 
    //  * @param enquiryID
    //  * Unique identifier for the enquiry 
    //  * 
    //  * @param applicant
    //  * The applicant who made the enquiry
    //  * 
    //  * @param project
    //  * The project being enquired about
    //  * 
    //  * @param message
    //  * The enquiry message content 
    //  * 
    //  * @param reply
    //  * The official reply to the enquiry 
    //  */
    private String enquiryID;
    private Applicant applicant;
    private Project project;
    private String message;
    private String reply;

    // ========== SETTER METHODS ==========
    
    /**
     * Sets the unique identifier for this enquiry.
     * 
     * @param enquiryID the unique ID to assign
     */
    public void setEnquiryID(String enquiryID) {
        this.enquiryID = enquiryID;
    }
    
    /**
     * Sets the applicant associated with this enquiry.
     * 
     * @param applicant the Applicant making the enquiry
     */
    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }
    
    /**
     * Sets the project this enquiry is about.
     * 
     * @param project the Project being enquired about
     */
    public void setProject(Project project) {
        this.project = project;
    }
    
    /**
     * Sets the enquiry message content.
     * 
     * @param message the enquiry text
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    /**
     * Sets the official reply to this enquiry.
     * 
     * @param reply the response text
     */
    public void setReply(String reply) {
        this.reply = reply;
    }

    // ========== GETTER METHODS ==========
    
    /**
     * Gets the enquiry's unique identifier.
     * 
     * @return the enquiry ID
     */
    public String getEnquiryID() {
        return this.enquiryID;
    }
    
    /**
     * Gets the applicant who made this enquiry.
     * 
     * @return the Applicant object
     */
    public Applicant getApplicant() {
        return this.applicant;
    }
    
    /**
     * Gets the project being enquired about.
     * 
     * @return the Project object
     */
    public Project getProject() {
        return this.project;
    }
    
    /**
     * Gets the enquiry message content.
     * 
     * @return the enquiry text
     */
    public String getMessage() {  // Fixed method name from setMessage to getMessage
        return this.message;
    }
    
    /**
     * Gets the official reply to this enquiry.
     * 
     * @return the response text
     */
    public String getReply() {
        return this.reply;
    }
}