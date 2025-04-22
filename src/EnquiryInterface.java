/**
 * <p>
 * This interface defines the methods for creating, modifying, and deleting enquiries related to housing projects.
 * It provides a contract for classes that implement enquiry functionalities.
 * </p>
 * 
 * @see Enquiry
 * @see Applicant
 * @see Project
 */
public interface EnquiryInterface {
    /**
     * Creates an enquiry for a project.
     * @param applicant The applicant making the enquiry
     * @param project The project the enquiry relates to
     * @param message The enquiry message content
     * @return The created Enquiry object
     */
    public default Enquiry createEnquiry(Applicant applicant, Project project, String message) {
        return new Enquiry(applicant, project, message);
    }
    /**
     * Modifies an existing enquiry.
     * @param enquiry The enquiry to modify
     * @param newMessage The new message content for the enquiry
     */
    public default void editEnquiry(Enquiry enquiry, String newMessage) {
        enquiry.setMessage(newMessage);
    }
    /**
     * Modifies an existing enquiry.
     * @param enquiry The enquiry to modify
     */
    public default void deleteEnquiry(Enquiry enquiry) {
        enquiry.setMessage(null);
    }
} 
