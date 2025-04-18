public interface EnquiryReply {

    public default void reply(Enquiry enquiry, Enquiry reply) {
        enquiry.setReply(reply);
    }
    
}
