import java.sql.*;

public class HDBManager extends User{
    private Project[] managedProjects;
    

    //Abstract function
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


    //HDB manager functions
    public Project createProject(String projectDetails){
        Project project = new Project();
        
        // getting and setting project details	
        
        
        return project;
    }
    
    public void editProject(String source, String target, int index){
    	
    	// call the WRITE function to update respectively
    }
    
    public void deletProject(String source){
    	// call the DELETE function 
    	System.out.println("Successfully Deleted.");
    }
    
    public void toggleVisibility(String project, boolean visible){

    }
    
    public void approveOfficerRegistration(HDBOfficer officer, Project project){

    }
    
    public void rejectOfficerRegistration(HDBOfficer officer, Project project){

    }
    
    public void approveApplication(Applicant applicant, Project project){

    }
    
    public void rejectApplication(Applicant applicant, Project project){

    }
    
    public void approveWithdrawal(Applicant applicant, Project project){

    }
    
    public void rejectWithdrawal(Applicant applicant, Project project){

    }
    
    public String generateApplicantReport(ReportFilter filters){
        return "report";
    }
    
    public void replyToEnquiry(Enquiry enquiry, String reply){

    }
    
}
