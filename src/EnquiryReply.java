/**
 * Enquiry.java
 * 
 * This class represents an enquiry made by an applicant regarding a housing project.
 */
public interface EnquiryReply {
    /**
     * Replies to an enquiry by setting the reply ID of the enquiry to the ID of the reply.
     * 
     * @param enquiry the Enquiry object to be replied to
     * @param reply the Enquiry object representing the reply
     */
    public default void reply(Enquiry enquiry, Enquiry reply) {
        enquiry.setReplyID(reply.getEnquiryID());
    }
    
}
