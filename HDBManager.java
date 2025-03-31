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
    public Project createProject(ProjectDetails projectDetails){
        Project project = new Project();
        return project;
    }
    public void editProject(Project project, ProjectDetails newDetails){

    }
    public void deletProject(Project project){

    }
    public void toggleVisibility(Project project, boolean visible){

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
