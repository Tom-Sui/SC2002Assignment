public class Applicant extends User{
    private Project applProject;
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
    public Project[] viewAvailableProjects(Project[] allProjects){
        Project[] project = new Project[10];
        return project;
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
