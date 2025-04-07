import java.util.ArrayList;

public class Applicant extends User{
    private Application currentApplication; 
    private ArrayList<Project> pastAppliedProjects = new ArrayList<Project>();
    private ApplicationStatus applicationStatus;
    private FlatType bookedFlatType;
    
    public Applicant() {};
    public Applicant(String name, String NRIC, String userID, String password, int age, MaritalStatus maritalStatus, Project appliedProject,  ArrayList<Project> pastAppliedProjects, ApplicantStatus applicationStatus, FlatType bookedFlatType){
    	super(name, NRIC, userID, password, age, maritalStatus);
    	//TODO FILL  iN THE REST OF THE ATTRIBUTES
    }
    
    public Applicant(String name, String NRIC, String userID, String password, int age, MaritalStatus maritalStatus){
    	super(name, NRIC, userID, password, age, maritalStatus);
    }
    //Absract functions
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

    //Applicant functions
    
    /* Display projects that are available to the applicants
     */
    public void viewAvailableProjects(ArrayList<Project> projects){
    	int size = projects.size();
    	for (int i=0; i<size; i++) {
    		Project currentProject = projects.get(i);
    		if (currentProject.getVisibility() == true) {
                System.out.println("Project ID: " + (i+1));
    			System.out.println(projects.get(i).toString());
    		}	
    	} 	  
    }
    
    /*
    * @param project - Project object
    * @return void
    * @throws Exception - If applicant is not eligible to apply for the project    
    * @description - This function checks and allows the applicant to apply for a project, (checks if the applicant is single and the project is not a 3 room flat)   */

    public void applyForProject(Project project, FlatType flatType){
        System.out.println("Applied for project: " + project.getProjectName());
        System.out.println("Flat type: " + flatType.toString());
        Application application = new Application(this, project, flatType);
        project.addApplication(application);
        currentApplication = application;
        System.out.println("Application ID: " + application.getApplicationId());
    }
    public ApplicationStatus viewApplicationStatus(){
        // ApplicationStatus applicationStatus = new ApplicantStatus();
        return currentApplication.getApplicationStatus();
    }
    public void requestWithdrawal(){

    }
    public void bookFlat(HDBOfficer officer) {
    	currentApplication.setBookingRequested(true);
    	officer.receiveBookFlatRequest(currentApplication);
    }

    public Application getCurrentApplication() {
        return currentApplication;
    }

    public void setCurrentApplication(Application currentApplication) {
        this.currentApplication = currentApplication;
    }
}
