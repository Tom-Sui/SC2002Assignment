import java.util.ArrayList;

public class Applicant extends User{
    private Project appliedProject;
    private ArrayList<Project> pastAppliedProjects = new ArrayList();
    private ApplicationStatus applicationStatus;
    private FlateType bookedFlateType;

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
    			System.out.println(projects.get(i).toString());
    		}	
    	} 	
        
    }
    public void applyForProject(Project project){

    }
    public ApplicationStatus viewApplicationStatus(){
        ApplicationStatus applicationStatus = new ApplicationStatus();
        return applicationStatus;
    }
    public void requestWithdrawal(){

    }
    public void bookFlat(HDBOfficer officer, FlateType flateType){

    }
    //Duplicated in UML diagram
    // public boolean canApply(Project project){
    //     return false;
    // }

}
