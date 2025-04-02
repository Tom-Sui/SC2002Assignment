import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HDBManager extends User{
    private String DataFilePath = "./Data";
    private Project[] managedProjects;

    //set managed projects
    public void setManagedProjects(Project project){
        if (managedProjects == null) {
            Project[] temp = new Project[1];
    
            temp[temp.length - 1] = project;
            System.out.println(temp.length);
            managedProjects = temp;
            // System.arraycopy(temp,0 , managedProjects, 0, temp.length);
            // System.out.println(managedProjects[0].getNeiborhood());
        }else{
            int count = 0;
            Project[] temp = new Project[managedProjects.length + 1];

            for(Project item:managedProjects){
                
                temp[count] = item;
                count += 1;
            }
    
            temp[temp.length - 1] = project;

            managedProjects = temp;
            // System.arraycopy(temp,0 , managedProjects, 0, temp.length);
            // System.out.println(managedProjects[0].getNeiborhood());
        }

    }
    public Project[] getProject(){
        return this.managedProjects;
    }

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
    public Project[] createProject(String projectDetails, Project[] projects,HDBManager[] hdbManager, HDBOfficer[] hdbOfficer){
        String filecontent = "";
        int size;

        File officerFile = new File(DataFilePath + "/ProjectList.txt");

        try{
            Scanner scanner = new Scanner(officerFile);
            Init init = new Init();
            size = Integer.parseInt(scanner.nextLine()) + 1;
            filecontent = filecontent + size + "\n";

            while(scanner.hasNextLine()){
                filecontent = filecontent + scanner.nextLine() + "\n";
            }
            filecontent = filecontent + projectDetails;
            String[] buffer = projectDetails.split(",");

            Project[] project = new Project[size];
            System.arraycopy(projects,0 , project, 0, projects.length);

            project[project.length - 1] = init.setProject(buffer, hdbManager, hdbOfficer);

            FileWriter writer = new FileWriter(DataFilePath + "/ProjectList.txt");
            writer.write(filecontent);
            writer.close();
            scanner.close();
            return project;
        }catch (FileNotFoundException e){
            System.out.println("Error occured while reading ProjectList.txt");
            e.printStackTrace();
            return projects;
        }catch(IOException e){
            System.out.println("Error occured while writing into ProjectList.txt");
            e.printStackTrace();
            return projects;
        }
    }
    public boolean editProject(Project project, String updateContent, String target){
        for(Project managedProject: managedProjects){
            if(managedProject.getProjectName().equals(project.getProjectName())){
                switch (target.replace(" ","").toLowerCase()) {
                    case "name":
                        
                        break;
                    case "neiborhood":
                        
                        break;
                    case "flatetype":
                        
                        break;
                    case "openingdate":
                        
                        break;
                    case "closingdate":
                        
                        break;
                    case "manager":
        
                        break;
                    case "officer":
        
                        break;
                    default:
                        break;
                }
                return true;
            }else{
                System.out.println("Not within managed projects");
                return false;
            }
        }

        return false;

    }
    // public project[] deletProject(Project project){


    // }
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
