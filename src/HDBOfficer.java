public class HDBOfficer extends Applicant{
    private Project managedProject;
    private OfficerRegistrationStatus officerRegistrationStatus;

    public HDBOfficer() {};
    public HDBOfficer(String name, String NRIC, String userID, String password, int age, MaritalStatus maritalStatus, Project managedProject){
    	super(name, NRIC, userID, password, age, maritalStatus);
    	this.managedProject = managedProject;
    }
    //Abstract functions
    public Enquiry createEnquiry(Project project, String message){
        Enquiry[] enquiry = new Enquiry[10];
        return enquiry[0];
    }
    public Enquiry[] viewEnquiries(){
        Enquiry[] enquiry = new Enquiry[10];
        return enquiry;
    }
    public void editEnquiry(Enquiry enquiry, String newMessage){

    }
    public void deletEnquiry(Enquiry enquiry){

    }
    public boolean canApply(Project project){
        return false;
    }

    //HDBOfficer function
    public void registerForProject(Project project){

    }
    public void viewProjectDetails(Project project){

    }
    public void replyToEnquiry(Enquiry enquiry, String reply){

    }
    
    public void updateRemaningFlatUnits(Project project, FlatType flatType){

    }
    public Applicant retrieveApplicant(Applicant applicant){
        return applicant;
    }
    
    public String toString() {
    	String officerDetails = String.format("%s, %d years old", super.getName(), super.getAge());
		return officerDetails;
    }
    
}
