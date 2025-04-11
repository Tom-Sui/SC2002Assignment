import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class HDBManager extends User{
    private String DataFilePath = "./Data";
    private ArrayList<Project> managedProjects = new ArrayList<Project>();

    //set managed projects
    public void setManagedProjects(Project project){
        this.managedProjects.add(project);
    }

    public ArrayList<Project> getProject(){
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
    //Yet: update the program to track managed project dates
    public ArrayList<Project> createProject(String projectDetails, ArrayList<Project> projects,ArrayList<HDBManager> hdbManager, ArrayList<HDBOfficer> hdbOfficer){
        String filecontent = "";

        File officerFile = new File(DataFilePath + "/ProjectList.txt");
        Init init = new Init();

        try{
            Scanner scanner = new Scanner(officerFile);

            while(scanner.hasNextLine()){
                filecontent = filecontent + scanner.nextLine() + "\n";
            }
            filecontent = filecontent + projectDetails;

            String[] buffer = projectDetails.split(",");

            Project project = init.setProject(buffer, hdbManager, hdbOfficer);

            if(project == null){
                scanner.close();
                return init.LoadProjectInfo(hdbManager,hdbOfficer);
            }

            this.managedProjects.add(project);

            FileWriter writer = new FileWriter(DataFilePath + "/ProjectList.txt");
            writer.write(filecontent);
            writer.close();
            scanner.close();
            return init.LoadProjectInfo(hdbManager,hdbOfficer);
        }catch (FileNotFoundException e){
            System.out.println("Error occured while reading ProjectList.txt");
            e.printStackTrace();
            return init.LoadProjectInfo(hdbManager,hdbOfficer);
        }catch(IOException e){
            System.out.println("Error occured while writing into ProjectList.txt");
            e.printStackTrace();
            return init.LoadProjectInfo(hdbManager,hdbOfficer);
        }
    }

    //If call this function
    //Do remember to run init.LoadProjectInfo() to restore the changed project info
    public boolean editProject(String projectName, String updateContent, String target,ArrayList<HDBManager> hdbManager,ArrayList<HDBOfficer> hdbOfficer){
        General general = new General();
        Scanner scanner = new Scanner(System.in);
        if (managedProjects == null) {
            System.out.println("No managed projects");
            scanner.close();
            return false;
        }
        for(Project managedProject: managedProjects){
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
                    case "flatetype","3":


                        //YET
                        //Note that there are more than one flate type
                        //flate type is under a seperate class
                        //remeber to edit each of the content


                        break;
                    case "openingdate","4":
                        if(general.findProject(this.managedProjects, projectName) != null){
                            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            general.editFile(DataFilePath + "/ProjectList.txt", 
                                            updateContent,
                                            general.findProject(this.managedProjects, projectName).getApplicationOpeningDate().toString(),
                                            projectName);
                            System.out.println("Opending date changed to: " + updateContent);
                            break;
                        }
                        System.out.println("Not within managed projects");
                        break;
                    case "closingdate","5":
                        if(general.findProject(this.managedProjects, projectName) != null){
                            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            general.editFile(DataFilePath + "/ProjectList.txt", 
                                            updateContent,
                                            general.findProject(this.managedProjects, projectName).getApplicationClosingDate().toString(),
                                            projectName);
                            System.out.println("Closing date changed to: " + updateContent);
                            break;
                        }
                    System.out.println("Not within managed projects");
                        break;
                    // case "manager","6":
                    //     //DONE: add the project to another manager
                    //     //DONE: delet project from current manager
                    //     if(general.findManager(hdbManager, updateContent)!= null){
                    //         general.findManager(hdbManager, updateContent).setManagedProjects(this.deletProject(general.findProject(this.managedProjects,projectName)));
                    //     }else{
                    //         System.out.println("New manager does not exist");
                    //         break;
                    //     }
                    //     //DONE: change project manager name
                    //     general.editFile(DataFilePath + "/ProjectList.txt", updateContent,this.getName(),projectName);
                    //     break;
                    // case "officer","7":



                    //     //YET: same as manager
                    //     //Note that there are more than one officer

        
                    //     break;
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
    
    public Project deletProject(Project targetProject){
        // ArrayList<Project> tempProject = new Project[this.managedProjects.size() - 1];
        File projectFile = new File("./Data/ProjectList.txt");
        String fileContent = "";
        String buffer;
        try{
            Scanner scanner = new Scanner(projectFile);
            while(scanner.hasNextLine()){
                buffer = scanner.nextLine();
                String[] data = buffer.split(",");

                if(data[0].equals(targetProject.getProjectName())){
                    continue;
                }
                fileContent = fileContent + buffer + "\n";
            }
            FileWriter writer = new FileWriter("./Data/ProjectList.txt");
            writer.write(fileContent);
            writer.close();
            scanner.close();
        }catch (FileNotFoundException e){
            System.out.println("Error occured while reading ProjectLists.txt");
            e.printStackTrace();
            return null;
        }catch(IOException e){
            System.out.println("Error occured when writing ProjectLists.txt");
            e.printStackTrace();
        }

        if(this.managedProjects == null){
            System.out.println("No project managed");
            return targetProject;
        }

        this.managedProjects.remove(targetProject);
        System.out.println("Project " + targetProject.getProjectName() + " removed");
        return targetProject;
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
        // filter for applicant PENDINGWITHDRAWAL application 
        // set application status to WITHDRAWN
        // remove application from applicant
        // remove application from project
        // check if bookedflat 
        // minus from available units 


    }
    public void rejectWithdrawal(Applicant applicant, Project project){

    }
    public String generateApplicantReport(ReportFilter filters){
        return "report";
    }
    public void replyToEnquiry(Enquiry enquiry, String reply){

    }

    // private Project findProject(String projectName){
    //     if(this.managedProjects != null){
    //         for(Project project: this.managedProjects){
    //             if(project.getProjectName().equals(projectName)){
    //                 return project;
    //             }
    //         }
    //     }
    //     return null;
    // }
}
