public interface EnquiryInterface {

    public default Enquiry createEnquiry(Applicant applicant, Project project, String message) {
        return new Enquiry(applicant, project, message);
    }

    public default void editEnquiry(Enquiry enquiry, String newMessage) {
        enquiry.setMessage(newMessage);
    }

    public default void deleteEnquiry(Enquiry enquiry) {
        enquiry.setMessage(null);
    }
} 
