import java.util.ArrayList;
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
    private Applicant applicant;
    private Project project;
    private String message;
    private ArrayList<Enquiry> replies = new ArrayList<>();
    private String reply;
    private boolean hasReplied;

    // ========== CONSTRUCTOR ==========
    /**
     * Constructs an Enquiry object with the specified applicant, project, and message.
     * 
     * @param applicant the Applicant making the enquiry
     * @param project the Project being enquired about
     * @param message the enquiry message content String     */
    public Enquiry(Applicant applicant, Project project, String message) {
        enquiryID = enquiryID++;
        this.applicant = applicant;
        this.project = project;
        this.message = message;
        this.reply = "";
        this.hasReplied = false;
    }

    // ========== SETTER METHODS ==========
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
    public void setReply(Enquiry reply) {
        this.replies.add(reply);
    }
    /**
     * Sets the official reply to this enquiry.
     * 
     * @param stringReply the response text
     */
    public void setStringReply(String stringReply) {
    	this.reply = stringReply;
    }
    /**
     * Sets the boolean value indicating if the enquiry has been replied to.
     * 
     * @param replyBoolean true if replied, false otherwise
     */
    public void setReplyBoolean(boolean replyBoolean) {
    	this.hasReplied = replyBoolean;
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
    public ArrayList<Enquiry> getReplies() {
        return this.replies;
    }
    /**
     * Gets the official reply to this enquiry.
     * 
     * @return the response text
     */
    public String getStringReply() {
    	return reply;
    }
    /**
     * Checks if the enquiry has been replied to.
     * 
     * @return true if replied, false otherwise
     */
    public boolean getReplyBoolean() {
    	return this.hasReplied;
    }
}