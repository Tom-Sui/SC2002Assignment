import java.util.ArrayList;

public class Applicant extends User{
    private Project appliedProject;
    private ArrayList<Project> pastAppliedProjects = new ArrayList<Project>();
    private ApplicantStatus applicationStatus;
    private FlatType bookedFlatType;
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
    public void viewAvailableProjects(ArrayList<Project> projects){
    	System.out.println("\nList of available projects:");
		System.out.println("============================");
    	int size = projects.size();
    	for (int i=0; i<size; i++) {
    		// getMaritalStatus gives attribute ms which is a temporary placeholder for marital status
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
        appliedProject = project;
        System.out.println("Applied for project: " + project.getProjectName());
        System.out.println("Flat type: " + flatType.toString());
        Application application = new Application(this, project, flatType);
        project.addApplication(application);
        System.out.println("Application ID: " + application.getApplicationId());
    }
    public ApplicantStatus viewApplicationStatus(){
        // ApplicationStatus applicationStatus = new ApplicantStatus();
        return this.applicationStatus;
    }
    public void requestWithdrawal(){

    }
    public void bookFlat(){
    	if (applicationStatus == ApplicantStatus.SUCCESSFUL) {
    		ProjectLogic.displayHDBOfficers(appliedProject);
    		
    	}
    	
    }

    public Project getAppliedProject() {
        return appliedProject;
    }
}
