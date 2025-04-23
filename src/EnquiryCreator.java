// File: Enquiry.java

/**
 * EnquiryCreator.java
 * 
 * This interface defines methods for creating and editing enquiries related to housing projects.
 * It provides default implementations for creating a new enquiry and editing an existing one.
 * 
 * @see Enquiry
 */
public interface EnquiryCreator {
    /**
     * Creates a new enquiry with the specified user NRIC, project name, and message.
     * 
     * @param userNric the NRIC of the user making the enquiry
     * @param projectName the name of the project being enquired about
     * @param message the content of the enquiry message
     * @return a new Enquiry object with the specified details
     */
    public default Enquiry createEnquiry(String userNric, String projectName, String message) {
        return new Enquiry(userNric, projectName, message, 0);
    }
    /**
     * Edits an existing enquiry by updating its message content.
     * 
     * @param enquiry the Enquiry object to be edited
     * @param message the new message content for the enquiry
     * @return the updated Enquiry object
     */
    public default Enquiry editEnquiry(Enquiry enquiry, String message) {
        if (enquiry == null) {
            System.out.println("Enquiry cannot be null");
            return null;
        }
        enquiry.setMessage(message);
        System.out.println("Enquiry updated");
        return enquiry;
    }
}
