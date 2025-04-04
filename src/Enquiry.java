public class Enquiry {
    private String enquiryID;
    private Applicant applicant;
    private Project project;
    private String message;
    private String reply;

    //set function
    public void setEnquiryID(String enquiryID){
        this.enquiryID = enquiryID;
    }
    public void setApplicant(Applicant applicant){
        this.applicant = applicant;
    }
    public void setProject(Project project){
        this.project = project;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public void setReply(String reply){
        this.reply = reply;
    }

    //get function
    public String getEnquiryID(){
        return this.enquiryID;
    }
    public Applicant getApplicant(){
        return this.applicant;
    }
    public Project getProject(){
        return this.project;
    }
    public String setMessage(){
        return this.message;
    }
    public String getReply(){
        return this.reply;
    }
}
