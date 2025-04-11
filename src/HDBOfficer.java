import java.util.ArrayList;

public class HDBOfficer extends Applicant{
    private Project managedProject;
    private OfficerRegistrationStatus officerRegistrationStatus = OfficerRegistrationStatus.PENDING;
    private ArrayList<Application> managedApplications = new ArrayList<Application>();
    private boolean isManagingOfficer = false;

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
    
    public void setManagedProject(Project project) {
    	managedProject = project;
    }
    //HDBOfficer function
    public void registerForProject(Project project){

    }
    /
    public void viewProjectDetails(){
        System.out.println("Manager Name: " + managedProject.getHDBManager().getName());
        System.out.println(managedProject.toString());
    }

    public OfficerRegistrationStatus getOfficerRegistrationStatus() {
    	return officerRegistrationStatus;
    }
    public void viewBookRequests() {
    	if (managedApplications.size() > 0) {
        	for (Application app : managedApplications) {
        		System.out.println(app.getApplicant().getName() + " " + app.getApplicationStatus());
        	}
    	}

    }
    public void replyToEnquiry(Enquiry enquiry, String reply){

    }
    
    public ArrayList<Application> getApplications(){
    	return managedApplications;
    }
    
    public void receiveBookFlatRequest(Application application) {
    	managedApplications.add(application);
    }
    
    public void helpBookFlat(Application application) {
    	FlatType chosenFlatType = application.getFlatType();
    	ArrayList<FlatType> flatTypes = managedProject.getFlatTypeList();
    	int remainingUnits = FlatTypeLogic.returnFilteredFlatTypeUnits(flatTypes, chosenFlatType);
    	if (remainingUnits >= 1) {
    		//Updating applicant and his application process
    		application.setIsBooked(true); //finished booking
    		application.setBookingRequested(false); //reset request back to false
    		application.getApplicant().setFlatType(chosenFlatType); //update flatType in applicant profile
    		application.setApplicationStatus(ApplicationStatus.BOOKED);
    	
    		//Updating units in project
    		FlatTypeLogic.updateFilteredFlatTypeUnits(flatTypes, chosenFlatType);
    		System.out.println("Booking successful!");
    	}
    	else {
    		System.out.println("Booking unsuccessful. Chosen flat type has no more units.");
    	}
    }
    
    public void generateFlatReceipt(Application application) {
    	System.out.println(application.toString());
    }

    //Getters and Setters
    /*      
     * @return if the officer is managing a Project 
     */
    public boolean isManagingOfficer() {
    	return isManagingOfficer;
    }

    /*
     * @param isManagingOfficer if the officer is managing a Project
     */

    public void setManagingOfficer(boolean isManagingOfficer) {
    	this.isManagingOfficer = isManagingOfficer;
    }

    public String toString() {
    	String officerDetails = String.format("%s, %d years old", super.getName(), super.getAge());
		return officerDetails;
    }
    
}
