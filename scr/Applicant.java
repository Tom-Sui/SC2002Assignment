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
    			if (super.getMaritalStatus() == MaritalStatus.SINGLE)
    				System.out.println(projects.get(i).toString());
    			else 
    				System.out.println(projects.get(i).toString());
    		}
    		
    	}
    	
        
    }
    
    /*
    * @param project - Project object
    * @return void
    * @throws Exception - If applicant is not eligible to apply for the project    
    * @description - This function checks and allows the applicant to apply for a project, (checks if the applicant is single and the project is not a 3 room flat)   */

    public void applyForProject(Project project){
        int eligible = this.getMaritalStatus() == MaritalStatus.SINGLE ? 1 : 0 ;
        if (eligible == 1 && project.getFlateType() == FlateType.flateType.ThreeRoom){
            System.out.println("Cannot apply for project, applicant is single: " + project.getProjectName());
            return;
        }
        appliedProject = project;
        appliedBookedFlateType = project.getFlateType();
        System.out.println("Applied for project: " + project.getProjectName());
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
