public interface EnquiryCreator {

    public default Enquiry createEnquiry(String userNric, String projectName, String message) {
        return new Enquiry(userNric, projectName, message, 0);
    }

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
