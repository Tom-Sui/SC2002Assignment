public interface EnquiryReply {

    public default void reply(Enquiry enquiry, Enquiry reply) {
        enquiry.setReplyID(reply.getEnquiryID());
    }
    
}
