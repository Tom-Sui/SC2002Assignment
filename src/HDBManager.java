import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
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
        if (this.managedProjects == null) {
            System.out.println("Current manager has no managed projects");
            return null;
        }
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

    //If call this function
    //Do remember to run init.LoadProjectInfo() to restore the changed project info
    public boolean editProject(String projectName, String updateContent, String target,HDBManager[] hdbManager,HDBOfficer[] hdbOfficer){
        General general = new General();
        Scanner scanner = new Scanner(System.in);
        
        for(Project managedProject: managedProjects){
            if (managedProject == null) {
                System.out.println("No managed projects");
                break;
            }
            if(managedProject.getProjectName().equals(projectName)){
                switch (target.replace(" ","").toLowerCase()) {
                    //note that if changing managed project name
                    //pass the new manager's name
                    case "projectname","1":
                        general.editFile(DataFilePath + "/ProjectList.txt", updateContent,projectName,projectName);
                        break;
                    case "neiborhood","2":
                        if(general.findProject(this.managedProjects, projectName) != null){
                            general.editFile(DataFilePath + "/ProjectList.txt", 
                                            updateContent,
                                            general.findProject(this.managedProjects, projectName).getNeiborhood(),
                                            projectName);
                            System.out.println("Neiborhood changed to: " + updateContent);
                            break;
                        }
                        System.out.println("Not within managed projects");
                        break;
                    case "flatetype":


                        //YET
                        //Note that there are more than one flate type
                        //flate type is under a seperate class
                        //remeber to edit each of the content


                        break;
                    case "openingdate":
                        if(general.findProject(this.managedProjects, projectName) != null){
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            general.editFile(DataFilePath + "/ProjectList.txt", 
                                            updateContent,
                                            general.findProject(this.managedProjects, projectName).getApplicationOpeningData().format(formatter),
                                            projectName);
                            System.out.println("Opending date changed to: " + updateContent);
                            break;
                        }
                        System.out.println("Not within managed projects");
                        break;
                    case "closingdate":
                        if(general.findProject(this.managedProjects, projectName) != null){
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            general.editFile(DataFilePath + "/ProjectList.txt", 
                                            updateContent,
                                            general.findProject(this.managedProjects, projectName).getApplicationClosingData().format(formatter),
                                            projectName);
                            System.out.println("Closing date changed to: " + updateContent);
                            break;
                        }
                    System.out.println("Not within managed projects");
                        break;
                    case "manager":
                        //DONE: add the project to another manager
                        //DONE: delet project from current manager
                        if(general.findPerson(hdbManager, updateContent)!= null){
                            general.findPerson(hdbManager, updateContent).setManagedProjects(deletProject(projectName));
                        }else{
                            System.out.println("Manager does not exist");
                            break;
                        }
                        //DONE: change project manager name
                        general.editFile(DataFilePath + "/ProjectList.txt", updateContent,this.getName(),projectName);
                        break;
                    case "officer":



                        //YET: same as manager
                        //Note that there are more than one officer

        
                        break;
                    default:
                        System.out.println("!!!Error input!!!");
                        break;
                }
                scanner.close();
                return true;
            }else{
                System.out.println("Not within managed projects");
                scanner.close();
                return false;
            }
        }
        System.out.println("There is no managed projects");
        scanner.close();
        return false;
    }
    
    public void deletProject(Project targetProject){
        Project[] tempProject = new Project[this.managedProjects.length - 1];
        int count = 0;
        for(Project project : this.managedProjects){
            if(project.getProjectName().equals(targetProject.getProjectName())){
                continue;
            }else{
                tempProject[count] = project;
                count++;
            }
        }
        this.managedProjects = tempProject;
    }
    public Project deletProject(String targetProject){
        Project[] tempProject = new Project[this.managedProjects.length - 1];
        Project deletedProject = new Project();
        int count = 0;
        for(Project project : this.managedProjects){
            if(project.getProjectName().equals(targetProject)){
                deletedProject = project;
                continue;
            }else{
                tempProject[count] = project;
                count++;
            }
        }
        this.managedProjects = tempProject;
        return deletedProject;
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
